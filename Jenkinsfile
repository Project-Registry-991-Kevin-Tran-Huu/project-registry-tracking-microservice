pipeline {
    agent any
    stages {
        stage ('build'){
        	steps {
             	sh 'mvn clean package spring-boot:repackage -DskipTests=true'
             	}
        	}
        stage('Publish Tests Results'){
      		steps {
          		echo "Publish junit Tests Results"
		  		junit '**/target/surefire-reports/TEST-*.xml'
		  		archive 'target/*.jar'
        	}
    	}

    
    }
}