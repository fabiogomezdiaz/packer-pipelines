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
      args "--privileged -e PACKER_HOME_DIR='.packer.d' -e PACKER_PLUGIN_PATH='.packer.d/plugins' -e TMPDIR=" + env.WORKSPACE_TMP + " --entrypoint=''"
    }
  }

  stages {
    stage('Packer - Build Docker Image') {
      steps {
        sh """
        #!/bin/sh
        cd jenkins-tutorial
        packer init .
        ls -la .packer.d/plugins
        printenv
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