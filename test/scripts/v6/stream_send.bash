#!/bin/bash
dsti6=$1
dstport=$2
file=$3
vlc -I dummy $file --sout '#std{access=udp,dst=['$dsti6']:'$dstport'}'