pipeline {
    def dockerImageTag = "${dockerRepoUrl}/${dockerImageName}:${env.BUILD_NUMBER}"
    agent any
    stages {
        stage ('build'){
        	steps {
             	sh 'mvn clean package spring-boot:repackage'
             	}
        	}
        stage('Publish Tests Results'){
      		steps {
          		echo "Publish junit Tests Results"
		  		junit '**/target/surefire-reports/TEST-*.xml'
		  		archive 'target/*.jar'
        	}
    	}
    	stage('Build Docker Image') {
      		steps {
      		   sh "ls -all /var/run/docker.sock"
      			sh "mv ./target/app*.jar ./data" 
      			dockerImage = docker.build("tracking-microservice")
    		}
    	}
    
    
    }
}