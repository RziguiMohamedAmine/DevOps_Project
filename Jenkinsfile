pipeline {
    agent any

    stages {
        stage('Checkout GIT') {
            steps {
                checkout scm
            }
        }

        stage('Run Unit Tests JUNIT') {
            steps {
                script {
                    sh 'mvn test' 
                }
            }
        }

        stage('Build Spring Boot Application') {
            steps {
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