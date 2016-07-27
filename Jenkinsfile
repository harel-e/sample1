stage 'Unit Test'

node {
    checkout scm
    mvn 'clean package'
    step([$class: 'JUnitResultArchiver', testResults: '**/target/*.xml'])
}

stage 'Integration'

node {
    mvn 'clean package'
}



def mvn(args) {
    sh "${tool 'Maven 3.x'}/bin/mvn ${args}"
}
