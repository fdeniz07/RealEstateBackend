---
document_id: RE-MON-001
title: Monitoring (Summary)
version: 0.1.0
status: draft
language: en
updated_at: 2026-11-18
---

# Monitoring

**Canonical:** [OBSERVABILITY-RELIABILITY-ARCHITECTURE.md](../03-architecture/operations/OBSERVABILITY-RELIABILITY-ARCHITECTURE.md). **Deployment:** [INFRASTRUCTURE-DEPLOYMENT-ARCHITECTURE.md](../03-architecture/infrastructure/INFRASTRUCTURE-DEPLOYMENT-ARCHITECTURE.md).

## AS-IS

- `spring-boot-starter-actuator` on classpath.
- `management` endpoint exposure largely **commented** in `application.yaml`.
- `GET /actuator/**` **permitAll** in security config.
- **No** Prometheus, Grafana, Loki, or OpenTelemetry deployment in repo.

## TO-BE / FUTURE

Micrometer + Prometheus, health groups `liveness`/`readiness`, OpenTelemetry traces — see NFR-OBS in [NFR-CATALOGUE.md](../02-requirements/NFR/NFR-CATALOGUE.md).