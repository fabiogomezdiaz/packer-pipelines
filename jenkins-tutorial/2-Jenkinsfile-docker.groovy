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
          args '-v $WORKSPACE:/workspace -w /workspace -e PACKER_PLUGIN_PATH=/workspace/.packer.d/plugins --entrypoint='''
        }
      }
      steps {
        sh """
        echo ${WORKSPACE}
        cd jenkins-tutorial
        mkdir -p .packer.d/plugins
        ls -la .packer.d
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