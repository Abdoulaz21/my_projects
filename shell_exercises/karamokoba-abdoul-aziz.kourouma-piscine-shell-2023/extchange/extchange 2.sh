#!/bin/sh

if (( $# != 2 )); then
	echo "Usage: extchange.sh 'ext1' 'ext2'" 1>&2
	exit 1
fi
ext1=$1
ext2=$2
for i in *
do
	extFile=$(echo $i | cut -d "." -f 2)
	if [[ "$extFile" == "$ext1" ]];then
		name=$(echo $i | cut -d "." -f 1)
		mv "$i" "${name}.${ext2}"
		echo "mv $i ${name}.${ext2}"
	fi
done
if [ "$name" = "" ]; then
	exit 2
fi
exit 0
