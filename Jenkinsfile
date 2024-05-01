pipeline {
    agent any
    environment {
        BACKEND_IMAGE = 'backend'
        FRONTEND_IMAGE = 'frontend'
        DOCKER_HUB_REPO = 'xicosimoes/teste'
        DOCKER_USERNAME = ''
        DOCKER_PASSWORD = ''
        GMAIL_USERNAME = ''
        GMAIL_PASSWORD = ''
        CURRENT_STAGE = ''
    }
    tools {
        maven 'maven'
    }
     stages {
    stage('Login to docker') {
        steps {
            script {
                CURRENT_STAGE = 'Login to docker'
                echo 'Logging to docker'
                withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')]) {   
                    sh 'echos ${DOCKER_PASSWORD} | docker login -u ${DOCKER_USERNAME} --password-stdin'
                }
                }
            }
        }
        stage('Backend Image Build and Push') {
            steps {
                script {
                        CURRENT_STAGE = 'Backend Image Build and Push'
                        echo "Getting the Backend dockerfile from Git, Building and Pushing it..."

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

        stage('Frontend Image Build and Push') {
            steps {
                script {
                        CURRENT_STAGE = 'Frontend Image Build and Push'
                        echo "Getting the Frontend dockerfile from Git, Building and Pushing it..."
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
    }
    post {
            failure {
                script {
                    emailext (
                        attachLog:true,
                        subject: "Build Failed in Stage: ${CURRENT_STAGE}",
                         body: """
                                <div class="container">
                                    <div class="alert alert-success" role = "alert">
                                        <b> Your build failed in stage: ${CURRENT_STAGE}.</b>
                                    </div > 
                                </div>
                              """,
                        mimeType:"text/html",
                        to: 'franciscoscc15@gmail.com'
                    )
                }
            }
            success {
                script {
                    emailext (
                        attachLog:true,
                        subject: "Build Succeeded in Stage: ${CURRENT_STAGE}",
                        body: """
                                <div class="container">
                                    <div class="alert alert-success" role = "alert">
                                        <b> Your build succeeded in stage: ${CURRENT_STAGE}.</b>
                                    </div > 
                                </div>
                              """,
                        mimeType:"text/html",
                        to: 'franciscoscc15@gmail.com'
                    )
                }
            }
        }
}
