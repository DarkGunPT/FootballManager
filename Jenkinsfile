pipeline {
    agent any

    environment {
        BACKEND_IMAGE = 'backend'
        FRONTEND_IMAGE = 'frontend'
        DOCKER_HUB_REPO = 'xicosimoes/teste'
        DOCKERHUB_U = '' 
        DOCKERHUB_P = ''  
    }

    stages {
        stage('Login to docker') {
            steps {
                script {
                   withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', passwordVariable: 'DOCKERHUB_P', usernameVariable: 'DOCKERHUB_U')]) {
                   
                    sh "docker login -u "${DOCKERHUB_P}" -p "${DOCKERHUB_P}" --password-stdin"
                }
                }
            }
        }

        stage('Pull And Build Backend') {
            steps {
                script {
                        echo "Building and Pushing Backend Image to DockerHub Repository..."

                        dir('backend') {
                            sh '''
                            apt-get install -y maven
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
