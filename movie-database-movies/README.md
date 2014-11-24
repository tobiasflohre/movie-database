movie-database
==============

## Description

The movie-database is a ROCA-style web application built on Bootstrap, jQuery, Thymeleaf, Spring HATEOAS and Spring MVC.
For more information take a look at [this blog post](http://blog.codecentric.de/en/2013/01/a-real-roca-using-bootstrap-jquery-thymeleaf-spring-hateoas-and-spring-mvc/).

## Build & Run

From the command line do:

    git clone https://github.com/tobiasflohre/movie-database.git
    cd movie-database
    mvn jetty:run

Access [http://localhost:8080/movies](http://localhost:8080/movies) in your browser.

## Acceptances Tests with Geb

The Geb/Spock/cucumber-jvm acceptance tests for the movie-database have been moved to a separate repository: [https://github.com/mlex/geb-demo](https://github.com/mlex/geb-demo).