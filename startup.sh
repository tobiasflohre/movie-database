#!/usr/bin/env bash
echo "Stopping all services..."
sudo nginx -s stop
pkill -f movie-database
pkill -f redis-server
echo "Starting HTTP Server..."
sudo nginx
echo "Starting Redis Server..."
redis-server 2>&1 &
echo "Starting Java Apps..."
java -jar movie-database-monitoring/target/movie-database-monitoring-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &
java -jar movie-database-navigation/target/movie-database-navigation-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &
java -jar movie-database-movies/target/movie-database-movies-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &
java -jar movie-database-actors/target/movie-database-actors-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &
java -jar movie-database-shop-rest/target/movie-database-shop-rest-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &
