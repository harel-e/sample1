stage 'Unit Test'

node {
    checkout scm
    mvn 'clean package'
    step([$class: 'Publisher'])
    //step([$class: 'ArtifactArchiver', artifacts: '*.jar', excludes: null])
    //archive 'target/*.jar'
    //step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])

}

stage 'Integration'

node {
    mvn 'clean package'
}



def mvn(args) {
    sh "${tool 'Maven 3.x'}/bin/mvn ${args}"
}
