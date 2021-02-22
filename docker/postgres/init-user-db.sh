#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE DATABASE dbcatalog;
    GRANT ALL PRIVILEGES ON DATABASE dbcatalog TO dbuser;
EOSQL

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE DATABASE dborders;
    GRANT ALL PRIVILEGES ON DATABASE dborders TO dbuser;
EOSQL
