#!/bin/sh

echo | sed -n "s/\(^[a-zA-Z].* .*(.*)\)$/\1;/p" "$1"
exit 0
