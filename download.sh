#!/bin/sh

yt-dlp -x --audio-quality 0 --write-auto-subs --write-thumbnail --output "$PWD/%(title)s/$1.%(ext)s" https://www.youtube.com/watch?v=$1
