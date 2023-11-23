UPDATE countries
SET create_at = CURRENT_TIMESTAMP
WHERE create_at IS NULL;