#! /bin/bash
$projects_csv = input.csv

while IFS="," read -r name git_address
do
  echo "Git cloning $name"
  git clone $git_address && cd $name && mvn verify && cd ..
done < <(tail -n +2 $projects_csv)

echo "Done!"