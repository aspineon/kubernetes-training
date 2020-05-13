FROM openjdk:11.0.7-slim
ADD target/products.jar /
CMD java -jar \
    -Dspring.profiles.active=$ACTIVE_PROFILE \
    -Dspring.cloud.config.uri=$CONFIGURATION_SERVER_URI \
    products.jar