#!/bin/sh

i=0
while read line
do
	i=$(( $i + 1))
	if (( i == $2 )); then
		echo $line | sed -n "s/^[^;]*;\([^;]*\);\([^;]*\);.*/\1 is \2/p"
		#printf "${name1} is ${name2}\n"
	fi
done < $1
