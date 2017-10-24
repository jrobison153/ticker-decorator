FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD ./build/libs/ticker-decorator-spring-1.0.0-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'
RUN apk --no-cache add curl
ENV JAVA_OPTS=""
HEALTHCHECK --start-period=30s CMD curl --fail http://localhost:8081/health || exit 1
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Dspring.profiles.active=$SPRING_PROFILES -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]