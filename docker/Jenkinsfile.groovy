pipeline {
  agent any

  stages {
    stage('Packer - Build Docker Image') {
      steps {
        sh """
        #!/bin/bash
        echo 'Building Ubuntu Container Image using Packer...'
        cd docker
        ls -la .
        packer init .
        packer build -force .
        """
      }
    }
    stage('Docker - Verify Docker Image') {
      steps {
        sh """
        #!/bin/bash
        echo 'Verifying Container Image using Docker..'
        docker images
        """
      }
    }
  }
}