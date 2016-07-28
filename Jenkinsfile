stage 'Unit Test'

node {
    checkout scm
    mvn 'clean package'
    step([$class: 'Publisher'])
    archive 'core/target/*.jar'
}



def mvn(args) {
    sh "${tool 'Maven 3.x'}/bin/mvn ${args}"
}
