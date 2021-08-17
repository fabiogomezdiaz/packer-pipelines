pipeline {
  agent {
    docker {
      image 'fabiogomezdiaz/docker-packer:latest'
      args "--privileged --entrypoint='' -v /var/lib/docker:/var/lib/docker -v /var/run/docker.sock:/var/run/docker.sock --group-add=\$(stat -c %g /var/run/docker.sock)"
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