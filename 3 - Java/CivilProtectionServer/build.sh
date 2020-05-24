#!/usr/bin/bash
find src -name \*.java | xargs javac --release 8 -d build -cp ./../Common/common-1.0.jar;
#rmic -classpath ./build\;./../Common/common-1.0.jar -d build it.polimi.project14.server.EventStorageImpl;