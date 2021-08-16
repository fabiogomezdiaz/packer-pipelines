pipeline {
  agent none

  stages {
    stage('Packer - Build Docker Image') {
      agent {
        docker {
          image 'hashicorp/packer:light'
        }
      }
      steps {
        sh """
        cd jenkins-tutorial
        packer init .
        packer build -force .
        """
      }
    }
    /*stage('Docker - Verify Docker Image') {
      steps {
        sh """
        #!/bin/bash
        docker images
        """
      }
    }*/
  }
}