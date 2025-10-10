#!/bin/bash

echo -n "Enter first number: "
read a
echo -n "Enter second number: "
read b
echo -n "Enter third number: "
read c

if [ $a -ge $b ] && [ $a -ge $c ]; then
    echo "Largest is $a"
elif [ $b -ge $a ] && [ $b -ge $c ]; then
    echo "Largest is $b"
else
    echo "Largest is $c"
fi



