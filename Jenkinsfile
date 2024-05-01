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
        // Specify the Maven installation defined in Jenkins
        maven 'maven'
    }
    stages {
        stage('Login to docker') {
            steps {
                script {
                    CURRENT_STAGE = 'Login to docker'
                    echo "Logging to docker'
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
                    def failedStageName = ${CURRENT_STAGE}
                    emailext (
                        subject: "Build Failed in Stage: ${failedStageName}",
                         body: """
                                <div class="container">
                                    <div class="alert alert-success" role = "alert">
                                        <b> Your build failed in stage: ${failed}.</b>
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
                    def succededStageName = ${CURRENT_STAGE}
                    emailext (
                        subject: "Build Succeeded in Stage: ${succededStageName}",
                        body: """
                                <div class="container">
                                    <div class="alert alert-success" role = "alert">
                                        <b> Your build succeeded in stage: ${succededStageName}.</b>
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
