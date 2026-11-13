# Coding Standards (draft, TO-BE alignment)

- Package by feature module (target) vs current `controller/service/entity` (AS-IS).
- MapStruct for DTO mapping; avoid duplicating mapping logic in managers.
- Never commit secrets; use environment variables.
- Exception types per domain area (existing pattern under `shared.exception`).
