stage 'Unit Test'

node {

    env.JAVA_HOME="${tool 'jdk1.8.0_102'}"
    mvn '-v'

    env.JAVA_HOME="${tool 'jdk1.8.0_92'}"
    mvn '-v'


    //env.PATH="${env.JAVA_HOME}/bin:${env.PATH}"
    //sh 'java -version'

    checkout scm
    mvn 'clean package'
    step([$class: 'Publisher'])
    archive 'core/target/*.jar'
}

node {
  jdk = tool name: 'jdk1.8.0_92'
  env.JAVA_HOME = "${jdk}"

  echo "jdk installation path is: ${jdk}"

  // next 2 are equivalents
  //sh "${jdk}/bin/java -version"

  // note that simple quote strings are not evaluated by Groovy
  // substitution is done by shell script using environment
  //sh '$JAVA_HOME/bin/java -version'
}

def mvn(args) {
    sh "${tool 'Maven 3.x'}/bin/mvn ${args}"
}
