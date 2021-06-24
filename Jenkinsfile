pipeline {
    agent {
        docker {
            image 'maven:3-alpine'
            args '--rm -v /root/.m2:/root/.m2 -p 8808:8808  -v /opt/logs:/opt/logs -v /etc/localtime:/etc/localtime --memory=4g --memory-swap=4.5g --name=survey-ki --hostname=127.0.0.1 --net=host'
        }
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Run') {
            steps {
                sh 'mvn spring-boot:run -Dspring-boot.run.profiles=uat -Dspring-boot.run.jvmArguments=-Duser.timezone=Asia/Ho_Chi_Minh'
                input message: 'Finished using the web site? (Click "Process" to continue)'
            }
        }
    }
}
