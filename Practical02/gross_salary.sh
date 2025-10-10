#!/bin/bash

# Prompt user for basic salary
echo -n "Enter Basic Salary: "
read basic

# Calculate HRA and DA
hra=$(echo "$basic * 0.2" | bc)
da=$(echo "$basic * 0.8" | bc)

# Calculate Gross Salary
gross=$(echo "$basic + $hra + $da" | bc)

# Display results
echo "Basic Salary : $basic"
echo "HRA (20%)    : $hra"
echo "DA (80%)     : $da"
echo "Gross Salary : $gross"

