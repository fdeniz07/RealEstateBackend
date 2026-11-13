# Error Handling

AS-IS: `GlobalExceptionHandler` → `ApiErrorResponse` + `FriendlyMessage` with localization utilities.

Clients should parse stable error codes/messages from response body — field names per `ApiErrorResponse` source (TBD export in OpenAPI).
