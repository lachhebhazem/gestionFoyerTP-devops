pipeline {
    agent any
    environment {
        DOCKER_IMAGE = "hazemlachheb/projet-devops"
        SONAR_TOKEN = credentials('sonar-token')
        DOCKER_REGISTRY = "https://index.docker.io/v1/"
    }
    triggers {
        pollSCM('* * * * *')  // Polls every minute
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
                sh """
                ./mvnw clean verify sonar:sonar \
                -Dsonar.projectKey=test-devops \
                -Dsonar.host.url=http://http://192.168.72.129:9000 \
                -Dsonar.login=${SONAR_TOKEN}
                """
            }
        }
        stage('Quality Gate') {
            steps {
                timeout(time: 2, unit: 'MINUTES') {
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
                    // This is the key fix: wrap docker.withRegistry in a script block
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
            echo "Pipeline échoué ! Vérifie Jenkins / Sonar / Docker / Kubernetes."
        }
    }
}
