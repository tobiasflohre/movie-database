#!/usr/bin/env bash

pkill -f redis-server
sudo nginx -s stop
pkill -f movie-database

MOVIE_DATABASE_HOME=/home/vagrant/share

apt-get -y update
apt-get -y install \
    unzip \
    redis-server \
    nginx \
    openjdk-7-jdk \
    maven \
    npm

# /usr/bin/node is required by bower
if [ ! -e /usr/bin/node ]; then
    ln -s /usr/bin/{nodejs,node}
fi

cd $MOVIE_DATABASE_HOME

# fix redis host
#find . -type f -name application.properties -exec sed -i 's/192.168.59.103/127.0.0.1/g' {} \;''

mvn clean install

npm install -g grunt-cli
npm install grunt
npm install -g bower

cd $MOVIE_DATABASE_HOME/movie-database-shop-app

npm install
bower --allow-root install
grunt
cp  -R dist/. /opt/moviedatabase/shop-app

cd $MOVIE_DATABASE_HOME

cp $MOVIE_DATABASE_HOME/moviedatabase.conf /etc/nginx/conf.d/
service nginx restart
redis-server 2>&1 &
java -jar movie-database-monitoring/target/movie-database-monitoring-0.0.1-SNAPSHOT.jar > /tmp/movie-database-monitoring.log 2>&1 &
java -jar movie-database-navigation/target/movie-database-navigation-0.0.1-SNAPSHOT.jar > /tmp/movie-database-navigation.log 2>&1 &
java -jar movie-database-movies/target/movie-database-movies-0.0.1-SNAPSHOT.jar > /tmp/movie-database-movies.log 2>&1 &
java -jar movie-database-actors/target/movie-database-actors-0.0.1-SNAPSHOT.jar > /tmp/movie-database-actors.log 2>&1 &
java -jar movie-database-shop-rest/target/movie-database-shop-rest-0.0.1-SNAPSHOT.jar > /tmp/movie-database-shop-rest.log 2>&1 &
