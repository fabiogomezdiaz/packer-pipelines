pipeline {
  agent any

  stages {
    stage('Setting PATH') {
      steps {
        echo 'Setting PATH for Packer...'
        sh 'export PATH="/usr/local/bin:${PATH}"'
      }
    }
    stage('Packer - Build Docker Image') {
      steps {
        sh 'export PATH="/usr/local/bin:${PATH}"'
        echo 'Building Ubuntu Container Image using Packer...'
        sh 'cd docker'
        sh 'packer init .'
        sh 'packer build -force .'
      }
    }
    stage('Docker - Verify Docker Image') {
      steps {
        sh 'export PATH="/usr/local/bin:${PATH}"'
        echo 'Verifying Container Image using Docker..'
        sh 'docker images'
      }
    }
  }
}