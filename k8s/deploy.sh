#!/bin/bash

# Define the filenames
MYSQL_PV="mysql-pv.yaml"
MYSQL_PVC="mysql-pvc.yaml"
MYSQL_SERVICE="mysql-service.yaml"
MYSQL_DEPLOYMENT="mysql-deployment.yaml"

HRM_SERVICE="hrm-service.yaml"
HRM_DEPLOYMENT="hrm-deployment.yaml"

# Apply each YAML file
kubectl delete all --all

kubectl apply -f "$MYSQL_PV"
kubectl apply -f "$MYSQL_PVC"
kubectl apply -f "$MYSQL_SERVICE"
kubectl apply -f "$MYSQL_DEPLOYMENT"

kubectl apply -f "$HRM_SERVICE"
kubectl apply -f "$HRM_DEPLOYMENT"
