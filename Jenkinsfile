stage 'Dev'

node {
    checkout scm
    mvn 'clean package'
}

stage 'QA'

node {
    mvn 'clean package'
}



def mvn(args) {
    sh "${tool 'Maven 3.x'}/bin/mvn ${args}"
}
