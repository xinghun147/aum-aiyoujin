#!/bin/sh
mvn clean package -Dmaxmemory=500M -Dmaven.test.skip=true -U
