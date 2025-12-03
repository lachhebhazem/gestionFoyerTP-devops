pipeline {
    agent any
    environment {
        DOCKER_IMAGE = "hazemlachheb/projet-devops" 
    }
    triggers {
        pollSCM('* * * * *')
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm  
                sh 'git clean -fdx'
            }
        }
        stage('Clean & Build') {
            steps {
                sh 'chmod +x mvnw'
                sh './mvnw clean package -DskipTests'
            }
        }
        stage('Build Docker Image') {
            steps {
                script {  
                    docker.build("${DOCKER_IMAGE}:latest")
                }
            }
        }
        stage('Push Docker Image') {
            steps {
                script {  
                    docker.withRegistry('https://index.docker.io/v1/', 'dockerhub-credentials') {
                        sh "docker push ${DOCKER_IMAGE}:latest"
                    }
                }
            }
        }
    }
    post {
        success {
            echo "Pipeline terminé avec succès ! L'image Docker est prête."
        }
        failure {
            echo "Pipeline échoué !"
        }
    }
}
