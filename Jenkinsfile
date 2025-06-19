pipeline {
  agent any

  tools {
    maven "Maven3"
  }

  environment {
        ARTIFACTORY_SERVER = 'jFrog_Artifactory'
        ARTIFACTORY_CRED = credentials('jFrog_Credentials')
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

    stage('Upload to Artifactory') {
            steps {
                script {
                    def server = Artifactory.server(ARTIFACTORY_SERVER)
                    def uploadSpec = """{
                      "files": [
                        {
                          "pattern": "build/libs/*.war",
                          "target": "libs-release-local/SimpleApp/"
                        }
                      ]
                    }"""
                    server.upload spec: uploadSpec
                }
            }
    }
  }
}