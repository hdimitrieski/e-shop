#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE DATABASE dbcatalog;
    GRANT ALL PRIVILEGES ON DATABASE dbcatalog TO dbuser;
    ALTER DATABASE dbcatalog SET TIMEZONE='Europe/Amsterdam';
EOSQL

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE DATABASE dborders;
    GRANT ALL PRIVILEGES ON DATABASE dborders TO dbuser;
    ALTER DATABASE dborders SET TIMEZONE='Europe/Amsterdam';
EOSQL

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE DATABASE keycloak;
    GRANT ALL PRIVILEGES ON DATABASE keycloak TO dbuser;
    ALTER DATABASE keycloak SET TIMEZONE='Europe/Amsterdam';
EOSQL
