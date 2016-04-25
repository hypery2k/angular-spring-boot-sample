node {
   env.JAVA_HOME = tool 'jdk-8-oracle'
   def mvnHome = tool 'Maven 3.3.1'
   env.PATH="${env.JAVA_HOME}/bin:${mvnHome}/bin:${env.PATH}"
   
   stage 'Checkout'
   git url: 'https://github.com/hypery2k/angular-spring-boot-sample.git'

   stage 'Build'
   sh "${mvnHome}/bin/mvn clean package"
   
   stage 'Unit-Tests'
   sh "${mvnHome}/bin/mvn test"
   
   stage 'Integration-Tests'
   wrap([$class: 'Xvfb']) {
        sh "${mvnHome}/bin/mvn -Pdocker clean verify"
   }
   
   step([
       $class: 'ArtifactArchiver', 
       artifacts: '**/target/*.jar', 
       fingerprint: true
    ])
   step([
       $class: 'JUnitResultArchiver', 
       testResults: 'angular-spring-boot-webapp/target/surefire-reports/TEST*.xml,target/failsafe-reports/TEST*.xml'
   ])
   publishHTML(target: [
       reportDir:'angular-spring-boot-webapp/target/site/serenity/',
       reportFiles:'index.html',
       reportName:'Serenity Test Report', 
       keepAll:true, 
       alwaysLinkToLastBuild:true,
       allowMissing: false
    ])
 
}