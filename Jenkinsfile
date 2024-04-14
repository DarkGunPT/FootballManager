pipeline {
    agent any

    environment {
        MONGO_IMAGE = 'mongo'
        MONGO_VOLUME = 'mongo-data'
        BACKEND_IMAGE = 'backend'
        BACKEND_DOCKERFILE = 'dockerfile'
        MONGO_CONTAINER_NAME = 'mongodb'
        BACKEND_CONTAINER_NAME = 'backend'
        NETWORK = 'football-network'
        DOCKER_HUB_REPO = 'xicosimoes/teste'
        DOCKERHUB_USERNAME = ''
        DOCKERHUB_PASSWORD = ''
        BUILD_NUMBER='1'
    }

    stages {
       stage('Create Volume') {
        steps {
            script {
                // Check if the volume already exists
                def volumeExists = sh(script: "docker volume ls -qf name=${env.MONGO_VOLUME}", returnStatus: true) == 0
                if (!volumeExists) {
                    sh "docker volume create ${env.MONGO_VOLUME}"
                } else {
                    echo "Volume ${env.MONGO_VOLUME} already exists."
                }
    
                // Check if the network already exists
                def networkExists = sh(script: "docker network ls -qf name=${env.NETWORK}", returnStatus: true) == 0
                if (!networkExists) {
                    sh "docker network create ${env.NETWORK}"
                } else {
                    echo "Network ${env.NETWORK} already exists."
                }
            }
        }
      }

        
        stage('Pull And Run MongoDB') {
            steps {
                sh "docker run -p 27017:27017 -d --name ${env.MONGO_CONTAINER_NAME} --network football-network -v mongo-data:/data/db ${env.MONGO_IMAGE}"
            }
        }

       stage('Pull And Build Backend') {
    steps {
        // Login to Docker Hub
        withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKERHUB_USERNAME', passwordVariable: 'DOCKERHUB_PASSWORD')]) {
            sh "docker login -u ${DOCKERHUB_USERNAME} -p ${DOCKERHUB_PASSWORD}" || error("Failed to login to Docker Hub")

            dir('backend') {
                sh '''
                apt-get update
                apt-get install -y maven 
                mvn -B -DskipTests clean package
                '''
            }

            dir('backend') {
                // Build, tag, and push Docker image
                sh "docker build -t ${DOCKER_IMAGE_NAME} ." || error("Failed to build Docker image")
                sh "docker tag ${DOCKER_IMAGE_NAME} ${DOCKER_HUB_REPO}:${BUILD_NUMBER}" || error("Failed to tag Docker image")
                sh "docker push ${DOCKER_HUB_REPO}:${BUILD_NUMBER}" || error("Failed to push Docker image")
            }
        }
    }
}

        

        stage('Run Custom Backend Container') {
            steps {
                sh """
                docker run -d \
                -p 8080:8080 \
                --name ${env.BACKEND_CONTAINER_NAME} \
                --network ${env.NETWORK} \
                ${env.BACKEND_IMAGE}
                """
            }
        }
    }

    post {
        always {
            cleanWs()
            sh "docker stop ${env.MONGO_CONTAINER_NAME}"
            sh "docker rm ${env.MONGO_CONTAINER_NAME}"
            sh "docker stop ${env.BACKEND_CONTAINER_NAME}"
            sh "docker rm ${env.BACKEND_CONTAINER_NAME}"
            sh "docker rmi ${env.MONGO_IMAGE}"
            sh "docker rmi ${env.BACKEND_IMAGE}"
            sh "docker network rm ${env.NETWORK}"
        }
    }
}
