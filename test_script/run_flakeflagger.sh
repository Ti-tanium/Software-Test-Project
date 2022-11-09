VERIFY=1 # set to 1 if needs to collect features from projects

# check java version
JAVA_VER=$(java -version 2>&1 | sed -n ';s/.* version "\(.*\)\.\(.*\)\..*".*/\1\2/p;')
USER_DIR=~
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
    mkdir ~/bin
  fi

  # download mvn
  wget https://dlcdn.apache.org/maven/maven-3/3.5.4/binaries/apache-maven-3.5.4-bin.tar.gz
  tar -xvf apache-maven-3.5.4-bin.tar.gz -C ~/bin
  rm apache-maven-3.5.4-bin.tar.gz
else
  echo "Maven installed."
fi

# build feature collector
if [ ! -f "${USER_DIR}/bin/apache-maven-3.5.4/lib/ext/test-smells-maven-extension-1.0-SNAPSHOT.jar" ]; then
  cd ../FlakeFlagger/test-feature-collector
  if [ ! -f "./maven-extension/target/test-smells-maven-extension-1.0-SNAPSHOT.jar" ]; then
    mvn install
  fi
  cp -v ./maven-extension/target/test-smells-maven-extension-1.0-SNAPSHOT.jar ${USER_DIR}/bin/apache-maven-3.5.4/lib/ext/
fi
echo "Environment setup complete."

# use local mvn
mvn=(~/bin/apache-maven-3.5.4/bin/mvn)
echo "--------mvn version-----------"
$mvn -v
echo "------------------------------"

cd $WORK_DIR
if [ ! -f $WORK_DIR/results/flakeflagger_verify_runtime.csv ]; then
touch $WORK_DIR/results/flakeflagger_verify_runtime.csv
echo "project,runtime" > $WORK_DIR/results/flakeflagger_verify_runtime.csv
fi

# generate features for all projects
PROJECT_DIR=/misc/scratch/st_flaky_xx/projects/
projects="zxing"
if [ $VERIFY -eq 1 ]; then
  while IFS="," read -r project sha git_address; do
      cd "$PROJECT_DIR/$project"
      echo "Processing $project"
      echo "#### Clean up ####"
      $mvn clean
      $mvn dependency:purge-local-repository -DactTransitively=false -DreResolve=false
      echo "#### START VERIFY ####"
      start=`date +%s`
      $mvn -Drat.skip=true verify
      end=`date +%s`
      runtime=$((end-start))

      echo "$project,$runtime" >> $WORK_DIR/results/flakeflagger_verify_runtime.csv
      echo "$project verify time: $runtime"
      cd ..
  done < <(tail -n +2 $WORK_DIR/../test-projects/projects.csv)
fi

