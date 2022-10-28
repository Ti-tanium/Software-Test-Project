VERIFY=1 # set to 1 if needs to collect features from projects

# check java version
JAVA_VER=$(java -version 2>&1 | sed -n ';s/.* version "\(.*\)\.\(.*\)\..*".*/\1\2/p;')
USER_DIR=~
WORK_DIR=$(pwd)

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
junit4_projects="Achilles ambari assertj-core checkstyle commons-exec dropwizard hadoop hbase httpcore jackrabbit-oak jimfs"
projects="Achilles hadoop hbase jackrabbit-oak dropwizard jimfs orbit oryx spring-boot undertow wro4j"
if [ $VERIFY -eq 1 ]; then
  for project in $projects; do
      if [ $project = "Achilles" ] || [ $project = "alluxio" ]; then
        echo "$project skiped"
        continue
      fi
      cd "$PROJECT_DIR/$project"
      echo "###################################"
      echo "Processing $project"
      $mvn clean
      start=`date +%s`
      $mvn -Drat.skip=true verify
      end=`date +%s`
      runtime=$((end-start))
      echo "$project,$runtime" >> $WORK_DIR/results/flakeflagger_verify_runtime.csv
      echo "$project verify time: $runtime"
      cd ..
  done
fi

# clean up
# rm ${USER_DIR}/bin/apache-maven-3.5.4/lib/ext/test-smells-maven-extension-1.0-SNAPSHOT.jar


