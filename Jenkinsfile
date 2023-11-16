pipeline {
    agent any

    environment {
       DOCKERHUB_CREDENTIALS = credentials('dockertoken')
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

        stage('NEXUS DEPLOYMENT') {
            steps {
                dir('DevOps_Project') {
                    script {
                        sh 'mvn deploy'
                    }
                }
            }
        }
        stage('DOCKER HUB LOGIN') {
            steps {
                sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
            }
        }
        stage('BUILD DOCKER IMAGE') {
            steps {
                script {
                    def dockerImage = 'zied159/alpine:1.0.0'
                    def imageExists = sh(script: "docker inspect --type=image $dockerImage", returnStatus: true) == 0

                    
                    dir('DevOps_Project') {
                        sh "docker build -t $dockerImage ."
                        sh "docker push $dockerImage"
                    }
                   
                }
            }
        }
        stage('DOCKER-COMPOSE') {
            steps {
                dir('DevOps_Project') {
                    script {
                        sh 'docker-compose -f docker-compose.yml up -d'
                    }
                }
            }
        }
        // stage('PROMETHEUS AND GRAFANA') {
        //     steps {
        //         script {
        //             dir('DevOps_Project') {
        //                 sh 'docker-compose -f docker-compose-prometheus.yml -f docker-compose-grafana.yml up -d'
        //             }
        //         }
        //     }
        // }

    }
}