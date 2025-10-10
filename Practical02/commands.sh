#!/bin/bash
# Menu-driven program

while true
do
    echo "-------------------------"
    echo " MENU "
    echo "1. List of files"
    echo "2. Process Status"
    echo "3. Date"
    echo "4. Users in system"
    echo "5. Quit"
    echo "-------------------------"
    echo -n "Enter your choice [1-5]: "
    read choice

    case $choice in
        1) ls ;;
        2) ps ;;
        3) date ;;
        4) who ;;
        5) echo "Exiting..."; exit ;;
        *) echo "Invalid choice! Please try again." ;;
    esac
done

