#!/bin/sh

if [ -d $1 ]; then
	if [ -f $2 ]; then
		> $2
	else
		touch $2
	fi
	for i in $1/*
	do
		echo "$i" >> $2
	done
else
	if [ -f $2 ]; then
		> $2
	fi
fi
