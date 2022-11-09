WORK_DIR=$(pwd)
FLAKIFY_DIR=$WORK_DIR/../Flakify
MODEL_WEIGHTS=FlakeFlagger

# install python packages
pip3 install -r $FLAKIFY_DIR/requirements.txt

echo "----------- Python packages install completed! ---------------"
dataset_file="${FLAKIFY_DIR}/dataset/FlakeFlagger/Flakify_FlakeFlagger_dataset.csv" 
model_weights="${FLAKIFY_DIR}/results/Flakify_per_project_model_weights_on_${MODEL_WEIGHTS}_dataset.pt"
results_file="${WORK_DIR}/results/Flakify_cross_validation_results.csv"

python3 ${FLAKIFY_DIR}/src/Flakify_predictor_per_project.py $dataset_file $model_weights $results_file