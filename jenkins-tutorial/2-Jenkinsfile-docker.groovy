pipeline {
  agent {
    docker {
      //mage 'hashicorp/packer:light'
      //args "--entrypoint=''"
      image 'fabiogomezdiaz/jenkins-agent-packer-docker:latest'
      args "--entrypoint='' -v /certs/client:/certs/client"
    }
  }

  environment {
    PACKER_CACHE_DIR = "${env.WORKSPACE_TMP}/.packer.d/packer_cache"
    PACKER_CONFIG_DIR = "${env.WORKSPACE_TMP}/.packer.d"
    PACKER_HOME_DIR = "${env.WORKSPACE_TMP}/.packer.d"
    PACKER_PLUGIN_PATH = "${env.WORKSPACE_TMP}/.packer.d/plugins"
    TMPDIR = "${env.WORKSPACE_TMP}"
  }

  stages {
    stage('Packer - Build Docker Image') {
      steps {
        sh """
        #!/bin/sh
        printenv
        docker ps
        cd jenkins-tutorial
        packer init .
        ls -la ${PACKER_PLUGIN_PATH}
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