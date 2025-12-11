pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "hazemlachheb/projet-devops"
        K8S_NAMESPACE = "devops"
    }

    triggers {
        // Vérifier le dépôt toutes les minutes
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

        stage('Deploy to Kubernetes') {
            steps {
                script {
                    sh """
                        echo '🚀 Déploiement dans Kubernetes…'
                        kubectl apply -f k8s/mysql-deployment.yaml -n ${K8S_NAMESPACE}
                        kubectl apply -f k8s/spring-deployment.yaml -n ${K8S_NAMESPACE}
                    """
                }
            }
        }

        stage('Verify Deployment') {
            steps {
                script {
                    sh "kubectl get pods -n ${K8S_NAMESPACE}"
                    sh "kubectl get svc -n ${K8S_NAMESPACE}"
                }
            }
        }
    }

    post {
        success {
            echo "Pipeline terminé avec succès ! Déploiement Kubernetes OK."
        }
        failure {
            echo "Pipeline échoué ! Vérifie les logs."
        }
    }
}
