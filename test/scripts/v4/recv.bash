#!/bin/bash
port=$1
vlc udp://@127.0.0.1:$port --sout '#display{noaudio,delay=2000}'