pipeline {
    agent any
    environment {
        DOCKER_IMAGE = "hazemlachheb/projet-devops"
        K8S_NAMESPACE = "devops"
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
                sh './mvnw dependency:go-offline -B'
                sh './mvnw clean package -DskipTests -o'
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
                        docker.image("${DOCKER_IMAGE}:latest").push()
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                sh """
                    echo 'Déploiement dans Kubernetes…'
                    kubectl apply -f k8s/mysql-deployment.yaml -n ${K8S_NAMESPACE}
                    kubectl apply -f k8s/mysql-service.yaml -n ${K8S_NAMESPACE}
                    kubectl apply -f k8s/spring-config.yaml -n ${K8S_NAMESPACE}
                    kubectl apply -f k8s/spring-deployment.yaml -n ${K8S_NAMESPACE}
                    kubectl apply -f k8s/spring-service.yaml -n ${K8S_NAMESPACE}
                """
            }
        }

        stage('Verify Deployment') {
            steps {
                sh 'echo "=== PODS ==="'
                sh "kubectl get pods -n ${K8S_NAMESPACE}"
                sh 'echo "=== SERVICES ==="'
                sh "kubectl get svc -n ${K8S_NAMESPACE}"
            }
        }
    }

    post {
        success {
            echo "Pipeline terminé avec succès ! Ton application est accessible sur http://<IP>:30080"
        }
        failure {
            echo "Pipeline échoué ! Vérifie les logs."
        }
        always {
            cleanWs()
        }
    }
}
