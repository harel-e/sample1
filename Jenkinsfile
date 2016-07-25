stage 'Dev'

node {
    sh 'echo hello world'
    checkout scm
    mvn '-o clean package'
}


def mvn(args) {
    sh "${tool 'Maven 3.x'}/bin/mvn ${args}"
}
