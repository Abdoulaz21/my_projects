#!/bin/sh

i=0
while read line
do
	i=$(( $i + 1))
	if (( i == $2 )); then
		name1=$(echo ${line} | cut -d';' -f2)
		name2=$(echo ${line} | cut -d';' -f3)
		printf "${name1} is ${name2}\n"
	fi
done < $1
