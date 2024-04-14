FROM jenkins/jenkins:lts
USER root

# Install Docker
RUN curl -fsSL https://get.docker.com -o get-docker.sh && \
    sh get-docker.sh && \
    rm get-docker.sh && \
    usermod -aG docker jenkins

# Adjust permissions and ownership
RUN usermod -aG docker jenkins


USER jenkins

