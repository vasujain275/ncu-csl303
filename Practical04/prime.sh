#!/bin/bash

read -p "Enter a number: " num

if [ $num -le 1 ]; then
    echo "$num is not a prime number"
    exit
fi

is_prime=1
for (( i=2; i*i<=num; i++ ))
do
    if [ $((num % i)) -eq 0 ]; then
        is_prime=0
        break
    fi
done

if [ $is_prime -eq 1 ]; then
    echo "$num is a Prime number"
else
    echo "$num is Not a prime number"
fi
