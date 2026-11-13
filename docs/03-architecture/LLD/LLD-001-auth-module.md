# LLD — Authentication Module (proposal)

Components: `AuthController`, `AuthManager`, `JWTUtils`, `JWTAuthenticationFilter`, `UserDetailsServiceImpl`.

TO-BE: extract `AuthenticateUserUseCase` port; domain `Credentials` VO.

```mermaid
classDiagram
  class AuthController
  class AuthManager
  class JWTUtils
  class JWTAuthenticationFilter
  AuthController --> AuthManager
  JWTAuthenticationFilter --> JWTUtils
```

**Figure RE-FIG-LLD-001** — AS-IS auth components.
