#!/bin/bash



if [ $# != 2 ]; then
    echo "must be specified launch mode: start,stop or restart!"
elif [ $1 == 'start' ]; then
    ./db.sh
    ./build.sh
    ./cp.sh
    ./start.sh
elif [ $1 == "stop" ]; then
    ./db.sh
    ./build.sh
    ./cp.sh
    ./stop.sh
elif [ $1 == "restart" ]; then
    ./db.sh
    ./build.sh
    ./cp.sh
    ./restart.sh
else
    echo 'not support command: $1'
fi
