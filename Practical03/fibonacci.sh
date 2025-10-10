#!/bin/bash

echo -n "Enter number of terms: "
read N

a=0
b=1

echo "Fibonacci Series:"
for (( i=0; i<N; i++ ))
do
    echo -n "$a "
    fn=$((a + b))
    a=$b
    b=$fn
done
echo
