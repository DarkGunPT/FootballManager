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
                sh "docker run -p 27017:27017 -d --name ${env.MONGO_CONTAINER_NAME} --network ${env.NETWORK} -v mongo-data:/data/db ${env.MONGO_IMAGE}"
            }
        }

       stage('Pull And Build Backend') {
            steps {
                script {
                    def imageExists = sh(script: "docker pull ${DOCKER_HUB_REPO}:${BUILD_NUMBER}", returnStatus: true) == 0
        
                    if (imageExists) {
                        echo "Image ${DOCKER_HUB_REPO}:${BUILD_NUMBER} exists in the registry. Pulling image..."
                        sh "docker pull ${DOCKER_HUB_REPO}:${BUILD_NUMBER}"
                    } else {
                        echo "Image ${DOCKER_HUB_REPO}:${BUILD_NUMBER} does not exist in the registry. Building locally..."
        
                        dir('backend') {
                            sh '''
                            apt-get update
                            apt-get install -y maven 
                            mvn -B -DskipTests clean package
                            docker build -t ${BACKEND_IMAGE} .
                            docker tag ${BACKEND_IMAGE} ${DOCKER_HUB_REPO}:${BUILD_NUMBER}
                            docker push ${DOCKER_HUB_REPO}:${BUILD_NUMBER}
                            '''
                            }
                    }
                    sh "docker run -p 8081:8081 -d --name ${env.BACKEND_CONTAINER_NAME} --network ${env.NETWORK} ${DOCKER_HUB_REPO}:${BUILD_NUMBER}"
                }
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
