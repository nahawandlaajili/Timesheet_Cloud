# java
 java brew install openjdk@21

# node js
brew install node
npm start
http://localhost:3001/


# maven
brew install maven

# k3s
 brew install k3d
 k3d cluster create dev-cluster --agents 2
 k3d cluster list


kubectl cluster-info

<!-- # mysql
brew install mysql@8.0
brew services start mysql@8.0
mysql_secure_installation -->

# Postgres 
brew install postgresql@15
brew services start postgresql@15
psql --version

<!-- ==> Summary
🍺  /opt/homebrew/Cellar/mysql@8.0/8.0.42: 319 files, 303.9MB
==> Running `brew cleanup mysql@8.0`...
Disable this behaviour by setting HOMEBREW_NO_INSTALL_CLEANUP.
Hide these hints with HOMEBREW_NO_ENV_HINTS (see `man brew`).
==> Caveats
==> mysql@8.0
We've installed your MySQL database without a root password. To secure it run:
    mysql_secure_installation

MySQL is configured to only allow connections from localhost by default

To connect run:
    mysql -u root

mysql@8.0 is keg-only, which means it was not symlinked into /opt/homebrew,
because this is an alternate version of another formula.

If you need to have mysql@8.0 first in your PATH, run:
  echo 'export PATH="/opt/homebrew/opt/mysql@8.0/bin:$PATH"' >> ~/.zshrc

For compilers to find mysql@8.0 you may need to set:
  export LDFLAGS="-L/opt/homebrew/opt/mysql@8.0/lib"
  export CPPFLAGS="-I/opt/homebrew/opt/mysql@8.0/include"

To restart mysql@8.0 after an upgrade:
  brew services restart mysql@8.0
Or, if you don't want/need a background service you can just run:
  /opt/homebrew/opt/mysql@8.0/bin/mysqld_safe --datadir\=/opt/homebrew/var/mysql -->

# Nexus (via docker)
 Create directory for Nexus data
- mkdir -p ~/nexus-data && chmod 777 ~/nexus-data
- docker pull sonatype/nexus3
- docker run -d -p 8081:8081 --name nexus \
  -v nexus-data:/nexus-data \
  sonatype/nexus3:latest

# Check logs
docker logs -f nexus


Access Nexus: http://localhost:8081
Default admin credentials:
- username: admin
- password: stored in cat /nexus-data/admin.password

Get Admin password:
- docker exec -it nexus cat /nexus-data/admin.password
pwd:saminahawand

# Jenkins
Install the latest LTS version: brew install jenkins-lts
Start the Jenkins service: brew services start jenkins-lts
Restart the Jenkins service: brew services restart jenkins-lts
Update the Jenkins version: brew upgrade jenkins-lts
Access Jenkins via: http://localhost:8080

Token: 114bb968fd9f594b3c2fcd93ac86db1715

# SonarQube
Start sonarQube cd ~/Downloads curl -O https://binaries.sonarsource.com/Distribution/sonarqube/sonarqube-10.5.1.90531.zip unzip sonarqube-10.5.1.90531.zip mv sonarqube-10.5.1.90531 ~/sonarqube

cd ~/sonarqube/bin/macosx-universal-64 ./sonar.sh start

Access SonarQube: http://localhost:9000

./sonar.sh status

Sonar-token squ_6198116a5fceda09890da18427b6f345020662fd




Version
java    |java -v|openjdk 21.0.7 2025-04-15
-----------------------------------------| 
 maven  |mvn -version|Apache Maven 3.9.9 
 -------------------------------------------| 
 Jenkins| UI | Version jenkins-lts 2.504.2 
 ------------------------------------------| 
 Sonar  | UI | v25.5.0.107428 
 -------------------------------------------| 
 Nexus  | UI | 3.80.0-06 
 --------------------------------------------|


Credentials
Jenkins Nahawand 2948ae5c64044265a97de46037982e35
Sonar
---------------------------------------------
Nexus
--------------------------------------------


sonar/jenkins-token: squ_54133f3b36f62e9ca5f061183b886f432e8a36be
Jenkins Plugins Required
Git Plugin

Pipeline
# ail Extension Plugin

Credentials Plugin

SonarQube Scanner	Enables withSonarQubeEnv() DSL block Pipeline Utility Steps	Enables file operations and build logic Nexus Artifact Uploader	(Optional) Uploads files to Nexus Credentials Binding	Binds credentials to environment vars Maven Integration	Makes Maven available for pipelines
environment block
Use Jenkins credentials plugin (not hardcoded admin:admin123). Create a new credential (e.g., nexus-credentials) via Jenkins > Manage Jenkins > Credentials.

Declare SONARQUBE_ENV with the same name you configure in "Manage Jenkins > Configure System > SonarQube servers".
tools block
Explicitly defines Maven and JDK versions. You must configure these in Jenkins global tools (Manage Jenkins > Global Tool Configuration).
stage: SonarQube Analysis
Uses withSonarQubeEnv() to integrate Jenkins with your SonarQube instance. Must be configured in Jenkins.

No credentials are needed here if the SonarQube token is already configured globally.
stage: Send Email
Uses Jenkins Mailer plugin. Make sure SMTP settings are configured in "Manage Jenkins > Configure System".


---
sudo apt update && sudo apt upgrade -y
curl -fsSL -o get_helm.sh https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3
chmod +x get_helm.sh
./get_helm.sh
helm version
---
# elasticsearch 
helm install elasticsearch elastic/elasticsearch -n logging