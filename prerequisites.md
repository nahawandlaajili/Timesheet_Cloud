## MAC Installations
- java brew install openjdk@17
- maven : brew install maven
- Jenkins
Install the latest LTS version: brew install jenkins-lts
Start the Jenkins service: brew services start jenkins-lts
Restart the Jenkins service: brew services restart jenkins-lts
Update the Jenkins version: brew upgrade jenkins-lts
Access Jenkins via: http://localhost:8080

Token: 114bb968fd9f594b3c2fcd93ac86db1715

- SonarQube
Start sonarQube cd ~/Downloads curl -O https://binaries.sonarsource.com/Distribution/sonarqube/sonarqube-10.5.1.90531.zip unzip sonarqube-10.5.1.90531.zip mv sonarqube-10.5.1.90531 ~/sonarqube

cd ~/sonarqube/bin/macosx-universal-64 ./sonar.sh start

Access SonarQube: http://localhost:9000

./sonar.sh status

Sonar-token squ_6198116a5fceda09890da18427b6f345020662fd
Nexus
https://help.sonatype.com/repomanager3/download

cd ~/Downloads tar -xvzf nexus-.tar.gz cd nexus- cd bin ./nexus start

Access Nexus: http://localhost:8081

Default login: admin — get the password from: bash CopyEdit cat sonatype-work/nexus3/admin.password

KR228Laj75##ceuV8muth0
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

