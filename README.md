# GitHub Actions Java CI/CD Demo

A minimal Spring Boot application designed specifically to demonstrate a three-stage GitHub Actions CI/CD flow on GitHub-hosted runners.

## Demo flow

```text
Commit to dev
    |
    v
01 - Java CI and Publish Package
    |-- Checkout
    |-- JDK 17
    |-- CodeQL
    |-- Maven test
    |-- Checkstyle
    |-- SpotBugs
    |-- Maven package
    `-- Publish JAR to GitHub Packages
            |
            v
02 - Build and Publish Container
    |-- Download exact JAR from GitHub Packages
    |-- Docker build
    |-- Container smoke test
    `-- Push image to GHCR
            |
            v
03 - CLI Demo Deployment
    |-- Pull image
    |-- Start Docker container
    |-- Readiness check
    |-- curl /
    |-- curl /api/hello
    |-- curl /actuator/health
    |-- Keep running 500 seconds
    `-- Stop and remove container
```

## Branches

Recommended:

- `main` - default branch. Keep all three workflow YAML files here.
- `dev` - development branch. Commits to this branch trigger the CI pipeline.

`workflow_run` workflows must exist on the repository default branch to trigger.

## Local test

Requires Java 17 and Maven.

```bash
mvn clean verify
mvn spring-boot:run
```

Then:

```bash
curl http://localhost:8080/
curl http://localhost:8080/api/hello
curl http://localhost:8080/actuator/health
```

## GitHub setup

1. Create a repository named `github-actions-java-demo`.
2. Make `main` the default branch.
3. Push this source to `main`.
4. Create a `dev` branch from `main`.
5. Push any small source change to `dev`.
6. Open the GitHub Actions tab.
7. Watch:
   - `01 - Java CI and Publish Package`
   - `02 - Build and Publish Container`
   - `03 - CLI Demo Deployment`

The workflows use the automatically generated `GITHUB_TOKEN`; no custom secret is required for publishing packages from the same repository.

## Expected CLI demo

The deployment workflow prints an application page similar to:

```text
============================================================
           GITHUB ACTIONS CI/CD DEMO
============================================================
 Application       : Java Spring Boot Demo
 Environment       : DEV
 Version           : 1.0.x
 Commit            : abc1234
 Status            : RUNNING
 HTTP Port         : 8080
------------------------------------------------------------
 Message            : Deployment is healthy and reachable.
============================================================
```

and then a deployment status box before keeping the container active for 500 seconds.
