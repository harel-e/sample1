stage 'Unit Test'

node {
    checkout scm
    mvn 'clean package'
}

stage 'Integration'

node {
    mvn 'clean package'
}



def mvn(args) {
    sh "${tool 'Maven 3.x'}/bin/mvn ${args}"
}
