# Installations: 
## method 1: 

brew install jenkins-lts

nano /usr/local/opt/jenkins-lts/homebrew.mxcl.jenkins-lts.plist
<string>--httpPort=8085</string>

brew services restart jenkins-lts

cat /Users/Shared/Jenkins/Home/secrets/initialAdminPassword

brew services start jenkins-lts

## method 2: 
$ docker run -d --name jenkins-ci -p 8082:8080 jenkins/jenkins:lts

http://localhost:8082/

docker exec -it jenkins-ci cat /var/jenkins_home/secrets/initialAdminPassword

3afb57247792498392715cb687780874
---
