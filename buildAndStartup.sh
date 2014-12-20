mvn clean install
java -jar movie-database-monitoring/target/movie-database-monitoring-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &
java -jar movie-database-navigation/target/movie-database-navigation-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &
java -jar movie-database-movies/target/movie-database-movies-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &
java -jar movie-database-actors/target/movie-database-actors-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &
