#!/bin/bash
dsti6=$1
dstport=$2
file=$3
vlc $file --play-and-exit -I dummy --sout '#std{access=udp,dst='$dsti6':'$dstport'}'
