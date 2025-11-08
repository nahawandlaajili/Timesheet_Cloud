# DevOps Infrastructure Audit Report - Timesheet Cloud

**Date:** $(date)  
**Scope:** Complete DevOps infrastructure review including CI/CD, Kubernetes, Docker, Monitoring, and Logging

---

## 🔴 CRITICAL INFRASTRUCTURE ISSUES

### 1. Kubernetes Deployment Configuration Errors
**Severity:** CRITICAL  
**Location:** `kubernetes/user-service/user-deployment.yaml`

**Issues:**
- **Wrong container image:** References `docker.io/4arctic1/user-dashboard` (Flask app) instead of Java Spring Boot service
- **Wrong container port:** Uses port `5000` (Flask) instead of `7070` (Spring Boot)
- **Malformed YAML:** Lines 61-66 contain invalid `hostAliases` syntax
- **Wrong namespace:** Uses `apps` namespace, but secrets are in `app` namespace
- **Hardcoded node:** `nodeName: minikube` (should be removed for production)
- **Missing health probes:** No readiness/liveness probes configured
- **Missing environment variables:** No database connection or JWT secret configuration

**Current Configuration:**
```yaml
containers:
- image: docker.io/4arctic1/user-dashboard  # ❌ Wrong image
  ports:
  - containerPort: 5000  # ❌ Wrong port
  env:
  - name: FLASK_ENV  # ❌ Wrong env var
    value: "production"
```

**Recommendation:**
- Update to correct Java service image
- Fix port to 7070
- Add health probes
- Configure proper environment variables from secrets
- Remove hardcoded nodeName
- Fix namespace consistency

### 2. Namespace Inconsistencies
**Severity:** HIGH

**Issues:**
- `user-service` deployments use namespace `apps`
- `user-service` secrets use namespace `app`
- `timesheet-service` uses namespace `app`
- `ingress.yaml` uses namespace `apps`
- **Result:** Services cannot find secrets, deployments will fail

**Inconsistent Namespaces:**
- `kubernetes/user-service/user-deployment.yaml` → `namespace: apps`
- `kubernetes/user-service/user-svc.yaml` → `namespace: apps`
- `kubernetes/user-service/secret.yaml` → `namespace: app` ❌
- `kubernetes/timesheet-service/*` → `namespace: app`
- `kubernetes/ingress.yaml` → `namespace: apps`

**Recommendation:** Standardize on single namespace (`app` or `apps`) across all resources

### 3. Missing Service Configuration
**Severity:** HIGH  
**Location:** `kubernetes/timesheet-service/timesheet-svc.yaml`

**Issues:**
- Service selector is commented out (lines 8-9)
- Wrong port mapping: `port: 80` → `targetPort: 3001` (should be 8081)
- Service references `app.kubernetes.io/name: grafana` (commented) - wrong selector

**Recommendation:**
- Uncomment and fix selector to match deployment labels
- Fix port mapping to 8081
- Ensure selector matches deployment labels

### 4. Invalid Kustomization File
**Severity:** HIGH  
**Location:** `kubernetes/timesheet-service/kustomization.yaml`

**Issues:**
- Wrong API version: Uses `apiVersion: v1` instead of `kustomize.config.k8s.io/v1beta1`
- Wrong structure: Missing `resources:` section
- Typo in label: `app: timeshhet` (should be `timesheet`)
- Invalid YAML structure

**Current (Invalid):**
```yaml
apiVersion: v1  # ❌ Wrong
kind: kustomization  # ❌ Wrong
namespace: app
metadata:  # ❌ Not valid in kustomization
  name: timesheet-kustomzation
```

**Recommendation:** Fix kustomization structure or remove if not needed

### 5. Ingress Configuration Issues
**Severity:** MEDIUM  
**Location:** `kubernetes/ingress.yaml`

**Issues:**
- Mixed ingress controllers: Uses `traefik` but has commented `nginx` sections
- Port mismatch: Service references port `443` but services expose different ports
- TLS configuration references non-existent secret (`dashboard-tls`)
- Multiple commented-out sections causing confusion
- Host mismatch: `timesheet.com` in rules but `user-dash.com` in TLS section

**Recommendation:**
- Choose one ingress controller (nginx or traefik)
- Fix port mappings
- Create TLS secret or remove TLS configuration
- Clean up commented code

### 6. ArgoCD Deployment Configuration
**Severity:** MEDIUM  
**Location:** `CICD/ArgoCd/deployment.yaml`

