#!/bin/sh

while read line
do
	if grep -E -x -q "EOF" <<< "$line" ; then
		exit 0
	elif grep -E -x -q "" <<< "$line" ; then
		echo "it is empty"
	elif grep -E -x -q "[a-zA-Z][a-zA-Z]*" <<< "$line" ; then
		echo "it is a word"
	elif grep -E -x -q "[0-9]" <<< "$line" ; then
		echo "it is a digit"
	elif grep -E -x -q "[0-9][0-9]*" <<< "$line" ; then
		echo "it is a number"
	elif grep -E -x -q "([0-9]|[a-zA-Z])([0-9]|[a-zA-Z])*" <<< "$line" ; then
		echo "it is an alphanum"
	else
		echo "it is too complicated"
		exit 0
	fi
done
