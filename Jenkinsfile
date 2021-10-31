pipeline {

    agent any

    stages {
    
        stage ('GIT') {
            steps {
               echo "Getting Project from Git"; 
                git branch: "master", 
                    url: "https://github.com/326-163/timesheet-ci.git";    
            }
        }
            
        stage("Build") {
            steps {
                bat "mvn clean install -DskipTests"
            }
        }
        
        stage("Maven version") {
            steps {
                  bat "mvn -version"
                  }
        }
        
        stage("tests unitaires ") {
            steps {
                bat "mvn test"
                  }
        }
        
        stage("Sonar") {
            steps {
                bat "mvn sonar:sonar"
            }
        }
       
       
        stage("Nexus") {
            steps {           
                    bat "mvn deploy"
                }         
        }
   
}
}