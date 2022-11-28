#! /bin/bash
rt=/misc/scratch/xlswtest/results
echo "project,sha,url,subproject,result,test_count,flaky_count" > $rt/idflakies_result_count.csv; \
echo "project,sha,url,subproject,flaky_test" > $rt/idflakies_result.csv; \
while IFS=, read -r name SHA URL
do
    cd $rt
    echo "------cd $rt"
    if [ -d $name ]; then
        cd $name
        echo "------cd $name"
        files=`find . -name .dtfixingtools`
        for item in $files
        do
            Module_Name="$(dirname $item)"
            cd $Module_Name/.dtfixingtools
            echo "------cd $Module_Name/.dtfixingtools"
            if find detection-results -mindepth 1 -maxdepth 1 | read; then
                test_count=`sed -n '$=' original-order`
                flaky_count=`sed -n '$=' detection-results/list.txt`
                if [ -z "$flaky_count" ]; then
                    flaky_count="0"
                else
                    echo "-------output tests"
                    while IFS= read -r line || [ -n "$line" ]; do
                        echo "$name,$SHA,$URL,$Module_Name,$line" >> $rt/idflakies_result.csv;
                    done < detection-results/list.txt
                fi
                echo "$name,$SHA,$URL,$Module_Name,success,$test_count,$flaky_count" >> $rt/idflakies_result_count.csv;
            else
                echo "$name,$SHA,$URL,$Module_Name,error,," >> $rt/idflakies_result_count.csv;
            fi
            cd $rt/$name
        done
    else
        echo "$name,$SHA,$URL,,mvn failure,," >> $rt/idflakies_result_count.csv;
    fi
done < <(tail -n +2 projects.csv)