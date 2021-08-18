pipeline {
  agent {
    docker {
      //mage 'hashicorp/packer:light'
      //args "--entrypoint=''"
      image 'fabiogomezdiaz/jenkins-agent-packer-docker:latest'
      args "--entrypoint=''"
    }
  }

  environment {
    PACKER_CACHE_DIR = "${env.WORKSPACE_TMP}/.packer.d/packer_cache"
    PACKER_CONFIG_DIR = "${env.WORKSPACE_TMP}/.packer.d"
    PACKER_HOME_DIR = "${env.WORKSPACE_TMP}/.packer.d"
    PACKER_PLUGIN_PATH = "${env.WORKSPACE_TMP}/.packer.d/plugins"
    TMPDIR = "${env.WORKSPACE_TMP}"
    DOCKER_HOST = "tcp://docker:2376"
    DOCKER_CERT_PATH = "/certs/client"
    DOCKER_TLS_VERIFY = 1
  }

  stages {
    stage('Packer - Build Docker Image') {
      steps {
        sh """
        #!/bin/sh
        printenv
        #docker ps
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