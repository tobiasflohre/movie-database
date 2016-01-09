movie-database
==============

## Description

The movie-database is a ROCA-style web application built on Bootstrap, jQuery, Thymeleaf, Spring MVC and Spring Boot, see [Self-Contained Systems and ROCA: A complete example using Spring Boot, Thymeleaf and Bootstrap](https://blog.codecentric.de/en/2015/01/self-contained-systems-roca-complete-example-using-spring-boot-thymeleaf-bootstrap/) for concepts and pointers to details in the implementation.
This repository has been updated Nov 2014, the old version referenced in [this blog post](http://blog.codecentric.de/en/2013/01/a-real-roca-using-bootstrap-jquery-thymeleaf-spring-hateoas-and-spring-mvc/) is still available in the branch [classic](https://github.com/tobiasflohre/movie-database/tree/classic).

## Build & Run
The movie-database is a system of self-contained systems (take a look at the blog posts referenced above). There are three self-contained systems, one for movies, one for actors and one for a shop. The movies and the actors system are ROCA-style applications with server-side rendering, while the shop system is a SPA with AngularJS on the client side and a REST service on the backend side. In addition to these three systems we have two applications serving cross-cutting concerns: one for delivering navigation snippets and one for monitoring.
An SSO mechanism is used for security, the infrastructure setup to support this involves Redis and nginx. If you already have those two installed, good, and if not, it's a 15 minute thing (at least on Mac). If you shy away from that you're still able to run a non-integrated movie or actor application, I'll get to that later. The whole setup also includes an instance of [Spring Boot Admin](https://github.com/codecentric/spring-boot-admin), a complete tool for monitoring Spring Boot applications. You can do and see everything you need in there, like health status, log files, thread dumps, environment variables, a JMX console and much more.

### Build & Run with Vagrant
Clone this repository, jump into the subdirectory movie-database-vagrant and do

    vagrant up

That will take a while (and it will change some of the application.properties files, don't be confused about that). Edit your /private/etc/hosts file and add the line

    192.168.56.13	moviedatabase.com

When everything is started, access [http://moviedatabase.com](http://moviedatabase.com) in your browser for the application. Currently there are two users, admin/admin and user/user. For monitoring with Spring Boot Admin access [http://${docker-machine ip default}:8083](http://${docker-machine ip default}:8083).

This repo that you cloned is shared with the virtual machine. So you can change things in your IDE, log into the virtual machine and rebuild / restart changed services there.

### Build & Run with Docker

For the installation of Docker and Docker Compose please refer to [https://docs.docker.com/installation] and [https://docs.docker.com/compose/install].

Edit your /private/etc/hosts file and add the lines (on the Mac please verify the IP with "docker-machine ip default")

    192.168.99.100 moviedatabase.com

Clone this repository and do

    ./build.sh
    docker-compose --x-networking up

Alternatively you can start all the containers manually and attach them to the network "moviedatabase"

  docker network create moviedatabase

  docker build -t monitoring movie-database-monitoring
  docker build -t movies movie-database-movies
  docker build -t actors movie-database-actors
  docker build -t navigation movie-database-navigation
  docker build -t shop movie-database-shop
  docker build -t shopgui movie-database-shop-app
  
  docker run -it --net=moviedatabase --name redis redis
  docker run -p 8083:8083 -it --net=moviedatabase --name moviedatabase_monitoring_1 monitoring
  docker run -p 8080:8080 -it --net=moviedatabase --name moviedatabase_movies_1 movies
  docker run -p 8083:8083 -it --net=moviedatabase --name moviedatabase_actors_1 actors
  docker run -p 8081:8081 -it --net=moviedatabase --name moviedatabase_navigation_1 navigation
  docker run -p 8084:8084 -it --net=moviedatabase --name moviedatabase_shop_1 shop
  docker run -p 80:80 -it --net=moviedatabase --name moviedatabase_shopgui_1 shopgui

When everything is started, access [http://moviedatabase.com](http://moviedatabase.com) in your browser for the application. Currently there are two users, admin/admin and user/user. For monitoring with Spring Boot Admin access [http://moviedatabase.com:8083](http://moviedatabase.com:8083).

### Build & Run without virtualization

You need to have [Homebrew](http://brew.sh/) installed to do the following.

#### nginx
Follow [these](https://gist.github.com/netpoetica/5879685) instructions to install nginx. When editing the /private/etc/hosts file, add the lines

    127.0.0.1 moviedatabase.com
    127.0.0.1 redis
    127.0.0.1 moviedatabase_monitoring_1
    127.0.0.1 moviedatabase_navigation_1
    127.0.0.1 moviedatabase_actors_1
    127.0.0.1 moviedatabase_movies_1
    127.0.0.1 moviedatabase_shop_1

Now copy [moviedatabase.conf](https://github.com/tobiasflohre/movie-database/blob/master/moviedatabase.conf) to /usr/local/etc/nginx/conf.d/. Start nginx with `sudo nginx`, stop it with `sudo nginx -s stop`.

#### Redis

##### Linux

Redis is available in the repositories of most mainstream distros. Installing it is usually a matter of running something along the lines of (for Ubuntu):

    sudo apt-get install redis

After the command finishes you can (again on Ubuntu) run:

    redis-cli ping

Redis should now reply

    PONG

##### Mac

You can install Redis with Homebrew

    brew install redis

And start it with

    redis-server

##### Alternative way for Mac and Windows

Another fast way to get Redis running is to use a Docker image. If you're on Mac or Windows, you'll need to install [Boot2Docker](http://boot2docker.io/) first and start it. My colleague Ben Ripkens wrote a [nice tool](https://github.com/bripkens/dock) to install standard Docker images fast. To install it, do the following

    brew tap bripkens/dock
    brew install dock

To install and startup Redis:

    dock redis

Now Redis is running under 192.168.59.103:6379, which is exactly what the movie database expects. If you already have a Redis installed somewhere else, take a look at the `application.properties` files contained in the projects, there you may change the host and port used for connecting to Redis (for changing the port you need to add the property `spring.redis.port`, for a list of all Spring Boot properties take a look [here](http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#common-application-properties)).

#### Running the applications

Now clone this repository and check out master. From command line you may just call `./build.sh` and then `./startup.sh`. The first script builds all the applactions and the second script stops all services and then starts all applications, starts Redis, starts nginx, copies the static Angular app to a specific place and then starts all movie-database applications. You need to have `mvn` and `java` and `grunt` and `bower` on your path for that (and maybe some other stuff, better take a look at the script). If you're not on OSX it should be easy to adapt the script to your OS. I piped the logs to `dev/null` because the applications themselves are logging to `/tmp`.

Alternatively start nginx and Redis yourself, build the Angular app yourself and copy it to the folder accessed by nginx, and then in your IDE import all Maven projects, then run the class `ShopApplication` in the project movie-database-shop-rest, the class `NavigationApplication` in the project movie-database-navigation, the class `ActorsApplication` in the project movie-database-actors, the class `MoviesApplication` in the project movie-database-movies and the class `MonitoringApplication`in the project movie-database-monitoring.

Then access [http://moviedatabase.com](http://moviedatabase.com) in your browser for the application. Currently there are two users, admin/admin and user/user. For monitoring with Spring Boot Admin access [http://localhost:8083](http://localhost:8083).

### Build & Run without security

If you really shy away from installing nginx and Redis, you may just start the applications without security. Remove the dependency to movie-database-security from movie-database-movies and movie-database-actors, then remove the reference to `SecurityConfiguration` from those two projects (making them compile-clean again). Now start `NavigationApplication` and `MoviesApplication` (or `ActorsApplication`) and browse to [http://localhost:8080/movie-app](http://localhost:8080/movie-app) ([http://localhost:8082/actor-app](http://localhost:8082/actor-app)). Navigation links won't work, search works.

## Build & Run (classic branch)

From the command line do:

    git clone https://github.com/tobiasflohre/movie-database.git
    git checkout classic
    cd movie-database
    mvn jetty:run

Access [http://localhost:8080/movies](http://localhost:8080/movies) in your browser.
