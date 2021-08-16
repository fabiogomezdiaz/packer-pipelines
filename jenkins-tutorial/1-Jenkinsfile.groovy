pipeline {
  agent any

  stages {
    stage('Packer - Build Docker Image') {
      steps {
        sh """
        #!/bin/bash
        cd jenkins-tutorial
        packer init .
        packer build -force .
        """
      }
    }
    stage('Docker - Verify Docker Image') {
      steps {
        sh """
        #!/bin/bash
        docker images
        """
      }
    }
  }
}