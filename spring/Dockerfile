FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD ./build/libs/ticker-decorator-spring-1.0.0-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Dspring.profiles.active=$SPRING_PROFILES -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]