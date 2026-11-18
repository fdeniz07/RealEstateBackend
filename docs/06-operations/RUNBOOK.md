# Runbook (draft)

**Operations architecture:** [OBSERVABILITY-RELIABILITY-ARCHITECTURE.md](../03-architecture/operations/OBSERVABILITY-RELIABILITY-ARCHITECTURE.md).

1. Verify PostgreSQL reachable on configured port.
2. Start jar with `spring.profiles.active`.
3. Hit `/actuator/health` if exposed.
4. Verify Swagger loads.

Troubleshooting: [TROUBLESHOOTING.md](TROUBLESHOOTING.md).
