pipeline {
    agent any
    stages {
        stage ('build'){
        	steps {
             	sh 'mvn clean package spring-boot:repackage -DskipTests=true'
             	}
        	}
        	

    }
}