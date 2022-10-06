# this to run generate the processsed_data from the features collections


projects_dir="../../test-projects/"
source_of_flaky_tests="../../test-projects/historical_rerun_flaky_tests.csv"
column_name="Test_method"

python generate_processed_data.py $projects_dir $source_of_flaky_tests $column_name
