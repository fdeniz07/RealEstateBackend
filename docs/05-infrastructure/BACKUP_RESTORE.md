---
document_id: RE-BAK-001
title: Backup and Restore (Operations Proposal)
version: 0.1.0
status: draft
language: en
updated_at: 2026-11-18
---

# BACKUP_RESTORE (proposal)

**Canonical architecture:** [OBSERVABILITY-RELIABILITY-ARCHITECTURE.md](../03-architecture/operations/OBSERVABILITY-RELIABILITY-ARCHITECTURE.md) §9.

## AS-IS

- **No** automated `pg_dump`, WAL archive, or restore scripts in the repository.
- Local Compose volume `realestate_pg_data` is **not** a backup strategy.

## TO-BE (PROPOSED — not implemented)

PostgreSQL **logical** backup (`pg_dump`) on a schedule; encrypted off-site storage; **restore drill** quarterly (frequency **TBD** approval).

Physical backup / PITR — **TBD** if RPO requirements justify complexity.

**Do not** treat this file alone as an approved operational procedure until runbooks and ownership are agreed.