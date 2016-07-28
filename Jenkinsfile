node {
    env.JAVA_HOME="${tool 'jdk1.8.0_102'}"
    //env.PATH = "${tool 'Maven 3'}/bin:${env.PATH}"
    //sh 'mvn clean package'
}

stage 'Unit Tests'

node('linux') {
    checkout scm
    mvn 'clean package -Dgroups=unit'
    //step([$class: 'Publisher'])
    //step([$class: 'Publisher', reportFilenamePattern: 'core/**/testng-results.xml'])
    archive 'core/target/*.jar'
    slackSend channel: '#reg_sla_monitoring', color: 'green', message: 'Test Messsage - Build Successful'
}

stage 'Integration Tests'

node {
    mvn 'clean test -Dgroups=unit,integration'
    step([$class: 'Publisher'])
}



def mvn(args) {
    sh "${tool 'Maven 3.x'}/bin/mvn ${args}"
}
