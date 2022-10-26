#! /bin/bash
cd /misc/scratch/st_flaky_xx/
while IFS="," read -r name git_address
do
  echo "Git cloning $name"
  git clone $git_address
done < <(tail -n +2 /home/ecelrc/staff/xli6/Software-Test-Project/test-projects/historical_projects.csv)

echo "Done!"