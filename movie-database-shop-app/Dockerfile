FROM nginx:1.7.9
MAINTAINER Dennis Schulte <mail@dennis-schulte.de>

RUN rm /etc/nginx/conf.d/*

COPY moviedatabase.conf /etc/nginx/conf.d/moviedatabase.conf
COPY dist/. /usr/share/nginx/html/shop-app/

COPY run.sh /usr/local/bin/

RUN chmod 755 /usr/local/bin/run.sh

# service port
EXPOSE 80

ENTRYPOINT ["/usr/local/bin/run.sh"]