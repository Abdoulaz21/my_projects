#!/bin/sh

dir="."

touch ${dir}/' '
touch ${dir}/'\'
touch ${dir}/'--'
touch ${dir}/'|'
touch ${dir}/'"'
touch ${dir}/"'"
touch ${dir}/'# Exams are fun!'
touch ${dir}/';`kill -9 0`'
touch ${dir}/"--\$i*'\"\\"
for i in $(seq 1 50)
do
	dir="${dir}/$i"
	mkdir ${dir}
	if (( $i == 50 )); then
		touch ${dir}/"farfaraway"
	fi
done
