node {
    env.JAVA_HOME="${tool 'jdk1.8.0_102'}"
    //env.PATH = "${tool 'Maven 3'}/bin:${env.PATH}"
    //sh 'mvn clean package'
}

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
  sshagent (credentials: ['d43dd4d4-a68f-4401-b098-49827ed06f3f']) {
    sh 'ssh -v -o StrictHostKeyChecking=no -l harel 172.16.63.131 uname -a'
  }
}

def mvn(args) {
    sh "${tool 'Maven 3.x'}/bin/mvn ${args}"
}
