#!/bin/bash
# Runs only when the Postgres data directory is first initialized (empty volume).
# Does NOT run on existing developer volumes — see LOCAL_ENV.md for one-time CREATE DATABASE.

set -euo pipefail

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<EOSQL
CREATE DATABASE real_estate_test_db OWNER ${POSTGRES_USER} ENCODING 'UTF8';
CREATE DATABASE real_estate_ci_test_db OWNER ${POSTGRES_USER} ENCODING 'UTF8';
EOSQL