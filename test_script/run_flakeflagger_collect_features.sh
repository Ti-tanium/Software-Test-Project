VERIFY=1 # set to 1 if needs to collect features from projects
BUILD_SMELL_DETECTOR=0 # build smell detector once.

# check java version
JAVA_VER=$(java -version 2>&1 | sed -n ';s/.* version "\(.*\)\.\(.*\)\..*".*/\1\2/p;')
USER_DIR=/misc/scratch/st_flaky_xx/
WORK_DIR=$(pwd)
LOCAL_MVN_REPO=/misc/scratch/st_flaky_xx/.m2

if [ "$JAVA_VER" -eq 18 ] 
then
  echo "Java version 8, checked."
else
  echo "Error: Java version not compatible! Java 8 is required!"
  exit 0
fi

# install mvn on user directory
if [ ! -d "${USER_DIR}/bin/apache-maven-3.5.4" ]; then
  echo "Installing maven on user directory..."
  if [ ! -d "${USER_DIR}/bin/" ]; then
    mkdir ${USER_DIR}/bin
  fi

  # download mvn
  wget https://dlcdn.apache.org/maven/maven-3/3.5.4/binaries/apache-maven-3.5.4-bin.tar.gz
  tar -xvf apache-maven-3.5.4-bin.tar.gz -C ${USER_DIR}/bin
  rm apache-maven-3.5.4-bin.tar.gz
else
  echo "Maven installed."
fi

# use local mvn
mvn=(${USER_DIR}/bin/apache-maven-3.5.4/bin/mvn)
echo "--------mvn version-----------"
$mvn -v
echo "------------------------------"

# build feature collector
if [ $BUILD_SMELL_DETECTOR -eq 1 ]; then
  echo "Building Test smell detector"
  cd ../FlakeFlagger/test-feature-collector
  $mvn install
  cp -v ./maven-extension/target/test-smells-maven-extension-1.0-SNAPSHOT.jar ${USER_DIR}/bin/apache-maven-3.5.4/lib/ext/
  echo "Environment setup complete."
fi

cd $WORK_DIR
if [ ! -f $WORK_DIR/results/flakeflagger_verify_runtime.csv ]; then
touch $WORK_DIR/results/flakeflagger_verify_runtime.csv
echo "project,runtime" > $WORK_DIR/results/flakeflagger_verify_runtime.csv
fi

# generate features for all projects
PROJECT_DIR=/misc/scratch/st_flaky_xx/projects/
echo "Start Running Project"
if [ $VERIFY -eq 1 ]; then
  while IFS="," read -r project runtime build_status _; do
      if [ ! $build_status -eq 0 ]; then
        continue
      fi
      cd "$PROJECT_DIR/$project"
      echo "Processing $project"
      echo "#### Clean up ####"
      $mvn clean
      $mvn dependency:purge-local-repository -DactTransitively=false -DreResolve=false
      echo "#### START VERIFY ####"
      start=`date +%s`
      $mvn -Drat.skip=true -Dmaven.javadoc.skip=true verify -fae
      end=`date +%s`
      runtime=$((end-start))

      # echo "$project,$runtime" >> $WORK_DIR/results/flakeflagger_verify_runtime.csv
      echo "$project verify time: $runtime"
      cd ..
  done < <(tail -n +2 $WORK_DIR/../test_script/results/flakeflagger_verify_runtime.csv)
fi

# /misc/scratch/st_flaky_xx/bin/apache-maven-3.5.4/bin/mvn -Drat.skip=true -Dmaven.javadoc.skip=true verify -fae
