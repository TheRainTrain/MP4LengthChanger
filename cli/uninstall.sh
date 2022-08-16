#!/bin/bash

# colors
GREEN="\033[0;32m"
YELLOW="\033[1;33m"
RESET="\033[0m"

run() {
    JARS=`ls /usr/lib/mp4lengthchanger-*.jar`
    
    if [ -z ${JARS} ]; then
        printf $YELLOW"MP4 Length Changer isn't installed!\n"
        printf $GREEN"Did you mean to run "$RESET"./install.sh"$GREEN"?\n"
        return 0
    fi
    
    printf $YELLOW"Uninstalling requires root privileges\n"
    sudo true || return 1
    printf $GREEN"Uninstalling...\n"
    
    printf $RESET

    BIN_PATH="/usr/bin/mp4lengthchanger"

    echo Removing binary at $BIN_PATH
    sudo \rm -f $BIN_PATH

    echo "Removing jar(s) at ${JARS}"
    sudo \rm -f ${JARS}	

    printf $GREEN"Uninstalled MP4 Length Changer!\n"
}

run || echo "No root privileges. Exiting"
printf $RESET

