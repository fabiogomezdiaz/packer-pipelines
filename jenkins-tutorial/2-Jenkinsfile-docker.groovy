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
      args "--privileged -e PACKER_HOME_DIR='.packer.d' --entrypoint=''"
    }
  }

  stages {
    stage('Packer - Build Docker Image') {
      steps {
        sh """
        #!/bin/sh
        mkdir tmp
        cd jenkins-tutorial
        packer init .
        ls -la .packer.d/plugins
        packer build -force .
        """
      }
    }
    stage('Docker - Verify Docker Image') {
      steps {
        sh """
        #!/bin/sh
        docker images
        """
      }
    }
  }
}