#!/bin/bash

set -e

echo "------ 编译打包DAM start - build -------"
printenv

if [ -z "${VERSION}" ]
then
    echo "设置默认环境变量 [VERSION]"
    export VERSION=$(cat $(pwd)/../../gradle.properties|grep version|awk '{print $1}'|sed 's/version=//')
fi

if [ "${DOCKER_REPO}x" = "x" ]
then
    echo "设置默认环境变量 [DOCKER_REPO]"
    export DOCKER_REPO='qk/dam'
fi

echo "Version: $VERSION"
echo "Repo: $DOCKER_REPO"

#package(Project Directory)
echo -e "$(pwd)"
WORKDIR="$(pwd)"
cd $(pwd)/../../
./gradlew build  -x test --info
./gradlew makeReleaseJar

echo -e "mv distro/target/*SNAPSHOT.jar $WORKDIR/build/\n"
mv ./distro/target/*SNAPSHOT.jar $WORKDIR/build/

# docker build
BUILD_COMMAND="docker build --build-arg VERSION=${VERSION} SERVICE_NAME=${} -t $DOCKER_REPO:${VERSION} ."
echo -e "$BUILD_COMMAND\n"
if (docker info 2> /dev/null | grep -i "ERROR"); then
    sudo $BUILD_COMMAND
else
    $BUILD_COMMAND
fi

echo "------ dam end   - build -------"
