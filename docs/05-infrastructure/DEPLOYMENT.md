# Deployment (proposal)

```mermaid
flowchart LR
  DEV[Developer] --> CI[CI pipeline TBD]
  CI --> IMG[Container image]
  IMG --> RUN[Runtime 8080]
  RUN --> DB[(PostgreSQL)]
```

Docker listed as planned in README — not verified in repo.
