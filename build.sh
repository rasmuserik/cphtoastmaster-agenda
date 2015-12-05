#!/bin/bash -vx
killall java
lein clean 
lein cljsbuild once dist 
cd resources/app
cordova prepare
cordova build
