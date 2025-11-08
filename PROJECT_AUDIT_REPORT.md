# Project Audit Report - Timesheet Cloud

**Date:** $(date)  
**Project:** Timesheet Cloud Microservices Application  
**Scope:** Complete project review including security, configuration, architecture, and code quality

---

## 🔴 CRITICAL SECURITY ISSUES

### 1. Hardcoded Credentials in Source Code
**Severity:** CRITICAL  
**Location:** Multiple files

- **`app/user-service/src/main/resources/application.yml`** (Line 6)
  - Hardcoded password: `grgjUHI9`
  - Database credentials should use environment variables

- **`app/timesheet-service/src/main/resources/application.yml`** (Line 6)
  - Hardcoded password: `rootpwd`
  
- **`app/leave-service/src/main/resources/application.yml`** (Line 6)
  - Hardcoded password: `grgjUHI9`

- **`app/user-service/credentials.md`** (Untracked file)
  - Contains actual email credentials
  - Should be removed and added to `.gitignore`

**Recommendation:** 
- Move all credentials to environment variables
- Use Kubernetes secrets or external secret management (Vault)
- Remove `credentials.md` from repository

### 2. Hardcoded JWT Secrets
**Severity:** HIGH  
**Location:** Application YAML files

- Default JWT secrets are hardcoded with weak fallback values
- Should use strong secrets from environment variables only

**Recommendation:**
- Remove default values, require `JWT_SECRET` environment variable
- Generate strong secrets using: `openssl rand -base64 32`

### 3. Insecure HTTPS Configuration
**Severity:** MEDIUM  
**Location:** `app/timesheet-frontend/src/api/LeaveService.js` (Line 3)

- Uses `https://localhost:8082` which is incorrect (should be `http://`)
- Mixed HTTP/HTTPS usage across frontend

---

## ⚠️ CONFIGURATION ISSUES

### 4. Port Inconsistencies
**Severity:** MEDIUM  
**Issues:**

- **README.md** states user-service runs on port `8080`
- **application.yml** configures user-service on port `7070`
- **Frontend** (`authService.js`) points to `7070` (correct)
- **Dockerfile** exposes port `7070` (correct)

**Recommendation:** Update README to reflect actual port `7070`

### 5. Database Configuration Mismatch
**Severity:** MEDIUM

- **`docker-compose.yml`** uses MySQL but services are configured for PostgreSQL
- All services share the same database (`dashboard_app`) instead of separate databases
- Docker compose references non-existent service paths

**Recommendation:**
- Update `docker-compose.yml` to use PostgreSQL
- Consider separate databases per service for better isolation
- Fix service paths in docker-compose

### 6. Missing Build Configuration
**Severity:** LOW

- **`timesheet-service/pom.xml`** - Missing `<build>` section with Spring Boot plugin
- **`leave-service/pom.xml`** - Missing build plugins (JaCoCo, OWASP, etc.)
- Build scripts may fail without proper plugin configuration

**Recommendation:** Add build sections similar to `user-service/pom.xml`

### 7. Dependency Check Suppression File Path
**Severity:** LOW  
**Location:** `app/user-service/pom.xml` (Line 182)

- References `dependency-check-suppressions.xml` but file is in `app/` directory
- Path may not resolve correctly when building from service directory

**Recommendation:** Use absolute path or move file to service directory

---

## 📦 CODE QUALITY & STRUCTURE ISSUES

### 8. Nested Duplicate Directory
**Severity:** MEDIUM  
**Location:** `app/user-service/user-service/`

- Contains duplicate/unused Spring Boot project
- Uses Java 17 (parent uses Java 21)
- Conflicts with parent project structure

**Recommendation:** Remove the nested `user-service/user-service/` directory

### 9. Java Version Inconsistencies
**Severity:** LOW

- **user-service:** Java 21
- **timesheet-service:** Not specified (inherits from parent)
- **leave-service:** Not specified (inherits from parent)
- **README:** States Java 17
- **Nested user-service:** Java 17

**Recommendation:** 
- Standardize on Java 21 across all services
- Update README to reflect Java 21

### 10. Spring Boot Version Inconsistencies
**Severity:** LOW

- **user-service:** Spring Boot 3.3.3
- **timesheet-service:** Spring Boot 3.3.0
- **leave-service:** Spring Boot 3.3.3

**Recommendation:** Standardize on Spring Boot 3.3.3

### 11. Frontend Hardcoded URLs
**Severity:** MEDIUM  
**Location:** Multiple frontend files

- `authService.js` - Hardcoded localhost URLs
- `LeaveService.js` - Hardcoded URLs with incorrect protocol
- `signup.js` - Hardcoded localhost URL

**Recommendation:** 
- Use environment variables for API endpoints
- Create `.env` file with different values for dev/prod
- Update React to use `REACT_APP_API_URL` environment variables

---

