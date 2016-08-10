stage 'Unit Tests'

node {
    checkout scm
    try {
        mvn 'clean package -Dgroups=unit'
        archive 'core/target/*.jar'
        //step([$class: 'Publisher'])
        //step([$class: 'Publisher', reportFilenamePattern: 'core/**/testng-results.xml'])
        slackSend channel: "#reg_sla_monitoring", color: "good", message: "Test Messsage - ${env.JOB_NAME} ${env.BRANCH_NAME} - Build #${env.BUILD_NUMBER} - SUCCESSFUL!"
    } catch(e)  {
        slackSend channel: "#reg_sla_monitoring", color: "warning", message: "Test Messsage - ${env.JOB_NAME} ${env.BRANCH_NAME} - Build #${env.BUILD_NUMBER} - FAILED!"
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
//  sshagent (credentials: ['harel-github']) {
//    sh 'ssh -o StrictHostKeyChecking=no -l harel 10.47.231.126 uname -a'
//  }
}

def mvn(args) {
    env.JAVA_HOME="${tool 'jdk1.8.0_102'}"
    sh "${tool 'Maven 3.x'}/bin/mvn ${args}"
}
