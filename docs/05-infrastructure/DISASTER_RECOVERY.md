---
document_id: RE-DR-001
title: Disaster Recovery (Operations Proposal)
version: 0.1.0
status: draft
language: en
updated_at: 2026-11-18
---

# DISASTER_RECOVERY (proposal)

**Canonical architecture:** [OBSERVABILITY-RELIABILITY-ARCHITECTURE.md](../03-architecture/operations/OBSERVABILITY-RELIABILITY-ARCHITECTURE.md) §12–13.

## AS-IS

- **RPO / RTO:** **TBD** — no approved numeric targets in `DECISION_LOG`.
- **No** DR environment, failover, or tested runbook in repository.

## TO-BE (PROPOSED)

High-level sequence: restore PostgreSQL → restore object storage (when used) → redeploy stateless API instances → health and smoke tests.

Numeric RPO/RTO (e.g. 15 minutes / 4 hours) are **discussion candidates only** until PO/engineering approves and **restore tests** validate them.

See [INFRASTRUCTURE-DEPLOYMENT-ARCHITECTURE.md](../03-architecture/infrastructure/INFRASTRUCTURE-DEPLOYMENT-ARCHITECTURE.md) for deployment context.