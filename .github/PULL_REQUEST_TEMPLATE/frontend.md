# Frontend PR Summary

## Context
- Jira key(s):
- Related ticket(s):
- Why this frontend change is needed:

## What changed (frontend)
- 
- 
- 

## UI / state / API impact
- Screens/components impacted:
- State management impact:
- API client/interceptor impact:
- Validation/messages impact:

## How to validate
1. 
2. 
3. 

## Risks and rollback
- Risks:
- Rollback plan:

---

# Frontend Definition of Done (PR Gate)

## General
- [ ] Branch is not `main`.
- [ ] PR targets `main`.
- [ ] Jira key is referenced in PR title or description.

## Architecture and typing
- [ ] Feature-based structure respected.
- [ ] Components remain functional.
- [ ] No `any` introduced in impacted code.
- [ ] Shared logic extracted to hooks/services where relevant.

## UX, validation, and security
- [ ] Forms validated with agreed schema approach.
- [ ] Frontend/backend validation stays consistent for impacted flows.
- [ ] Auth/session handling verified on impacted routes.
- [ ] No sensitive data exposed in UI/state/storage.

## API integration
- [ ] Axios client/interceptors still align with backend contract.
- [ ] `ApiResponse` and error handling conventions are respected.
- [ ] 401/403/validation error paths handled in UI.

## Testing and quality
- [ ] Tests added/updated for impacted components/hooks.
- [ ] At least one error-path/UI-edge case covered.
- [ ] Frontend lint passes.
- [ ] Frontend build passes.
- [ ] CI checks are green.

## Documentation (mandatory)
- [ ] Task doc updated from `docs/TASK_DOC_TEMPLATE.md`.
- [ ] Decision log updated when technical choices were made.
- [ ] Frontend docs updated (`docs/03_frontend/`) when relevant.
- [ ] Jira-ready summary text prepared.

## Operational readiness
- [ ] New env vars (if any) are documented.
- [ ] Manual verification steps executed and captured.

---

## Reviewer focus
- Priority review areas:
- Known limitations:
- Follow-up tickets:
