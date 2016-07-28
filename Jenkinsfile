node {
    env.JAVA_HOME="${tool 'jdk1.8.0_102'}"
    //env.PATH = "${tool 'Maven 3'}/bin:${env.PATH}"
    //sh 'mvn clean package'
}

stage 'Unit Tests'

node {
    checkout scm
    mvn 'clean package -Dgroups=unit'
    step([$class: 'Publisher'])
    archive 'core/target/*.jar'
}

stage 'Integration Tests'

node {
    mvn 'clean test -Dgroups=integration2'
    step([$class: 'Publisher'])
}



def mvn(args) {
    sh "${tool 'Maven 3.x'}/bin/mvn ${args}"
}
