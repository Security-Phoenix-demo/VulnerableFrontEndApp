# VulnerableFrontEndApp

Deliberately vulnerable demo monorepo for Phoenix Security SCA demos. Every module pins dependency versions with
known advisories so that the Libraries tab and AI Remedy have something to work on. Do not deploy.

| Folder | Stack | Manifest |
|---|---|---|
| `web/` | React front end (npm) | `package.json` + `package-lock.json` |
| `api/` | Python (Django/Flask) | `requirements.txt` |
| `gateway/` | Go | `go.mod` / `go.sum` |
| `java-service/` | Java (Maven) | `pom.xml` |
| `ruby/` | Ruby (Bundler) | `Gemfile` / `Gemfile.lock` |
| `php/` | PHP (Composer) | `composer.json` / `composer.lock` |
