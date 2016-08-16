stage 'Unit Tests'

node {
    checkout scm
    try {
        mvn 'clean package -Dgroups=unit'
        archive 'core/target/*.jar'
        //step([$class: 'Publisher'])
        //step([$class: 'Publisher', reportFilenamePattern: 'core/**/testng-results.xml'])
        slackSend channel: "#reg_sla_monitoring", color: "good", message: "Build Lab - ${env.JOB_NAME} - Build #${env.BUILD_NUMBER} - Success"
    } catch(e)  {
        slackSend channel: "#reg_sla_monitoring", color: "warning", message: "Build Lab - ${env.JOB_NAME} - Build #${env.BUILD_NUMBER} - Fail"
        throw e
    }
}

stage 'Integration Tests'

node {
    mvn 'clean test -Dgroups=unit,integration'
    step([$class: 'Publisher'])
}

stage 'Deploy'

node {
  sh 'whoami'
  mvn 'clean install'
  step([$class: 'Publisher'])
//  sshagent (credentials: ['harel-github']) {
//    sh 'ssh -o StrictHostKeyChecking=no -l harel 172.16.63.131 uname -a'
//  }
}

def mvn(args) {
    env.JAVA_HOME="${tool 'jdk1.8.0_102'}"
    sh "${tool 'Maven 3.x'}/bin/mvn ${args}"
}
