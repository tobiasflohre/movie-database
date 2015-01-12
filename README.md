movie-database
==============

## Description

The movie-database is a ROCA-style web application built on Bootstrap, jQuery, Thymeleaf, Spring MVC and Spring Boot, see [Self-Contained Systems and ROCA: A complete example using Spring Boot, Thymeleaf and Bootstrap](https://blog.codecentric.de/en/2015/01/self-contained-systems-roca-complete-example-using-spring-boot-thymeleaf-bootstrap/) for concepts and pointers to details in the implementation. 
This repository has been updated Nov 2014, the old version referenced in [this blog post](http://blog.codecentric.de/en/2013/01/a-real-roca-using-bootstrap-jquery-thymeleaf-spring-hateoas-and-spring-mvc/) is still available in the branch classic.

## Build & Run
The movie-database has been updated to use Spring Boot. It also has been split up into three applications, one serving the movies resource, one serving a new actors resource and one serving the navigation header. In addition to that security (SSO) was added, and the infrastructure setup to support this involves Redis and nginx. If you already have those two installed, good, and if not, it's a 15 minute thing (at least on Mac). If you shy away from that you're still able to run a non-integrated movie or actor application, I'll get to that later. The whole setup also includes an instance of [Spring Boot Admin](https://github.com/codecentric/spring-boot-admin), a complete tool for monitoring Spring Boot applications. You can do and see everything you need in there, like health status, log files, thread dumps, environment variables, a JMX console and much more.

You need to have [Homebrew](http://brew.sh/) installed to do the following.

#### nginx
Follow [these](https://gist.github.com/netpoetica/5879685) instructions to install nginx. When editing the /private/etc/hosts file, add the line

    127.0.0.1 moviedatabase.com
Now copy [moviedatabase.conf](https://github.com/tobiasflohre/movie-database/blob/master/moviedatabase.conf) to /usr/local/etc/nginx/conf.d/. Start nginx with `sudo nginx`, stop it with `sudo nginx -s stop`.

#### Redis
One fast way to get Redis running is to use a Docker image. If you're on Mac or Windows, you'll need to install [Boot2Docker](http://boot2docker.io/) first and start it. My colleague Ben Ripkens wrote a [nice tool](https://github.com/bripkens/dock) to install standard Docker images fast. To install it, do the following

    brew tap bripkens/dock
    brew install dock
To install and startup Redis:

    dock redis
Now Redis is running under 192.168.59.103:6379, which is exactly what the movie database expects. If you already have a Redis installed somewhere else, take a look at the `application.properties` files contained in the projects, there you may change the host and port used for connecting to Redis (for changing the port you need to add the property `spring.redis.port`, for a list of all Spring Boot properties take a look [here](http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#common-application-properties)).

#### Running the applications

Now clone this repository and check out master. From command line you may just call `./buildAndStartup.sh` to build and startup all applications. You need to have `mvn` and `java` on your path for that. If you're not on OSX it should be easy to adapt the script to your OS. I piped the logs to `dev/null` because the applications themselves are logging to `/tmp`.

Alternatively, in your IDE import all Maven projects, then run the class `NavigationApplication` in the project movie-database-navigation, the class `ActorsApplication` in the project movie-database-actors, the class `MoviesApplication` in the project movie-database-movies and the class `MonitoringApplication`in the project movie-database-monitoring. 

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
