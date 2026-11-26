#!/bin/sh
# Fail-fast contract for Compose profile `test` (integration-test runner).
set -eu

if [ -z "${SPRING_DATASOURCE_URL:-}" ]; then
  echo "SPRING_DATASOURCE_URL is required (must target real_estate_test_db)." >&2
  exit 1
fi

# Extract database name (last path segment, strip query string).
db_path="${SPRING_DATASOURCE_URL##*/}"
db_name="${db_path%%\?*}"

if [ "$db_name" = "real_estate_db" ]; then
  echo "Refusing to run tests: development database real_estate_db is forbidden." >&2
  exit 1
fi

case "${SPRING_DATASOURCE_URL}" in
  *real_estate_test_db*) ;;
  *)
    echo "Refusing to run tests: SPRING_DATASOURCE_URL must contain real_estate_test_db (got database '${db_name}')." >&2
    exit 1
    ;;
esac

if [ -z "${SPRING_DATASOURCE_PASSWORD:-}" ]; then
  echo "SPRING_DATASOURCE_PASSWORD is required." >&2
  exit 1
fi

if [ -z "${JWT_SECRET:-}" ]; then
  echo "JWT_SECRET is required (map from JWT_SECRET_TEST; do not use development JWT_SECRET)." >&2
  exit 1
fi

export SPRING_PROFILES_ACTIVE=test
# test-runner image: maven:3.9.9-eclipse-temurin-17 (system mvn; wrapper not required in container)
exec mvn -B test