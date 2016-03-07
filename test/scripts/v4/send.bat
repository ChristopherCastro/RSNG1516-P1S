SET dsti6=%1
SET dstport=%2
SET file=%3
"C:\Program Files (x86)\VideoLAN\VLC\vlc.exe" "%file%" -I dummy --play-and-exit --sout="#transcode{vcodec=mp2v,vb=500,scale=1,deinterlace}:std{access=udp,dst=%dsti6%:%dstport%}"