#bin/bash

if [ "$#" -ne 2 ]; then
  echo "Usage: $0 <pod-prefix> <port>"
  exit 1
fi

POD_NAME=$1
PORT=$2

FULL_POD_NAME=$(kubectl get pods --no-headers -o custom-columns=":metadata.name" | grep "^$POD_NAME" | head -n 1)

if [ -z "$FULL_POD_NAME" ]; then
  echo "No pod found matching the name $POD_NAME"
  exit 1
fi

kubectl port-forward "$FULL_POD_NAME" "$PORT":"$PORT"
