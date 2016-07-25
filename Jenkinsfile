stage 'Dev2'

node {
    sh 'echo hello world'
    checkout scm
    mvn 'clean install'
}


def mvn(args) {
    sh "${tool 'Maven 3.x'}/bin/mvn ${args}"
}
