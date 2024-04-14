pipeline {
    agent any

    environment {
        MONGO_IMAGE = 'mongo'
        MONGO_VOLUME = 'mongo-data'
        BACKEND_IMAGE = 'backend'
        FRONTEND_IMAGE = 'frontend'
        BACKEND_DOCKERFILE = 'dockerfile'
        MONGO_CONTAINER_NAME = 'mongodb'
        BACKEND_CONTAINER_NAME = 'backend'
        FRONTEND_CONTAINER_NAME = 'frontend'  // Corrected the container name
        NETWORK = 'footballNetwork'
        DOCKER_HUB_REPO = 'xicosimoes/teste'
        DOCKERHUB_USERNAME = ''  // Fill in your Docker Hub username
        DOCKERHUB_PASSWORD = ''  // Fill in your Docker Hub password
    }

    stages {
        stage('Create Volume') {
            steps {
                script {
                    // Check if the volume already exists
                    def volumeCheck = sh(script: "docker volume ls -qf name=${env.MONGO_VOLUME}", returnStdout: true).trim()
                    def volumeExists = volumeCheck != ""
                    if (!volumeExists) {
                        sh "docker volume create ${env.MONGO_VOLUME}"
                    } else {
                        echo "Volume ${env.MONGO_VOLUME} already exists."
                    }

                    // Check if the network already exists
                    def networkCheck = sh(script: "docker network ls -qf name=${env.NETWORK}", returnStdout: true).trim()
                    def networkExists = networkCheck != ""
                    if (!networkExists) {
                        sh "docker network create ${env.NETWORK}"
                    } else {
                        echo "Network ${env.NETWORK} already exists."
                    }
                    withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', passwordVariable: 'DOCKERHUB_PASSWORD', usernameVariable: 'DOCKERHUB_USERNAME')]) {
                    // Login to Docker
                    sh "echo ${env.DOCKERHUB_PASSWORD} | docker login -u ${env.DOCKERHUB_USERNAME} --password-stdin"
                }
                }
            }
        }

        stage('Pull And Run MongoDB') {
            steps {
                script {
                    sh "docker run -p 27017:27017 -d --name ${env.MONGO_CONTAINER_NAME} --network ${env.NETWORK} -v ${env.MONGO_VOLUME}:/data/db ${env.MONGO_IMAGE}"
                }
            }
        }

        stage('Pull And Build Backend') {
            steps {
                script {
                    def imageExists = sh(script: "docker pull ${env.DOCKER_HUB_REPO}:${env.BACKEND_IMAGE}", returnStatus: true) == 0

                    if (imageExists) {
                        echo "Image ${env.DOCKER_HUB_REPO}:${env.BACKEND_IMAGE} exists in the registry. Pulling image..."
                        sh "docker pull ${env.DOCKER_HUB_REPO}:${env.BACKEND_IMAGE}"
                    } else {
                        echo "Image ${env.DOCKER_HUB_REPO}:${env.BACKEND_IMAGE} does not exist in the registry. Building locally..."

                        dir('backend') {
                            sh '''
                            apt-get update
                            apt-get install -y maven
                            mvn -B -DskipTests clean package
                            docker build -t $BACKEND_IMAGE .
                            docker tag $BACKEND_IMAGE $DOCKER_HUB_REPO:$BACKEND_IMAGE
                            docker push DOCKER_HUB_REPO:$BACKEND_IMAGE
                            '''
                        }
                    }
                    sh "docker run -p 8082:8082 -d --name $BACKEND_CONTAINER_NAME --network $NETWORK $DOCKER_HUB_REPO:$BACKEND_IMAGE"
                }
            }
        }

        stage('Pull And Build Frontend') {
            steps {
                script {
                    def imageExists = sh(script: "docker pull $DOCKER_HUB_REPO:$FRONTEND_IMAGE", returnStatus: true) == 0

                    if (imageExists) {
                        echo "Image ${env.DOCKER_HUB_REPO}:${env.FRONTEND_IMAGE} exists in the registry. Pulling image..."
                        sh "docker pull $DOCKER_HUB_REPO:$FRONTEND_IMAGE"
                    } else {
                        echo "Image ${env.DOCKER_HUB_REPO}:${env.FRONTEND_IMAGE} does not exist in the registry. Building locally..."

                        dir('frontend') {  // Corrected the directory name to 'frontend'
                            sh '''
                            npm install
                            npm run build
                            docker build -t $FRONTEND_IMAGE .
                            docker tag $FRONTEND_IMAGE $DOCKER_HUB_REPO:$FRONTEND_IMAGE
                            docker push $DOCKER_HUB_REPO:$FRONTEND_IMAGE
                            '''
                        }
                    }
                    sh "docker run -p 8081:8081 -d --name $FRONTEND_CONTAINER_NAME --network $NETWORK $DOCKER_HUB_REPO:$FRONTEND_IMAGE"
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
            sh "docker stop ${env.FRONTEND_CONTAINER_NAME}"  // Stop the frontend container
            sh "docker rm ${env.FRONTEND_CONTAINER_NAME}"     // Remove the frontend container
            sh "docker rmi ${env.MONGO_IMAGE}"
            sh "docker rmi ${env.DOCKER_HUB_REPO}:${env.FRONTEND_IMAGE}"
            sh "docker rmi ${env.DOCKER_HUB_REPO}:${env.BACKEND_IMAGE}"
            sh "docker network rm ${env.NETWORK}"
        }
    }
}
