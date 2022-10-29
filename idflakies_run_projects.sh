#! /bin/bash
mkdir ../projects
rt=$PWD/../projects
last=" "
lastModule=" "
while IFS=, read -r URL SHA Test_Count Module_Name Test_Name Category Version
do
    name=`echo $URL | awk -F/ '{print $NF}'`
    if [[ $URL != $last ]]; then
        echo "Git cloning $name and $URL"
        cd $rt
        git clone $URL
        cd $name
        git checkout $SHA
        bash $rt/../Software-Test-Project/iDFlakies/pom-modify/modify-project.sh .
        mvn install -Dmaven.test.skip
        cd ..
        last=$URL
    fi
    if [[ $Module_Name != $lastModule ]]; then
        cd $Module_Name
        mvn install -Dmaven.test.skip
        mvn idflakies:detect -Ddetector.detector_type=random-class-method -Ddt.randomize.rounds=10 -Ddt.detector.original_order.all_must_pass=false
        echo "$ModuleName finshed"
        if find .dtfixingtools/detection-results -mindepth 1 -maxdepth 1 | read; then
            mkdir -p $rt/../results/$Module_Name
            cp .dtfixingtools $rt/../results/$Module_Name
            echo "Detect sucess"
        fi
        cd $rt
        lastModule=$Module_Name
    fi
done < <(tail -n +2 list-flaky_filtered.csv)