#! /bin/bash

# directory to put all the projects
PROJECT_DIR=/misc/scratch/st_flaky_xx/projects/

WORK_DIR=$(pwd)
cd $PROJECT_DIR
pwd
while IFS="," read -r name sha git_address
do
  echo "Git cloning $name"
  git clone $git_address
  
  echo "Git checkout to $sha"
  cd $name
  git checkout $sha
  cd ..
done < <(tail -n +2 $WORK_DIR/projects.csv)

echo "Done!"