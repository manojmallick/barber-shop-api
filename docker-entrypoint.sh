#!/bin/sh

cd /run/secrets

export MONGO_DB_USERNAME=$(cat MONGO_DB_USERNAME)
export MONGO_DB_PASSWORD=$(cat MONGO_DB_PASSWORD)

cd /app

exec "$@"