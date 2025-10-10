#!/bin/bash
echo -n "Enter how many numbers: "
read N

sum=0
echo "Enter the numbers:"
for (( i=1; i<=N; i++ ))
do
    read num
    sum=$((sum + num))
done

avg=$((sum / N))
echo "Average = $avg"


