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

    stage('Upload to Artifactory') {
            steps {
                script {
                    def server = Artifactory.server(ARTIFACTORY_SERVER)
                    sh 'ls -l target'
                    def uploadSpec = """{
                      "files": [
                        {
                          "pattern": "*.war",
                          "target": "libs-release-local/my-app/1.0.2/"
                        }
                      ]
                    }"""
                    server.upload spec: uploadSpec
                }
            }
    }

    stage("Deploy into DEV Server") {
        steps {
            script {
                sh 'chmod +x scripts/deploy_weblogic.sh && ./scripts/deploy_weblogic.sh'
            }
        }
    }
  }
}