#bin/bash

# Config Service
kubectl delete -f config-deployment.yaml
kubectl delete -f config-service.yaml

# Keycloak DB
kubectl delete -f keycloakdb-deployment.yaml
kubectl delete -f keycloakdb-service.yaml

# Keycloak Service
kubectl delete -f keycloak-deployment.yaml
kubectl delete -f keycloak-service.yaml

# Eureka Service
kubectl delete -f eureka-deployment.yaml
kubectl delete -f eureka-service.yaml

# Edge Service
kubectl delete -f edge-deployment.yaml
kubectl delete -f edge-service.yaml

# Accounts DB
kubectl delete -f accountsdb-deployment.yaml
kubectl delete -f accountsdb-service.yaml

# Accounts Service
kubectl delete -f accounts-deployment.yaml
kubectl delete -f accounts-service.yaml