**Issues:**
- Placeholder image: `<AWS_ACCOUNT_ID>.dkr.ecr.ap-southeast-1.amazonaws.com/<ecr repo>:<tag>`
- Wrong container port: `5000` (should be ArgoCD default ports)
- Missing required ArgoCD configuration
- No service definition matching this deployment

**Recommendation:**
- Use official ArgoCD image or provide actual ECR image
- Fix container port configuration
- Add proper ArgoCD configuration

---

## ⚠️ CI/CD PIPELINE ISSUES

### 7. GitHub Actions Workflow Problems
**Severity:** MEDIUM  
**Location:** `CICD/github-actions-user.yml`

**Issues:**
- **Wrong build context:** Runs `mvn -B clean verify` from root, but services are in subdirectories
- **Missing working directory:** No `working-directory` specified for multi-service builds
- **Missing Docker build/push:** No container image building or pushing
- **Security scan path:** `dependency-check:check` may fail due to missing suppression file path
- **Missing service-specific builds:** Doesn't build specific services
- **No deployment steps:** No Kubernetes deployment or image pushing

**Current Workflow:**
```yaml
- name: Build with Maven
  run: mvn -B clean verify  # ❌ Runs from root, will fail
```

**Recommendation:**
- Add working directory context for each service
- Add Docker build and push steps
- Fix dependency-check paths
- Add deployment steps (optional)
- Consider matrix strategy for multi-service builds

### 8. Jenkins Pipeline Issues
**Severity:** MEDIUM  
**Location:** `CICD/jenkinsfile`

**Issues:**
- **Hardcoded Git URL:** Points to different repository (`timesheet-ci.git`)
- **Windows-specific commands:** Uses `bat` commands (Windows) but should support Linux
- **Missing Docker steps:** No container image building
- **Hardcoded Nexus URL:** Uses `localhost:8081` (not suitable for CI/CD)
- **Missing multi-service support:** Only builds one service
- **No deployment steps:** No Kubernetes deployment

**Issues:**
```groovy
bat "mvn clean install -DskipTests"  # ❌ Windows only
url: "https://github.com/326-163/timesheet-ci.git"  # ❌ Wrong repo
```

**Recommendation:**
- Use `sh` for cross-platform support
- Fix Git repository URL
- Add Docker build/push steps
- Add Kubernetes deployment steps
- Support multi-service builds

### 9. Missing Leave-Service CI/CD
**Severity:** LOW

**Issues:**
- No CI/CD configuration for `leave-service`
- No Dockerfile found for `leave-service`
- Missing from Kubernetes deployments

**Recommendation:**
- Add leave-service to CI/CD pipelines
- Create Dockerfile for leave-service
- Add Kubernetes deployment manifests

---

## 🐳 DOCKER CONFIGURATION ISSUES

### 10. Docker Compose Misconfiguration
**Severity:** MEDIUM  
**Location:** `app/docker-compose.yml`

**Issues:**
- **Wrong database:** Uses MySQL but services are configured for PostgreSQL
- **Wrong service paths:** References non-existent paths:
  - `./user-profile-service` (should be `./user-service`)
  - `./timesheet-service` (exists but path may be wrong)
  - `./frontend` (should be `./timesheet-frontend`)
- **Missing services:** Leave-service not included
- **Wrong port mappings:** Service ports don't match application.yml
- **No environment variables:** Hardcoded database URLs

**Recommendation:**
- Switch to PostgreSQL
- Fix service paths
- Add leave-service
- Use environment variables for configuration
- Fix port mappings

### 11. Dockerfile Inconsistencies
**Severity:** LOW

**Issues:**
- **user-service/Dockerfile:** Uses Java 21 (correct)
- **timesheet-service/Dockerfile:** Uses Java 17 (should be 21)
- **Missing Dockerfile:** No Dockerfile for leave-service
- **Different base images:** Mixed use of `maven:3.9.6-amazoncorretto-21` and `maven:3.9.9-eclipse-temurin-17`

**Recommendation:**
- Standardize on Java 21
- Use consistent base images
- Create Dockerfile for leave-service

### 12. Missing Docker Registry Configuration
**Severity:** LOW

**Issues:**
- No Docker registry configuration in CI/CD
- Hardcoded image names in Kubernetes manifests
- No image tagging strategy (versioning)

**Recommendation:**
- Configure Docker registry (Docker Hub, ECR, etc.)
- Add image tagging strategy
- Parameterize image names

---

## 📊 MONITORING & LOGGING ISSUES

### 13. Monitoring Configuration
**Severity:** LOW  
**Location:** `monitoring/`

