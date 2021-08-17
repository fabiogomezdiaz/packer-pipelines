pipeline {
  agent {
    docker {
      image 'fabiogomezdiaz/docker-packer:latest'
      args "--privileged --entrypoint=''"
    }
  }

  environment {
    PACKER_HOME_DIR = env.WORKSPACE + "/.packer.d"
    PACKER_PLUGIN_PATH = env.WORKSPACE + "/.packer.d/plugins"
    TMPDIR = env.WORKSPACE_TMP
  }

  stages {
    stage('Packer - Build Docker Image') {
      steps {
        sh """
        #!/bin/sh
        ls -la
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