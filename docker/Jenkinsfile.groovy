pipeline {
  agent any

  stages {
    stage('Packer - Build Docker Image') {
      steps {
        echo 'Building Ubuntu Container Image using Packer...'
        sh 'cd docker'
        sh 'ls -la .'
        sh 'packer init .'
        sh 'packer build -force .'
      }
    }
    stage('Docker - Verify Docker Image') {
      steps {
        echo 'Verifying Container Image using Docker..'
        sh 'docker images'
      }
    }
  }
}