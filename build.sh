#!/bin/bash -vx
lein clean 
lein cljsbuild once dist 
cordova prepare
cordova build
