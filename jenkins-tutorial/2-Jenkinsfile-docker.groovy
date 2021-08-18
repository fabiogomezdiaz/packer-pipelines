pipeline {
  agent {
    docker {
      image env.AGENT_DOCKER_IMAGE ?: 'fabiogomezdiaz/jenkins-agent-packer-docker:latest'
      args "--entrypoint='' -v /certs/client:/certs/client"
    }
  }

  environment {
    PACKER_CACHE_DIR = "${env.WORKSPACE_TMP}/.packer.d/packer_cache"
    PACKER_CONFIG_DIR = "${env.WORKSPACE_TMP}/.packer.d"
    PACKER_HOME_DIR = "${env.WORKSPACE_TMP}/.packer.d"
    PACKER_PLUGIN_PATH = "${env.WORKSPACE_TMP}/.packer.d/plugins"
    TMPDIR = "${env.WORKSPACE_TMP}"
    DOCKER_HOST = "tcp://${DOCKER_HOST_IP}:2376"
  }

  stages {
    stage('Packer - Build Docker Image') {
      steps {
        sh """
        #!/bin/sh
        cd jenkins-tutorial
        packer init .
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