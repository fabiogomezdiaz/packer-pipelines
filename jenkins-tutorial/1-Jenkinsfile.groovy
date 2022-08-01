pipeline {
  agent any

  stages {
    stage('Packer - Build Docker Image') {
      steps {
        sh """
        #!/bin/bash
        cd jenkins-tutorial
        /usr/bin/packer init .
        /usr/bin/packer build -force .
        """
      }
    }
    stage('Docker - Verify Docker Image') {
      steps {
        sh """
        #!/bin/bash
        sleep 60
        docker images
        """
      }
    }
  }
}
