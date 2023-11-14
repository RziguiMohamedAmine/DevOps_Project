pipeline {
    agent any


    environment {
        BRANCH_NAME = 'SupplierModule'
        GIT_CREDENTIAL_ID = 'ZM'
        GIT_REPO = 'https://github.com/RziguiMohamedAmine/DevOps_Project.git'
    }

    stages {
        stage('GIT HUB CHECKOUT') {
            steps {
                script {
                    echo "Checking out from git..."
                    checkout scm
                }
            }
        }
         stage("MAVEN CLEAN TEST") {
            steps {
                dir('DevOps_Project') {
                    script {
                        sh 'mvn clean test'
                    }
                }
            }
         }
         stage('SONAR QUBE') {
            steps {
                dir('DevOps_Project') {
                    withSonarQubeEnv('sonarqubeenv') {
                        sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.0.2155:sonar -Dsonar.host.url=http://192.168.33.10:9000 -Dsonar.login=admin -Dsonar.password=azerty123'
                    }
                }
            }
        }

    }
}