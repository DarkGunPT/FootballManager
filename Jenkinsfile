pipeline {
    agent any

    environment {
        BACKEND_IMAGE = 'backend'
        FRONTEND_IMAGE = 'frontend'
        DOCKER_HUB_REPO = 'xicosimoes/teste'
        DOCKER_USERNAME = '' 
        DOCKER_PASSWORD = ''  
    }
    tools {
        // Specify the Maven installation defined in Jenkins
        maven 'maven'
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
                            mvn -B -DskipTests clean package
                            docker build -t $FRONTEND_IMAGE .
                            docker tag $FRONTEND_IMAGE $DOCKER_HUB_REPO:$FRONTEND_IMAGE
                            docker push $DOCKER_HUB_REPO:$FRONTEND_IMAGE
                            '''
                        }
                }
            }
        }
        post {
            failure {
                script {
                    def failedStageName = env.STAGE_NAME
                    emailext (
                        subject: "Build Failed in Stage: ${failedStageName}",
                        body: "Your build failed in stage: ${failedStageName}.",
                        to: 'franciscoscc15@gmail.com'
                    )
                }
            }
        }
    }
}
