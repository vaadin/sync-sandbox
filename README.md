# sync-sandbox


A tiny greeter, nothing more.
Disposable sandbox validating the security fix sync between a public repository and its private `-security-fixes` copy. The sync itself lives in `vaadin/PSIRT` under `sync/`. This repo plays the public product repository; `vaadin/sync-sandbox-security-fixes` plays the private copy.

The validation scenarios and their results are recorded here as they run.

## Scenarios

| # | Scenario | Expected | Result |
|---|----------|----------|--------|
| a | PR merged in public only | private fast forwarded | pending |
| b | Fix PR in private, then another public merge | fix rebased on top | pending |
| c | CVE day: App fast forwards private main to public without a PR | accepted, next sync drops the fix | pending |
| d | Public merge conflicting with the pending fix | nothing pushed, PSIRT issue opened | pending |
| e | Private-Only dependabot deletion | stays topmost, auto resolved | pending |
| f | Publish workflow in the private copy | skipped by the repository guard | pending |
