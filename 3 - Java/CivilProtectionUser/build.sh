#!/usr/bin/bash
find src -name \*.java | xargs javac --release 8 -d build -cp './../Common/common-1.0.jar'