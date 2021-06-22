pipeline {
	environment {
		registry = "devaraj1234/microservice-registry"
		registryCredential = 'docker_hub_id'
		dockerImage = ''
	}	
    agent any
    stages {
       
        stage ('Build Application'){
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
      		steps{
      			sh 'docker build -t devaraj1234/microservice-registry:tracking .'
      		}
    	}
    	
    	stage('Push Docker image') {
            steps {
            script {
				docker.withRegistry( '', registryCredential ) {
                	sh 'docker push devaraj1234/microservice-registry:tracking'
                	}
               	}
              }
           }
           
        stage('Run Docker Image') {
        steps{

        	sh 'docker run -d --name=tracking --link consul:consul -p 8083:8083 devaraj1234/microservice-registry:tracking'
        }
        
        }
        
    }
}