#!/bin/bash

if [ $# != 1 -o ! -f $1 -o ! -r $1 ]; then
	exit 1
fi

while read line
do
	if (( ${#line} > 80 )); then
		echo "${line}"
	else
		echo ${#line}
		echo "${line}"
	fi
done < $1
exit 0
