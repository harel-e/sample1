stage 'Unit Test'

node {
    checkout scm
    mvn 'clean package'
    step([$class: 'hudson.plugins.testng.Publisher', reportFilenamePattern: 'target/surefire-reports/testng-results.xml'])
}

stage 'Integration'

node {
    mvn 'clean package'
}



def mvn(args) {
    sh "${tool 'Maven 3.x'}/bin/mvn ${args}"
}
