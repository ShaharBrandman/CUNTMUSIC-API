#!/bin/sh

yt-dlp -x --audio-quality 0 --write-auto-subs --write-thumbnail --output "$PWD/$1/track.%(ext)s" https://www.youtube.com/watch?v=$1

echo 0
