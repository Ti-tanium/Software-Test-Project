USER_DIR=~

rm ${USER_DIR}/bin/apache-maven-3.5.4/lib/ext/test-smells-maven-extension-1.0-SNAPSHOT.jar

# build feature collector
cd ../FlakeFlagger/test-feature-collector
  
mvn install

cp -v ./maven-extension/target/test-smells-maven-extension-1.0-SNAPSHOT.jar ${USER_DIR}/bin/apache-maven-3.5.4/lib/ext/
