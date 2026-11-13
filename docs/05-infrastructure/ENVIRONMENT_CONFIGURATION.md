# Environment Configuration

Document **keys only** (AS-IS):

| Key area | Purpose |
|----------|---------|
| `spring.datasource.*` | PostgreSQL connection |
| `spring.jpa.hibernate.ddl-auto` | `update` on localhost profile |
| `cors.*` | Wide CORS |
| `backendapi.app.jwtSecret` | JWT signing — use env var in production |
| `backendapi.app.jwtExpressionMS` | Token TTL |
| `prettier.openapi.*` | springdoc server URLs |

Use profile-specific YAML; never commit real credentials to docs or git.
