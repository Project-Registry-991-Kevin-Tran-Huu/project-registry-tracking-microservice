pipeline {
environment {
registry = "devaraj1234/microservice-registry"
registryCredential = 'docker_hub_id'
dockerImage = ''
}
agent any
stages {
stage('Cloning Git') {
steps {
git 'https://github.com/Project-Registry-991-Kevin-Tran-Huu/project-registry-tracking-microservice.git'
}
}
stage('Building image') {
steps{
script {
dockerImage = docker build -t devaraj1234/microservice-registry:tracking-service . + ":$BUILD_NUMBER"
}
}
}
stage('Deploy image') {
steps{
script {
docker.withRegistry( '', registryCredential ) {
dockerImage.push()
}
}
}
}
stage('Cleaning up') {
steps{
sh "docker rmi $registry:$BUILD_NUMBER"
}
}
}
}