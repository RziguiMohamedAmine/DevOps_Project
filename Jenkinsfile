pipeline {
    agent any

    tools{
        nodejs 'node16'
    }

    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub')
    }

    stages {
        stage('Checkout GIT') {
            steps {
                checkout scm
            }
        }

        stage('Run Unit Tests JUNIT') {
            steps {
                dir('DevOps_Project'){
                script {
                    sh 'mvn test' 
                    }
                }
            }
        }

        stage('Build Spring Boot Application') {
            steps {
                dir('DevOps_Project'){
                script {
                    try {
                        sh 'mvn clean install'
                    } catch (Exception e) {
                        currentBuild.result = 'FAILURE'
                        error("Build failed: ${e.message}")
                    }
                }
                }
            }
        }

        stage('SonarQube analysis') {
            steps {
                dir('DevOps_Project') {
                    script {
                        withSonarQubeEnv(installationName: 'sonarQubeServer') { 
                        sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.7.0.1746:sonar'
                        }
                    }
                }
            }
        }

        stage('Upload to Nexus'){
            steps{
                dir('DevOps_Project'){
                    script{
                        sh 'mvn deploy -DskipTests'
                    }
                }
            }
        }

        stage('Login to Docker Hub') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'dockerhub-pwd', variable: 'dockerhubpwd')]) {
                        sh 'docker login -u flija -p ${dockerhubpwd}'
                    }
                    }
                }
        }

        stage('Building & Pushing Docker Image') {
            steps {
                dir('DevOps_Project'){
                       script {
                            def imageName = 'flija/devops_project'
                            def imageExists = sh(script: "docker inspect --type=image $imageName", returnStatus: true) == 0

                            if (imageExists) {
                                echo 'Docker image $imageName already exists on Docker Hub. Skipping push.'
                            } else {
                                sh "docker build -t $imageName ."
                                sh "docker push $imageName"
                            }
                     }
                }
            }
        }

        stage('Deploying Docker Image') {
            steps {
                dir('DevOps_Project'){
                    script {
                        sh 'docker-compose -f docker-compose.yml up -d'
                    }
                }
            }
        }

        stage('Deploying Grafana and Prometheus') {
            steps {
                dir('DevOps_Project'){
                    script {
                        //check if the containers are already running
                        def prometheusExists = sh(script: "docker inspect --type=container prometheus", returnStatus: true) == 0
                        def grafanaExists = sh(script: "docker inspect --type=container grafana", returnStatus: true) == 0

                        if (prometheusExists && grafanaExists) {
                            echo 'Prometheus and Grafana are already running. Skipping deployment.'
                        }
                        
                        else {
                            sh 'docker-compose -f docker-compose-prometheus.yml -f docker-compose-grafana.yml up -d'
                        }
                
                    }
                }
            }
        }

    }

        post {
            success {
                script {
                                    def subject = "Success"
                                    def body = "Build has been succesfully approved"
                                    def to = 'youssef.flija@esprit.tn'

                                    mail(
                                        subject: subject,
                                        body: body,
                                        to: to,
                                    )
                                }
            }
            failure {
                script {
                    def subject = "Build Failure - ${currentBuild.fullDisplayName}"
                    def body = "The build has failed in the Jenkins pipeline. Please investigate and take appropriate action."
                    def to = 'youssef.flija@esprit.tn' // Replace with your email address

                    mail(
                        subject: subject,
                        body: body,
                        to: to,
                    )
                }
            }
        }
    }