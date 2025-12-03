pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "hazemlachheb/projet-devops:latest"
        GIT_REPO = "https://github.com/lachhebhazem/gestionFoyerTP-devops.git"
    }

    triggers {
        pollSCM('* * * * *')
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main', url: "${env.GIT_REPO}"
            }
        }

        stage('Clean & Build') {
            steps {
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${env.DOCKER_IMAGE} ."
            }
        }

        stage('Push Docker Image') {
            steps {
                withDockerRegistry([credentialsId: 'dockerhub-credentials', url: '']) {
                    sh "docker push ${env.DOCKER_IMAGE}"
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
