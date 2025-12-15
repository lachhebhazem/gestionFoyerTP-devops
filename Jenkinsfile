pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "hazemlachheb/projet-devops"
        SONAR_TOKEN = credentials('sonar-token')
        DOCKER_REGISTRY = "https://index.docker.io/v1/"
    }

    triggers {
        pollSCM('* * * * *')   // toutes les 5 minutes (meilleure pratique)
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
                sh '''
                ./mvnw clean verify \
                -DskipTests \
                -Dspring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
                '''
            }
        }

        stage('SonarQube Analysis') {
    steps {
        withSonarQubeEnv('SonarQubeServer') {
            sh '''
            ./mvnw sonar:sonar \
            -DskipTests \
            -Dspring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration \
            -Dsonar.projectKey=gestionfoyerTP
            '''
        }
    }
}


        stage('Quality Gate') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
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
            echo "✅ Pipeline terminé avec succès !"
        }
        failure {
            echo "❌ Pipeline échoué ! Vérifie Jenkins / Sonar / Docker / Kubernetes."
        }
    }
}
