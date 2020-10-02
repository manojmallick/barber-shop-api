FROM adoptopenjdk/openjdk8

WORKDIR /home/docker/app/
#COPY docker-entrypoint.sh /usr/local/bin/docker-entrypoint.sh
#RUN chmod +x /usr/local/bin/docker-entrypoint.sh
COPY ./target/barber-shop-api-*.jar barber-shop-api.jar

#ENTRYPOINT ["docker-entrypoint.sh"]

CMD ["java","-jar","barber-shop-api.jar"]
EXPOSE 8088
