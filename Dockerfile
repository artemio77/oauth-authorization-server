FROM java:8
VOLUME /tmp

ADD target/oauth-authorization-server.jar target/oauth-authorization-server.jar
RUN bash -c 'touch target/oauth-authorization-server.jar'
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=local","target/oauth-authorization-server.jar"]