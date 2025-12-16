pipeline {
    agent any

    environment {
        DOCKER_IMAGE    = "hazemlachheb/projet-devops"
        SONAR_TOKEN     = credentials('sonar-test')   // ID du token Jenkins pour SonarQube
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
                sh '''
                    ./mvnw clean verify \
                        -DskipTests \
                        -Dspring.profiles.active=h2
                '''
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQubeServer') {
                    sh '''
                        ./mvnw sonar:sonar \
                            -DskipTests \
                            -Dspring.profiles.active=h2 \
                            -Dsonar.projectKey=sonar-test \
                            -Dsonar.projectName=sonar-test \
                            -Dsonar.host.url=http://192.168.1.170:9000 \
                            -Dsonar.login=${SONAR_TOKEN} \
                            -Dsonar.ws.timeout=120
                    '''
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
