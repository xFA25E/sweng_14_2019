#!/usr/bin/bash
find src -name \*.java | xargs javac --release 8 -d build