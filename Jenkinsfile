pipeline {
    agent any

    environment {
        BACKEND_IMAGE = 'backend'
        FRONTEND_IMAGE = 'frontend'
        DOCKER_HUB_REPO = 'xicosimoes/teste'
        DOCKER_USERNAME = '' 
        DOCKER_PASSWORD = ''  
    }

    stages {
        stage('Login to docker') {
            steps {
                script {
                   withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')]) {
                   
                    sh 'echo ${DOCKER_PASSWORD} | docker login -u ${DOCKER_USERNAME} --password-stdin'
                }
                }
            }
        }
        stage('Install Maven') {
                    steps {
                        script {
                            // Install Maven tool
                            def mvnHome = tool 'Maven'
                            env.PATH = "${mvnHome}/bin:${env.PATH}"
                        }
                    }
                }
        stage('Pull And Build Backend') {
            steps {
                script {
                        echo "Building and Pushing Backend Image to DockerHub Repository..."

                        dir('backend') {
                            sh '''
                            mvn -B -DskipTests clean package
                            docker build -t $BACKEND_IMAGE .
                            docker tag $BACKEND_IMAGE $DOCKER_HUB_REPO:$BACKEND_IMAGE
                            docker push $DOCKER_HUB_REPO:$BACKEND_IMAGE
                            '''
                        }
                }
            }
        }

        stage('Pull And Build Frontend') {
            steps {
                script {
                        echo "Building and Pushing Frontend Image to DockerHub Repository..."

                        dir('frontend') { 
                            sh '''
                            apt-get update
                            apt-get install -y maven
                            mvn -B -DskipTests clean package
                            docker build -t $FRONTEND_IMAGE .
                            docker tag $FRONTEND_IMAGE $DOCKER_HUB_REPO:$FRONTEND_IMAGE
                            docker push $DOCKER_HUB_REPO:$FRONTEND_IMAGE
                            '''
                        }
                }
            }
        }
    }
}
