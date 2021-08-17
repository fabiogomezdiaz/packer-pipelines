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
  agent {
    docker {
      image 'hashicorp/packer:light'
      args "-e PACKER_PLUGIN_PATH='.packer.d/plugins' --entrypoint=''"
    }
    docker {
      image 'docker:20.10-dind'
      args "--privileged"
    }
  }

  stages {
    stage('Packer - Build Docker Image') {
      steps {
        sh """
        echo ${WORKSPACE}
        cd jenkins-tutorial
        packer init .
        ls -la .packer.d
        #packer build -force .
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