boot2docker stop
sudo nginx -s stop
pkill -f movie-database
mvn clean install
cd movie-database-shop-app
bower install
grunt
rm -rf /opt/moviedatabase/shop-app
cp  -R dist/. /opt/moviedatabase/shop-app
cd ..
sudo nginx
boot2docker init
boot2docker start
eval "$(boot2docker shellinit)"
dock redis
java -jar movie-database-monitoring/target/movie-database-monitoring-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &
java -jar movie-database-navigation/target/movie-database-navigation-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &
java -jar movie-database-movies/target/movie-database-movies-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &
java -jar movie-database-actors/target/movie-database-actors-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &
java -jar movie-database-shop-rest/target/movie-database-shop-rest-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &
