(defproject simple-mailservice-demo "0.1.0-SNAPSHOT"
  :description "read README.md"
  :java-source-paths ["src/main/java"]
  :dependencies [[javax.mail/mail "1.5.0-b01"]
                 [org.springframework/spring-context-support "4.2.5.RELEASE"]
                 [ch.qos.logback/logback-classic "1.1.7"]
                 [com.google.inject/guice "4.0"]
                 [com.miyamofigo/java8.nursery "0.2.0-SNAPSHOT"]]
  :repositories [["ghpages" "http://miyamofigo.github.io/java8-nursery"]])

