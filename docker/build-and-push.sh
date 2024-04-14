#!/bin/bash

cd ..

docker build -t lukamakojevic/hrm:arm64 -f ./docker/Dockerfile .

echo dckr_pat_HRy-1JU_iEZsHLcf5idhEzMmSfE | docker login -u lukamakojevic --password-stdin

docker push lukamakojevic/hrm:arm64
