#!/bin/sh

var=""

for i in $(seq $1 $2)
do
	var=$var$i
	if (( $i != $2 )); then
		var=$var'\n'
	fi
done

touch $3
echo -e ${var} > $3
exit 0
