/*pipeline {
    agent {
        docker { image 'node:14-alpine' }
    }
    stages {
        stage('Test') {
            steps {
                sh 'node --version'
            }
        }
    }
}*/

pipeline {
  agent none

  stages {
    stage('Packer - Build Docker Image') {
      agent {
        docker {
          image 'hashicorp/packer:light'
          args '-v $(pwd):/workspace -w /workspace -e PACKER_PLUGIN_PATH=/workspace/.packer.d/plugins'
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