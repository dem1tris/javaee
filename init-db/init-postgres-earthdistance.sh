#!/bin/bash

set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE EXTENSION cube;
    CREATE EXTENSION earthdistance;
    SELECT set_config('log_statement', 'all', true);
EOSQL

# docker run -e POSTGRES_DB=main -e POSTGRES_USER=root -e POSTGRES_PASSWORD=12341234 -d -p 5432:5432 postgres