#!/bin/bash

# Config Service
kubectl apply -f config-service.yaml
kubectl apply -f config-deployment.yaml

# Keycloak DB
kubectl apply -f keycloak-data-persistentvolumeclaim.yaml
kubectl apply -f keycloakdb-service.yaml
kubectl apply -f keycloakdb-deployment.yaml

# Keycloak Service
kubectl apply -f keycloak-service.yaml
kubectl apply -f keycloak-deployment.yaml

# Eureka Service
kubectl apply -f eureka-service.yaml
kubectl apply -f eureka-deployment.yaml

# Edge Service
kubectl apply -f edge-service.yaml
kubectl apply -f edge-deployment.yaml

# Accounts DB
kubectl apply -f accountsdb-data-persistentvolumeclaim.yaml
kubectl apply -f accountsdb-service.yaml
kubectl apply -f accountsdb-deployment.yaml

# Accounts Service
kubectl apply -f accounts-service.yaml
kubectl apply -f accounts-deployment.yaml

