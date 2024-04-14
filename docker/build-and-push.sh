#!/bin/bash

cd ..

docker build -t lukamakojevic/hrm:arm64 -f ./docker/Dockerfile .

echo dckr_pat_B6em4NipK0c4O348rPaVzaidrFI | docker login -u lukamakojevic --password-stdin

docker push lukamakojevic/hrm:arm64
