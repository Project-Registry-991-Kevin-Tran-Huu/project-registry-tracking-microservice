pipeline {
	agent any
    stages {
        stage('Build Project') {
      // build project via maven
      sh 'mvn clean package spring-boot:repackage -DskipTests=true'
    	}
    }
}