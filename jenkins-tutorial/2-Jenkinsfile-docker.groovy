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
      image 'fabiogomezdiaz/docker-packer:latest'
      args "--privileged -e PACKER_PLUGIN_PATH='.packer.d/plugins' --entrypoint=''"
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