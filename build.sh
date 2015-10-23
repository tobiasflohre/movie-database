#!/usr/bin/env bash
echo "Build all services..."
mvn clean install
cd movie-database-shop-app
bower install
grunt
sudo chmod 777 /opt/moviedatabase
rm -rf /opt/moviedatabase/shop-app
cp  -R dist/. /opt/moviedatabase/shop-app
cd ..