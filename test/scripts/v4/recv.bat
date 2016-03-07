SET port=%1
"C:\Program Files (x86)\VideoLAN\VLC\vlc.exe" udp://@127.0.0.1:%port% --sout="#display{noaudio,delay=2000}"