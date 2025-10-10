#!/bin/bash
echo "Enter a number:"
read number

factorial=1
counter=1

while [ "$counter" -le "$number" ]
do
    factorial=$((factorial * counter))
    counter=$((counter + 1))
done

echo "Factorial of $number is $factorial"
