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
                sh 'docker volume create mongo-data'
                sh 'docker network create ${env.NETWORK}'
            }
        }
        
        stage('Pull MongoDB Image') {
            steps {
                sh "docker pull ${env.MONGO_IMAGE}"
            }
        }

        stage('Pull Custom Backend Code') {
            steps {
                checkout([$class: 'GitSCM', 
                  branches: [[name: 'franciscoSimoes-pipeline']], 
                  doGenerateSubmoduleConfigurations: false, 
                  extensions: [[$class: 'SparseCheckoutPaths', sparseCheckoutPaths: [[path: 'backend/']]]], 
                  submoduleCfg: [], 
                  userRemoteConfigs: [[url: 'https://github.com/DarkGunPT/FootballManager.git']]])
            }
        }

        stage('Build Custom Backend Image') {
            steps {
                dir('backend'){
                    sh "docker build -t ${env.BACKEND_IMAGE} -f ${env.BACKEND_DOCKERFILE} ."                
                }
           }
        }

        stage('Run MongoDB Container') {
            steps {
                sh """
                docker run -d \
                --name ${env.MONGO_CONTAINER_NAME} \
                --network ${env.NETWORK} \
                ${env.MONGO_IMAGE}
                """
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
