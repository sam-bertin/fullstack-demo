# Backend PR Summary

## Context
- Jira key(s):
- Related ticket(s):
- Why this backend change is needed:

## What changed (backend)
- 
- 
- 

## API / contract impact
- Endpoints added/updated:
- Request DTO changes:
- Response DTO changes:
- Error mapping impact:

## How to validate
1. 
2. 
3. 

## Risks and rollback
- Risks:
- Rollback plan:

---

# Backend Definition of Done (PR Gate)

## General
- [ ] Branch is not `main`.
- [ ] PR targets `main`.
- [ ] Jira key is referenced in PR title or description.

## Architecture
- [ ] Layered architecture respected (Controller -> Service -> ServiceImpl -> Repository).
- [ ] No JPA entity exposed by REST endpoints.
- [ ] Mapping Entity <-> DTO handled outside controllers.

## API and validation
- [ ] `ApiResponse` contract respected (`success`, `message`, `data`, `errorCode`).
- [ ] Error mapping follows agreed rules.
- [ ] Input DTO validation implemented (`@Valid` + constraints).
- [ ] No sensitive fields exposed in responses.

## Security
- [ ] Auth/access rules checked on impacted endpoints.
- [ ] Password/secret handling follows project policy.

## Testing and quality
- [ ] Unit tests added/updated for impacted service logic.
- [ ] At least one error-path test covered.
- [ ] Backend lint/checkstyle passes.
- [ ] Backend build passes.
- [ ] CI checks are green.

## Documentation (mandatory)
- [ ] Task doc updated from `docs/TASK_DOC_TEMPLATE.md`.
- [ ] Decision log updated when technical choices were made.
- [ ] Backend docs updated (`docs/02_backend/`) when relevant.
- [ ] Jira-ready summary text prepared.

## Operational readiness
- [ ] Logs are sufficient for diagnosis.
- [ ] New env vars (if any) are documented.
- [ ] Manual verification steps executed and captured.

---

## Reviewer focus
- Priority review areas:
- Known limitations:
- Follow-up tickets:
