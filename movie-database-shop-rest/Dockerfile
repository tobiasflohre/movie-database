FROM java:7
MAINTAINER Dennis Schulte <mail@dennis-schulte.de>

# install updates
RUN apt-get update && \
    apt-get clean && \
    rm -rf /var/lib/apt/*

# default log directory
#VOLUME ["/data/log"]

# copy spring boot jar as 'service.jar'
COPY target/movie-database-*.jar /service.jar

# service port
EXPOSE 8084

# define default jar name
ENTRYPOINT java $JVM_OPTS -jar /service.jar $ARGS