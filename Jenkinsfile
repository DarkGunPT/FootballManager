pipeline {
    agent any
    environment {
        BACKEND_IMAGE = 'backend'
        FRONTEND_IMAGE = 'frontend'
        DOCKER_HUB_REPO = 'darkgunpt/football_pd'
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
                    sh 'echo ${DOCKER_PASSWORD} | docker login -u ${DOCKER_USERNAME} --password-stdin'
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
                                <html>
                                <head>
                                    <style>
                                .container {
                                    margin: 20px;
                                }
                                .alert {
                                    padding: 15px;
                                    border: 1px solid transparent;
                                    border-radius: .25rem;
                                    margin-bottom: 20px;
                                }
                                .alert-success {
                                    color: #721c24;
                                    background-color: #f8d7da;
                                    border-color: #f5c6cb;
                                }
                            </style>    
                                    
                                </head>
                                <body>
                                    <div class="container">
                                        <div class="alert alert-success" role="alert">
                                            <strong>Build Failed!</strong> Your build has failed in ${CURRENT_STAGE}.
                                        </div>
                                    </div>
                                </body>
                                </html>
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
                        subject: "Congratualions! Your Build went through all stages without errors!",
                        body: """
                                <html>
                                <head>
                                    <style>
                                .container {
                                    margin: 20px;
                                }
                                .alert {
                                    padding: 15px;
                                    border: 1px solid transparent;
                                    border-radius: .25rem;
                                    margin-bottom: 20px;
                                }
                                .alert-success {
                                    color: #155724;
                                    background-color: #d4edda;
                                    border-color: #c3e6cb;
                                }
                            </style>
                                </head>
                                <body>
                                    <div class="container">
                                        <div class="alert alert-success" role="alert">
                                            <strong>Build Succeeded!</strong> Your build has succeeded.
                                        </div>
                                    </div>
                                </body>
                                </html>
                            """,
                        mimeType:"text/html",
                        to: 'franciscoscc15@gmail.com'
                    )
                }
            }
        }
}
