pipeline {
    agent any
    environment {
        DOCKER_IMAGE = "hazemlachheb/projet-devops"
        SONAR_TOKEN = credentials('sonar-token')  // Already masked and available as env var
        DOCKER_REGISTRY = "https://index.docker.io/v1/"
    }
    triggers {
        pollSCM('* * * * *') // Every minute
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
                sh 'git clean -fdx'
            }
        }
        stage('Build Project') {
            steps {
                sh 'chmod +x mvnw'
                sh './mvnw clean verify -Dmaven.test.skip=true'  // Fully skip tests
            }
        }
        stage('SonarQube Analysis') {
    steps {
        withSonarQubeEnv('SonarQubeServer') {  // nom du serveur que tu as configuré
            sh './mvnw clean verify sonar:sonar -Dsonar.projectKey=gestionfoyerTP'
        }
    }
}

        stage('Quality Gate') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {  // Increased timeout – Sonar can take time
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${DOCKER_IMAGE}:latest ."
            }
        }
        stage('Push Docker Image') {
            steps {
                script {
                    docker.withRegistry(DOCKER_REGISTRY, 'dockerhub-credentials') {
                        sh "docker push ${DOCKER_IMAGE}:latest"
                    }
                }
            }
        }
        stage('Deploy to Kubernetes') {
            steps {
                sh '''
                kubectl set image deployment/spring-app spring=${DOCKER_IMAGE}:latest -n devops
                kubectl rollout restart deployment/spring-app -n devops
                kubectl rollout status deployment/spring-app -n devops --timeout=5m
                '''
            }
        }
    }
    post {
        success {
            echo "Pipeline terminé avec succès !"
        }
        failure {
            echo "Pipeline échoué ! Vérifie Jenkins / Sonar / Docker / Kubernetes."
        }
    }
}
