# this to run generate the processsed_data from the features collections
FLAKEFLAGGER_DIR="../FlakeFlagger/flakiness-predicter/"

projects_dir="/misc/scratch/st_flaky_xx/projects/"
source_of_flaky_tests="../test-projects/flaky_tests.csv"
class_col="Test_class"
test_method_col="Test_method"

python3 $FLAKEFLAGGER_DIR/generate_processed_data.py $projects_dir $source_of_flaky_tests $test_method_col $class_col
