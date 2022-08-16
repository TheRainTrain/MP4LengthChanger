#!/bin/bash

# colors
GREEN="\033[0;32m"
YELLOW="\033[1;33m"
RESET="\033[0m"

printf $YELLOW"WARNING: Don't run this script as root, you will be asked for authentication instead.\n"
echo

run() {
    printf $YELLOW"Installing requires root privileges!\n"
    sudo true || return 1
    printf $GREEN"Installing...\n"
    
    cd ..
    printf $GREEN"Building using maven...\n"$RESET
    mvn clean install
    
    cd target
    
    FILE=`ls *.jar`
    
    sudo cp ${FILE} /usr/lib
    printf $GREEN"Copied file ${FILE} to /usr/lib/${FILE}\n"
    printf $RESET
    
    BIN_PATH="/usr/bin/mp4lengthchanger"
    echo "java -jar /usr/lib/${FILE}" | sudo tee $BIN_PATH
    sudo chmod +x $BIN_PATH
    printf $GREEN"Installed MP4 Length Changer to $BIN_PATH\n"
}

run || echo "No root privileges. Exiting"
printf $RESET

