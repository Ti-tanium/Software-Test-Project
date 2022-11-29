#! /bin/bash
mkdir ../../projects
rt=$PWD/../../projects
while IFS=, read -r name SHA URL
do
    echo "Git cloning $name and $URL"
    cd $rt
    echo "cd $rt"
    git clone $URL
    cd $name
    echo "cd $name"
    git checkout $SHA
    bash $rt/../Software-Test-Project/iDFlakies/pom-modify/modify-project.sh .
    mvn install -Dmaven.test.skip

    files=`find * -name pom.xml`
    for item in $files
    do
        Module_Name="$(dirname $item)"
        cd `dirname $item`
        echo "cd `dirname $item`"
        mvn install -Dmaven.test.skip
        mvn idflakies:detect -Ddetector.detector_type=random-class-method -Ddt.randomize.rounds=10 -Ddt.detector.original_order.all_must_pass=false
        echo "$name -- $Module_Name finshed"
        if find .dtfixingtools -mindepth 1 -maxdepth 1 | read; then
            mkdir -p $rt/../results/$name/$Module_Name
            echo "Made directory '$rt/../results/$name/$Module_Name'"
            cp -r .dtfixingtools $rt/../results/$name/$Module_Name
            echo "finished copy .dtfixingtools from '$rt/../results/$name/$Module_Name'"
            if find .dtfixingtools/detection-results -mindepth 1 -maxdepth 1 | read; then
                echo "Detect sucess"
            fi
        fi
        cd $rt/$name
        echo "cd `$rt/$name`"
    done
    mvn clean   
    cd ..
    echo "------$name finished, deleting project"
    rm -rf $name
    echo "-------$name deleted"
done < <(tail -n +2 projects.csv)