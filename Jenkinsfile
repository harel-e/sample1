stage 'Unit Tests'

node {
        checkout scm
        mvn 'clean package -Dgroups=unit'
        archive 'core/target/*.jar'
        slackSend channel: "#reg_sla_monitoring", color: "good", message: "Build Lab - ${env.JOB_NAME} - Build #${env.BUILD_NUMBER} - Success"
}

stage 'Integration Tests'

node {
        mvn 'clean test -Dgroups=integration'
}

stage 'Deploy'

node {
  sh 'whoami'
  mvn 'clean -Pdocker verify'
  step([$class: 'Publisher'])
//  sshagent (credentials: ['harel-github']) {
//    sh 'ssh -o StrictHostKeyChecking=no -l harel 172.16.63.131 uname -a'
//  }
}

def mvn(args) {
    env.JAVA_HOME="${tool 'jdk1.8.0_102'}"
    try {
        sh "${tool 'Maven 3.x'}/bin/mvn ${args}"
    } catch(e) {
        step([$class: 'Publisher'])
        slackSend channel: "#reg_sla_monitoring", color: "warning", message: "Build Lab - ${env.JOB_NAME} - Build #${env.BUILD_NUMBER} - Fail"
        throw e
    }
}
