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
        stage('Configure E-mail') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'gmail-credentials', passwordVariable: 'GMAIL_PASSWORD', usernameVariable: 'GMAIL_USERNAME')]) {
                        emailext {
                            // SMTP server hostname
                            smtpServer('smtp.office365.com')
                            smtpPort(587)
                            starttls(true)
                            // Use curly braces for variable interpolation
                            username("${GMAIL_USERNAME}")
                            password("${GMAIL_PASSWORD}")
                            from('sender@example.com')
                        }
                    }
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
                        to: 'a2019133920@isec.pt'
                    )
                }
            }
            success {
                script {
                    def succededStageName = env.STAGE_NAME
                    emailext (
                        subject: "Build Succeeded in Stage: ${succededStageName}",
                        body: "Your build succeeded in stage: ${succededStageName}.",
                        to: 'a2019133920@isec.pt'
                    )
                }
            }
        }
}
