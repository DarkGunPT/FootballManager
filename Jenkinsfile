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
        stage('Create Volume') {
            steps {
                script {
                    sh'docker volume create mongo-data'
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
