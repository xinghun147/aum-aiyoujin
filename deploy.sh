#!/bin/sh

cd aiyoujin-core/
set MAVEN_OPTS="-Xms128m -Xmx1024m -XX:PermSize=128m -XX:MaxPermSize=256m"
mvn clean source:jar deploy
cd ../
