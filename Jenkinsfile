pipeline {
  agent any

  environment {
    Name = "Rohit Kumar"
  }

  stages {


    stage("Build the Application"){
        steps {
            script {
                def version = new Date().format("yyyyMMdd-HHmm")
                echo "Version: ${version}"
                sh 'mvn clean package -DskipTests=true'
                sh "pwd"
                dir("webapp/target/"){
                    sh "pwd"
                    stash includes: "*.war", name: 'maven-build'
                }
            }
        }
        post {
            success {
                echo "BUILD IS SUCCESSFULL"
            }
        }
    }

    stage("Testing the Application") {
        parallel {
            stage("Testing in DEV Environment") {
                steps {
                    sh 'mvn test'
                }
            }
        }
    }

    stage("Deploy into DEV Server") {

        steps {
            dir("/var/www/html") {
                script {
                    try {
                        unstash 'maven-build'
                    } catch (Exception e) {
                        echo "Failed to unstash: ${e.getMessage()}"
                        throw e
                    }
                }
            }
            sh """
            cd /var/www/html/
            jar -xvf webapp.war
            """
        }
    }
  }
}