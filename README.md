movie-database
==============

## Description

The movie-database is a ROCA-style web application built on Bootstrap, jQuery, Thymeleaf, Spring MVC and Spring Boot. This repository has been updated Nov 2014, the old version referenced in [this blog post](http://blog.codecentric.de/en/2013/01/a-real-roca-using-bootstrap-jquery-thymeleaf-spring-hateoas-and-spring-mvc/) is still available in the branch classic.

## Build & Run
The movie-database has been updated to use Spring Boot. It also has been split up into three applications, one serving the movies resource, one serving a new actors resource and one serving the navigation header. To run the application in your IDE check out and import all Maven projects, then run the class NavigationApplication in the project movie-database-navigation, the class ActorsApplication in the project movie-database-actors and the class MoviesApplication in the project movie-database-movies. Then access [http://localhost:8080/movies](http://localhost:8080/movies) in your browser.

## Build & Run (classic branch)

From the command line do:

    git clone https://github.com/tobiasflohre/movie-database.git
	git checkout classic
    cd movie-database
    mvn jetty:run

Access [http://localhost:8080/movies](http://localhost:8080/movies) in your browser.
