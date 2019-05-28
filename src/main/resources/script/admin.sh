#!/bin/bash

usage() {
    echo -e "\n  Usage:  $0 (start|stop|restart|status)\n"
    exit -1
}

if [ $UID -ne 0 ]; then
    echo -e "\nYou should run \"$0 $@\" as root.\n"
    exit -1
fi

if [ $# -ne 1 ]; then
    usage
fi

function start
{
	ps aux | grep "$CMD"| grep -v grep > /dev/null 2>&1

    if [ $? -eq 0 ]
    then
        echo "Failed to start $JAR_FILE ($JAR_FILE already running)"
    else
        ulimit -n 10240
        nohup $CMD > /tmp/${JAR_FILE}.log 2>&1 &
        if [ $? -ne 0 ]
        then
            echo "Failed to start $JAR_FILE"
        else
            echo "Success to start $JAR_FILE"
        fi
    fi
}

function stop
{
    kill -KILL `ps aux | grep -v grep | grep java | grep jar | grep $JAR_FILE_PREFIX | awk '{printf "%s ",$2}'` > /dev/null 2>&1
    if [ $? -ne 0 ]
    then
        echo "Failed to stop $JAR_FILE"
    fi
}

function status
{
    ps aux | grep "$CMD" | grep -v grep > /dev/null 2>&1
    if [ $? -eq 0 ]
    then
        echo "$JAR_FILE is running"
    else
        echo "$JAR_FILE is not running"
    fi
}

PROJECT_DIR=$(cd "$(dirname "$0")"; pwd;)
cd $PROJECT_DIR

JAR_FILE_PREFIX="bing"

JAR_FILE="$(ls -c $JAR_FILE_PREFIX*.jar|tail -n 1)"

APOLLO="-Denv=dev
        -Dapollo.meta=http://104.194.82.215:8080
        -Dapp.id=bing
        -Dapollo.cluster=promptness
        -Dapollo.cacheDir=/opt/properties/
        -Dapollo.bootstrap.enabled=true
        -Dapollo.bootstrap.eagerLoad.enabled=true
        -Dapollo.bootstrap.namespaces=application,business,bootstrap"

CMD="java -server -d64 -XX:+UseG1GC -XX:MaxGCPauseMillis=200  $APOLLO -Dnetworkaddress.cache.ttl=600 -Djava.security.egd=file:/dev/./urandom -Djava.awt.headless=true -Djava.library.path=/usr/local/apr/lib -jar $PROJECT_DIR/$JAR_FILE"

case "$1" in
    start)
        start
        ;;
    stop)
        stop
        ;;
    restart)
        stop
        start
        ;;
    status)
        status
        exit 0
        ;;
    *)
        usage
        ;;
esac


