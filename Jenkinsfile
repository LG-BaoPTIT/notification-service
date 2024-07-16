pipeline{
    agent any

    environment{
        IMAGE_TAG = 'v1'
        DOCKERHUB_PWD = credentials("dockerhup-pwd")
    }

    tools{
        maven 'maven_3_5_0'
    }

    stages{
        stage('Build maven'){
            steps{
                script{
                    checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/LG-BaoPTIT/notification-service']])
                    sh 'mvn clean install'
                }
            }
        }

        stage('Build docker image'){
            steps{
                script{
                    sh 'docker build -t lgbptit/notification-service:${IMAGE_TAG} .'
                }
            }
        }

        stage('Push image to Hub'){
            steps{
                script{
                    sh 'docker login -u lgbptit -p ${DOCKERHUB_PWD}'
                    sh 'docker push lgbptit/notification-service:${IMAGE_TAG}'
                }
            }
        }

        stage('Stop and remove old container'){
            steps{
                script{
                    sh 'docker stop notification-service-container || true'
                    sh 'docker rm notification-service-container || true'
                }
            }
        }

        stage('Run docker container'){
            steps{
                script{
                    sh 'docker run -d --name notification-service-container -p 9003:9003 --network myNetwork --ip 172.19.0.8 lgbptit/notification-service:${IMAGE_TAG}'
                }
            }
        }

    }

}