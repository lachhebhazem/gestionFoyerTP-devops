pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "hazemlachheb/projet-devops"
        DOCKER_REGISTRY = "https://index.docker.io/v1/"
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

        stage('Build Project') {
            steps {
                sh 'chmod +x mvnw'
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withCredentials([string(credentialsId: 'SONAR_AUTH_TOKEN', variable: 'SONAR_TOKEN')]) {
                    withSonarQubeEnv('SonarQube') {
                        sh '''
                        ./mvnw sonar:sonar \
                        -Dsonar.login=$SONAR_TOKEN
                        '''
                    }
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
                docker.withRegistry(DOCKER_REGISTRY, 'dockerhub-credentials') {
                    sh "docker push ${DOCKER_IMAGE}:latest"
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                sh '''
                kubectl set image deployment/spring-app spring='${DOCKER_IMAGE}:latest' -n devops
                kubectl rollout restart deployment/spring-app -n devops
                kubectl rollout status deployment/spring-app -n devops
                '''
            }
        }
    }

    post {
        success {
            echo "Pipeline terminé avec succès !"
        }
        failure {
            echo "Pipeline échoué ! Vérifie Jenkins / Sonar / Docker."
        }
    }
}
