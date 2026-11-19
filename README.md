# sync-sandbox


A tiny greeter, nothing more.
Disposable sandbox validating the security fix sync between a public repository and its private `-security-fixes` copy. The sync itself lives in `vaadin/PSIRT` under `sync/`. This repo plays the public product repository; `vaadin/sync-sandbox-security-fixes` plays the private copy.

The validation scenarios and their results are recorded here as they run.

## Scenarios

| # | Scenario | Expected | Result |
|---|----------|----------|--------|
| a | PR merged in public only | private fast forwarded | ✅ 1.0 fast forwarded, main rebased |
| b | Fix PR in private, then another public merge | fix rebased on top | ✅ and Private-Only reordered back on top |
| c | CVE day: App fast forwards private main to public without a PR | accepted, next sync drops the fix | ✅ tag v1.1.0; force push to public rejected even for the App |
| d | Public merge conflicting with the pending fix | nothing pushed, PSIRT issue opened | ✅ PSIRT#195, second run commented it |
| e | Private-Only dependabot deletion | stays topmost, auto resolved | ✅ |
| f | Publish workflow in the private copy | skipped by the repository guard | ✅ Verify runs, Publish skipped |

## Gotchas found while validating

- A freshly created repo can have event triggered workflows wedged: pushes and PRs produce no runs until Actions is toggled off and on (`gh api -X PUT repos/<r>/actions/permissions -F enabled=false` then `true`). Both sandbox repos needed it.
- Resolving a sync conflict by hand ends in a force push that only the App can do: the private History protection ruleset has no human bypass. Either resolutions are pushed with an App token, or the ruleset gets a team as bypass actor. Decision pending.
- A hand resolved rebase shipped conflict markers to public main (fixed in #6). Exactly the mistake the pending release gate with human confirmation of outgoing commits exists to catch.

## Matrix v2: the gated publish (2026-11-19)

Validated with the flow shaped rulesets replicated here (PR + checks + linear history + license/cla, merge queue on main, split so History protection has no bypass) and the two App model.

| # | Scenario | Result |
|---|----------|--------|
| g | Dispatch CVE publish, approver rejects | ✅ issue closed "Rejected", run failed, nothing pushed, key never used |
| h | Dispatch, second person approves | ✅ tag v1.2.0 + fast forward through PR, checks and merge queue bypasses |
| i | Publish App force pushes public | ✅ GH013 rejected, History protection has no bypass |
| j | Sync App pushes public | ✅ 403, not installed there any more |
| k | Public moves after the sha was built | ✅ gate fails pre approval with rebuild instructions, no issue opened |
| l | notify-publish filter | ✅ fires on the publish App's pushes (branch and tag), skipped on human pushes |

Extra gotcha found: a PR with auto merge enabled never entered the replicated merge queue here (queue stayed empty, PR BLOCKED); needs investigating before trusting the queue replica, flow's real queue is unaffected. The sandbox README edits after the ruleset switch are pushed with the publish App bypass for that reason.
