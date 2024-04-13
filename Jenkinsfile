pipeline {
    agent any

    environment {
        MONGO_IMAGE = 'mongo'
        BACKEND_IMAGE = 'backend'
        BACKEND_DOCKERFILE = './dockerfile'
        MONGO_CONTAINER_NAME = 'mongodb'
        BACKEND_CONTAINER_NAME = 'backend'
        NETWORK = 'football-network'
    }

    stages {
        stage('Check if Docker is Installed') {
            steps {
                script {
                    // Check if Docker is installed
                    def dockerVersion = sh(script: 'docker --version', returnStdout: true).trim()

                    if (dockerVersion.contains('Docker version')) {
                        echo "Docker is already installed: ${dockerVersion}"
                    } else {
                        echo "Docker is not installed, proceeding with installation"
                        
                        // Install Docker inside Jenkins container
                        sh '''
                        curl -fsSL https://get.docker.com -o get-docker.sh
                        sh get-docker.sh
                        '''
                    }
                }
            }
        }
        stage('Create Volume') {
            steps {
                script {
                    docker.image('docker:latest').inside {
                        sh 'docker volume create mongo-data'
                    }
                }
            }
        }
        stage('Pull MongoDB Image') {
            steps {
                script {
                    docker.image(env.MONGO_IMAGE).pull()
                }
            }
        }
        stage('Pull Custom Backend Code') {
            steps {
                checkout scm
            }
        }

        stage('Build Custom Backend Image') {
            steps {
                script {
                    docker.build(env.BACKEND_IMAGE, "-f ${env.BACKEND_DOCKERFILE} .")
                }
            }
        }

        stage('Run MongoDB Container') {
            steps {
                script {
                    docker.image(env.MONGO_IMAGE)
                        .withRun("-d --name ${env.MONGO_CONTAINER_NAME} --network ${env.NETWORK} ${env.MONGO_IMAGE}")
                }
            }
        }

        stage('Run Custom Backend Container') {
            steps {
                script {
                    docker.image(env.BACKEND_IMAGE)
                        .withRun("-d -p 8080:8080 --name ${env.BACKEND_CONTAINER_NAME} --network ${env.NETWORK} ${env.BACKEND_IMAGE}")
                }
            }
        }
    }

    post {
        always {
            cleanWs()
            script {
                docker.image(env.MONGO_IMAGE).stop()
                docker.image(env.MONGO_IMAGE).remove()
                docker.image(env.BACKEND_IMAGE).stop()
                docker.image(env.BACKEND_IMAGE).remove()
            }
        }
    }
}
