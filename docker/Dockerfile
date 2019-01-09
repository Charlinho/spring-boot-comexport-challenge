FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
COPY comexport-1.0.jar comexport-1.0.jar
RUN sh -c 'touch /comexport-1.0.jar'
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar /comexport-1.0.jar"]