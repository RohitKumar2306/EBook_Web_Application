pipeline {
  agent any

  tools {
    maven "Maven3"
  }

  stages {


    stage("Build the Application"){
        steps {
            script {
                sh 'mvn clean package -DskipTests=true'
                dir("target/"){
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
    stage("Deploy into DEV Server") {
        steps {
            dir("/Oracle/Middleware/Oracle_Home/user_projects/domains/base_domain/autodeploy") {
                script {
                    try {
                        unstash 'maven-build'
                    } catch (Exception e) {
                        echo "Failed to unstash: ${e.getMessage()}"
                        throw e
                    }
                }
            }
        }
    }
  }
}