#!/bin/sh

filename=default
number=1
ext=txt

for arg in "$@";
do
  shift
  case "$arg" in
    "--extension") set -- "$@" "-e" ;;
    "--number")    set -- "$@" "-n" ;;
    "--filename")  set -- "$@" "-f" ;;
    *)             set -- "$@" "$arg"
  esac
done

#echo "$@"

while getopts ":f:n:e:" options;
do
	case "${options}" in
		f)    filename="$OPTARG";;
		n)    number="$OPTARG";;
		e)    ext="$OPTARG";;
		?)    exit 1;;
	esac
done

shift $(($OPTIND - 1))
if [[ "$*" != "" ]];then
	exit 1
fi

for i in $(seq 1 $number)
do
	touch $filename-$i.$ext
done
