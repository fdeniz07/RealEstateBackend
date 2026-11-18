-- Idempotent helper for DB-INIT-P1 (manual use only).
-- Run against an EXISTING Postgres instance when the Docker volume was created before this database existed.
-- Does NOT run automatically on existing volumes (see LOCAL_ENV.md §7).
--
-- Example (replace user/host/port with your values):
--   psql -h 127.0.0.1 -p 5433 -U db_user -d real_estate_db -f docs/04-development/postgres/create-real-estate-test-db.sql

SELECT format('CREATE DATABASE %I OWNER %I ENCODING ''UTF8''', 'real_estate_test_db', current_user)
WHERE NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = 'real_estate_test_db')\gexec