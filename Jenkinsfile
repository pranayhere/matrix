pipeline {
  agent {
    label 'master'
  }
  stages {
    stage('Gather Facts') {
      steps {
        script {
          def configs = [
            [branch:"develop", suffix: "dev", db: "91.0.10.91", profile: "dev", deploy: true],
            [branch:"release", suffix: "qa",db: "91.0.10.19", profile: "qa", deploy: true],
            [branch:"staging", suffix: "rc",db: "91.0.10.20", profile: "staging", deploy: true],
            [branch:"master", suffix: "final", db: "", profile: "prod", deploy: false]
          ]

          commit_message = sh(script: "git log -1 --pretty=%B ", returnStdout:true).trim().toLowerCase()
          def conf = null

          configs.each{ config ->
          if(env.GIT_BRANCH.contains(config.branch)){
            conf = config
          }
        }

        if(conf == null){
          currentBuild.result = 'ABORTED'
          error("No suitable config found. Build is aborted")
        }

        deploy = conf.deploy
        profile = conf.profile

        if(deploy){
          def online = false
          for (slave in jenkins.model.Jenkins.instance.getNodes()) {
            if (slave.toComputer().isOnline() && slave.getDisplayName().equalsIgnoreCase(profile)) {
              online = true
              break
            }
          }

          if(!online){
            currentBuild.result = 'ABORTED'
            error("Agent ${profile} is offline. Build is aborted")
          }
        }

        def commit_id = sh(script: "git log -1 --pretty=%h ", returnStdout:true).trim()
        image = "registry.pharmeasy.in:5000/${app}:${commit_id}.${conf.suffix}"
        service_name = "${profile}_${app}"
        db = "-e DB_HOST=${conf.db} -e DB_NAME=mercury -e DB_USER=hermes -e DB_PASS=pem@2018"

        def logging = "--log-driver=awslogs --log-opt awslogs-region=ap-south-1 --log-opt awslogs-group=${service_name} --log-opt awslogs-create-group=true --log-opt tag='{{.Name}}' "
        def apm = "-e ELASTIC_APM_SERVER_URLS='http://91.0.10.93:8200'"
        opts = "${apm} ${db} ${logging} --network mercury --detach --reserve-memory 1024M --constraint 'node.labels.lot == ${profile}' --name ${service_name} ${image}"

        author = sh(script: "git log -1 --pretty=%an ", returnStdout:true).trim()

        echo "Image: ${image}"
      }

    }
  }
  stage('Build') {
    steps {
      sh './gradlew -x test clean build'
    }
  }
  stage('Build Docker Image') {
    steps {
      sh "sudo docker build -t ${image} --build-arg profile=${profile} ."
    }
  }
  stage('Push Docker Image') {
    steps {
      sh "sudo docker push ${image} "
    }
  }
  stage('Clean Local Docker Image') {
    steps {
      sh "sudo docker rmi ${image} "
    }
  }
  stage('Deploy') {
    when {
      expression {
        deploy == true
      }

    }
    steps {
      node(label: profile) {
        script {
          try{
            sh "sudo docker network create -d overlay mercury "
          }catch(Exception e){
            echo "network already exists..."
          }

          def create = false
          try{
            sh "sudo docker service ps ${service_name} "
          }catch(Exception e){
            create = true
          }

          if(create){
            sh "sudo docker service create ${opts} "
          }else{
            sh "sudo docker service update -d --image ${image} ${service_name}  "
          }
        }

      }

    }
  }
}
environment {
  app = 'inventory-data-service'
  db = ''
  author = ''
  commit_message = ''
  service_name = ''
  image = ''
  opts = ''
  deploy = false
  profile = 'dev'
}
post {
  success {
    googlechatnotification(url: 'id:gchat-builds', message: "SUCCESS #${env.BUILD_NUMBER} [${env.JOB_NAME}] [${author}] ${commit_message}")

  }

  failure {
    googlechatnotification(url: 'id:gchat-builds', message: "*FAILED* #${env.BUILD_NUMBER} [${env.JOB_NAME}] [${author}] ${commit_message}. <${env.BUILD_URL}|Logs>")

  }

}
}