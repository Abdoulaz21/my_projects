#!/bin/bash

if [ $# != 1 -o ! -d $1 ]; then
	exit 1
elif [ ! -d "exe" -o ! -d "non-exe" ]; then
	mkdir "exe"
	mkdir "non-exe"
fi

var=$(ls $1)
for i in ${var}
do
	if [ -x "$1/$i" ]; then
		cp "$1/$i" "exe"
	else
		cp "$1/$i" "non-exe"
	fi
done
exit 0;
