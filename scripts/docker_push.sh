#!/bin/bash

set -x
export PS4='+(${BASH_SOURCE}:${LINENO}): ${FUNCNAME[0]:+${FUNCNAME[0]}(): }'

echo $GCLOUD_SERVICE_KEY | base64 -d | docker login -u _json_key --password-stdin https://eu.gcr.io

export VERSIONTAG=$TRAVIS_BUILD_ID"-"$BRANCH
echo "Building with tags [$VERSIONTAG]"

docker build -t eu.gcr.io/census-int-ci/census-fs-cucumber:$VERSIONTAG .
docker push eu.gcr.io/census-int-ci/census-fs-cucumber:$VERSIONTAG

