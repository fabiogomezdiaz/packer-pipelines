pipeline {
  agent {
    docker {
      image 'hashicorp/packer:light'
      //image 'fabiogomezdiaz/docker-packer:latest'
      //args "--privileged=true --entrypoint=''"
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
        cd jenkins-tutorial
        packer init .
        ls -la ${PACKER_PLUGIN_PATH}
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