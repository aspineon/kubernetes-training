FROM openjdk:11.0.7-slim
ADD target/configuration.jar /
CMD java -jar \
    -Dspring.profiles.active=$ACTIVE_PROFILE \
    configuration.jar