## 🏗️ ARCHITECTURE ISSUES

### 12. Database Design
**Severity:** LOW

- All services share same database (`dashboard_app`)
- Better isolation would be achieved with separate databases per service

**Recommendation:** Consider separate databases for better microservice isolation

### 13. Docker Compose Configuration
**Severity:** MEDIUM

- References non-existent paths (`./user-profile-service`, `./timesheet-service`, `./frontend`)
- Uses MySQL instead of PostgreSQL
- Service dependencies may not work correctly

**Recommendation:** 
- Update paths to correct locations
- Switch to PostgreSQL
- Add leave-service to docker-compose

### 14. Missing Environment Configuration
**Severity:** LOW

- Frontend has no environment variable configuration
- No `.env.example` file
- No documentation for required environment variables

**Recommendation:**
- Create `.env.example` with all required variables
- Add environment variable documentation
- Update frontend to use environment variables

---

## 🔧 CI/CD ISSUES

### 15. GitHub Actions Configuration
**Severity:** LOW  
**Location:** `CICD/github-actions-user.yml`

- Build command runs `mvn -B clean verify` from root
- No service-specific build paths
- Missing context for multi-service builds
- Security scan may fail due to missing suppression file path

**Recommendation:**
- Add working directory context
- Specify correct paths for multi-service builds
- Add job for each service or use matrix strategy

### 16. Kubernetes Deployment Issues
**Severity:** MEDIUM  
**Location:** `kubernetes/user-service/user-deployment.yaml`

- Contains invalid YAML (lines 61-66 have malformed hostAliases)
- References wrong image (Flask app instead of Java service)
- Wrong container port (5000 instead of 7070)
- Wrong namespace (`apps` instead of `app`)

**Recommendation:** Fix Kubernetes deployment configurations

---

## 📝 DOCUMENTATION ISSUES

### 17. Inconsistent Documentation
**Severity:** LOW

- Multiple README files with conflicting information
- Port numbers don't match actual configuration
- Java version mentioned in README doesn't match code
- Missing API documentation

**Recommendation:** 
- Consolidate and update documentation
- Ensure all ports/versions match actual configuration
- Add API documentation (Swagger/OpenAPI)

---

## 🐛 KNOWN BUGS

### 18. Signup Not Working
**Severity:** HIGH  
**Location:** `app/bugs.md`

- Documented issue: "signup isn't working: Error connecting to server"
- Needs investigation and fix

---

## ✅ POSITIVE FINDINGS

1. ✅ Good use of JWT authentication
2. ✅ Security best practices implemented (JWT filters, password encoding)
3. ✅ Actuator endpoints configured for monitoring
4. ✅ OWASP dependency check configured
5. ✅ JaCoCo code coverage configured
6. ✅ Docker containerization implemented
7. ✅ Kubernetes configurations present
8. ✅ CI/CD pipeline setup

---

## 📋 PRIORITY ACTION ITEMS

### Immediate (Fix Now):
1. Remove hardcoded credentials from `application.yml` files
2. Delete `credentials.md` file
3. Fix Kubernetes deployment YAML
4. Remove nested `user-service/user-service/` directory
5. Fix frontend HTTPS URL in `LeaveService.js`

### High Priority (This Week):
6. Move all credentials to environment variables
7. Standardize Java versions (21) and Spring Boot versions (3.3.3)
8. Update docker-compose.yml to use PostgreSQL
9. Fix frontend environment variable configuration
10. Investigate and fix signup bug

### Medium Priority (This Month):
11. Add build plugins to timesheet-service and leave-service
12. Update all README files for consistency
13. Fix GitHub Actions workflow paths
14. Consider database separation per service

### Low Priority (Nice to Have):
15. Add API documentation (Swagger)
16. Create `.env.example` files
17. Add integration tests
18. Improve error handling and logging

---

## 📊 SUMMARY STATISTICS

- **Total Issues Found:** 18
- **Critical:** 2
- **High:** 3
- **Medium:** 7
- **Low:** 6
- **Security Issues:** 3
- **Configuration Issues:** 5
- **Code Quality Issues:** 4
- **Architecture Issues:** 3
- **Documentation Issues:** 1
- **Known Bugs:** 1

---

## 🔒 SECURITY RECOMMENDATIONS SUMMARY

1. **Never commit credentials** - Use environment variables or secrets management
2. **Use strong JWT secrets** - Generate with proper tools, no defaults
3. **Implement secret rotation** - Regularly rotate database passwords and JWT secrets
4. **Add .gitignore rules** - Ensure credentials.md and other sensitive files are ignored
5. **Use HTTPS in production** - Configure proper SSL/TLS certificates
6. **Regular security scans** - Continue using OWASP dependency check
7. **Implement rate limiting** - Protect authentication endpoints
8. **Add input validation** - Ensure all user inputs are validated

---

*Report generated by automated project audit*