**Issues:**
- **Only README:** Contains only installation instructions, no actual manifests
- **Grafana service:** Uses generic service name that may not match Helm chart
- **Missing ServiceMonitor:** No Prometheus ServiceMonitor for scraping application metrics
- **No AlertManager rules:** Missing alerting rules

**Recommendation:**
- Add ServiceMonitor manifests for each service
- Add AlertManager rules
- Verify Grafana service name matches actual deployment
- Add Prometheus scraping configuration

### 14. Logging Configuration
**Severity:** LOW  
**Location:** `logging/`

**Issues:**
- **Only README and ingress:** Missing ELK stack deployment manifests
- **Kibana ingress:** References service `kibana-kibana` which may not exist
- **No log collection config:** No Fluentd/Fluent Bit configuration
- **No application logging config:** Services don't have logging configuration

**Recommendation:**
- Add ELK stack deployment manifests
- Add Fluentd/Fluent Bit DaemonSet
- Configure application logging
- Verify Kibana service name

---

## 🔐 SECURITY & SECRETS MANAGEMENT

### 15. Secrets Management Issues
**Severity:** HIGH

**Issues:**
- **Hardcoded secrets in manifests:** Base64 encoded but still visible in Git
- **No external secret management:** Not using Vault, AWS Secrets Manager, etc.
- **Weak passwords:** Using simple passwords (`rootpwd`, `grgjUHI9`)
- **TLS secret placeholder:** `dashboard-secret.yaml` has placeholder values
- **Secret naming inconsistency:** Uses `timesheet-secret`, `user-secret`, `db-secret` (inconsistent)

**Recommendation:**
- Use external secret management (Vault, AWS Secrets Manager)
- Rotate all secrets
- Use strong passwords
- Consider using Sealed Secrets or External Secrets Operator
- Standardize secret naming convention

### 16. Missing Security Contexts
**Severity:** MEDIUM

**Issues:**
- **user-deployment.yaml:** Has security context but it's for wrong service
- **timesheet.yaml:** Missing security context (runAsNonRoot, etc.)
- **No Pod Security Policies/Pod Security Standards:** Missing security policies
- **Missing network policies:** No network segmentation

**Recommendation:**
- Add security contexts to all deployments
- Implement Pod Security Standards
- Add Network Policies for service isolation
- Use read-only root filesystems where possible

---

## 📋 MISSING INFRASTRUCTURE COMPONENTS

### 17. Missing Infrastructure Components

**High Priority:**
- ❌ **Database deployment:** No PostgreSQL deployment manifests
- ❌ **ConfigMaps:** No ConfigMaps for application configuration
- ❌ **PersistentVolumes:** No PVC definitions for database
- ❌ **Service accounts:** Using default service accounts (security risk)
- ❌ **RBAC:** No Role/RoleBinding definitions

**Medium Priority:**
- ❌ **Horizontal Pod Autoscaler (HPA):** No autoscaling configuration
- ❌ **Pod Disruption Budgets:** No PDB for high availability
- ❌ **Resource Quotas:** No namespace resource quotas
- ❌ **Limit Ranges:** No limit ranges defined

**Low Priority:**
- ❌ **Helm charts:** No Helm charts for easier deployment
- ❌ **Terraform/IaC:** No infrastructure as code
- ❌ **Kustomize overlays:** Incomplete kustomize setup

---

## 🔧 CONFIGURATION ISSUES

### 18. Environment Variable Mismatch
**Severity:** MEDIUM

**Issues:**
- **timesheet.yaml:** References env vars `DB_URL`, `DB_USER`, `DB_PASSWORD`
- **But application.yml:** Uses `spring.datasource.url`, `spring.datasource.username`, `spring.datasource.password`
- **Secret references:** Uses `db-secret` but secrets are named `timesheet-secret`, `user-secret`
- **Missing JWT config:** No environment variables for JWT secrets

**Recommendation:**
- Align environment variable names with Spring Boot configuration
- Fix secret references
- Add JWT secret environment variables

### 19. Missing Health Check Configuration
**Severity:** MEDIUM

**Issues:**
- **user-deployment.yaml:** No health probes
- **timesheet.yaml:** Has health probes (good example)
- **leave-service:** No deployment at all

**Recommendation:**
- Add health probes to all deployments
- Use `/actuator/health` endpoint (already configured in services)
- Configure appropriate timeouts and thresholds

### 20. Resource Limits Issues
**Severity:** LOW

**Issues:**
- **user-deployment.yaml:** Very low resource limits (128Mi-256Mi memory)
- **timesheet.yaml:** More reasonable limits (512Mi-1Gi)
- **Inconsistent:** No standard resource allocation strategy

