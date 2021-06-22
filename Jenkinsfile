pipeline {
    agent any
    stages {
        stage ('build'){
             sh 'mvn clean package spring-boot:repackage -DskipTests=true'
        }

    }
}