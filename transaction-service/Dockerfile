FROM openjdk:11
VOLUME /tmp
ADD ./target/transaction-service-1.0-SNAPSHOT.jar transaction.jar
ENTRYPOINT ["java","-jar","transaction.jar"]