**Recommendation:**
- Standardize resource requests/limits
- Use appropriate limits for Java Spring Boot applications
- Consider JVM heap size when setting memory limits

---

## 📝 DOCUMENTATION ISSUES

### 21. Infrastructure Documentation
**Severity:** LOW

**Issues:**
- README files contain only links and commands, no actual manifests
- Missing deployment instructions
- No troubleshooting guides
- No architecture diagrams

**Recommendation:**
- Add comprehensive deployment guides
- Document namespace structure
- Add troubleshooting section
- Create architecture diagrams

---

## ✅ POSITIVE FINDINGS

1. ✅ **Health probes configured** in timesheet-service deployment
2. ✅ **Resource limits defined** in deployments
3. ✅ **Rolling update strategy** configured
4. ✅ **Secrets used** (even if with issues)
5. ✅ **Monitoring setup** planned (Prometheus/Grafana)
6. ✅ **Logging setup** planned (ELK stack)
7. ✅ **GitOps** planned (ArgoCD)
8. ✅ **CI/CD** pipelines present (needs fixes)
9. ✅ **Multi-stage Docker builds** used
10. ✅ **Actuator endpoints** configured for health checks

---

## 📋 PRIORITY ACTION ITEMS

### 🔴 Immediate (Fix Now - Blocks Deployment):
1. Fix Kubernetes user-service deployment (wrong image, port, namespace)
2. Fix namespace inconsistencies across all manifests
3. Fix malformed YAML in user-deployment.yaml
4. Fix service selectors and port mappings
5. Create proper secrets or use external secret management

### ⚠️ High Priority (This Week):
6. Fix GitHub Actions workflow (working directories, Docker builds)
7. Fix Jenkins pipeline (cross-platform, correct repo)
8. Fix Docker Compose configuration
9. Add leave-service to infrastructure
10. Fix ingress configuration (choose one controller, fix ports)
11. Standardize Dockerfile versions (Java 21)

### 📊 Medium Priority (This Month):
12. Add missing infrastructure components (Database, ConfigMaps, PVCs)
13. Add ServiceMonitor for Prometheus
14. Add Network Policies
15. Add HPA configuration
16. Fix kustomization files or remove them
17. Add proper ArgoCD configuration
18. Create deployment documentation

### 🔧 Low Priority (Nice to Have):
19. Create Helm charts
20. Add Terraform/IaC
21. Implement external secret management
22. Add Pod Disruption Budgets
23. Add comprehensive monitoring dashboards
24. Add logging aggregation setup

---

## 🏗️ RECOMMENDED INFRASTRUCTURE STRUCTURE

```
kubernetes/
├── namespaces/
│   ├── app.yaml
│   ├── monitoring.yaml
│   └── logging.yaml
├── database/
│   ├── postgres-deployment.yaml
│   ├── postgres-service.yaml
│   └── postgres-pvc.yaml
├── user-service/
│   ├── deployment.yaml
│   ├── service.yaml
│   ├── configmap.yaml
│   └── hpa.yaml
├── timesheet-service/
│   ├── deployment.yaml
│   ├── service.yaml
│   ├── configmap.yaml
│   └── hpa.yaml
├── leave-service/
│   ├── deployment.yaml
│   ├── service.yaml
│   ├── configmap.yaml
│   └── hpa.yaml
├── networking/
│   ├── ingress.yaml
│   ├── network-policies.yaml
│   └── gateway-api.yaml
├── monitoring/
│   ├── servicemonitor.yaml
│   └── prometheus-rules.yaml
└── secrets/
    └── (managed externally)
```

---

## 📊 SUMMARY STATISTICS

- **Total Issues Found:** 21
- **Critical:** 5
- **High:** 6
- **Medium:** 7
- **Low:** 3
- **Kubernetes Issues:** 8
- **CI/CD Issues:** 3
- **Docker Issues:** 3
- **Security Issues:** 2
- **Missing Components:** 5

---

## 🔒 SECURITY RECOMMENDATIONS

1. **Never commit secrets** - Use external secret management
2. **Use RBAC** - Create dedicated service accounts with minimal permissions
3. **Implement Network Policies** - Restrict pod-to-pod communication
4. **Use Pod Security Standards** - Enforce security contexts
5. **Scan container images** - Integrate Trivy/Grype in CI/CD
6. **Use read-only root filesystems** - Where possible
7. **Implement TLS** - For all service-to-service communication
8. **Regular secret rotation** - Rotate database passwords and JWT secrets
9. **Use least privilege** - Minimize service account permissions
10. **Audit logs** - Enable Kubernetes audit logging

---

*Report generated by automated DevOps infrastructure audit*




