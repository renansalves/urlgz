FROM mariadb:10.11

ENV MARIADB_ROOT_PASSWORD="root"
ENV MARIADB_DATABASE="urlgz"
ENV MARIADB_USER="user"
ENV MARIADB_PASSWORD="root"

COPY ./src/main/resources/init.sql /docker-entrypoint-initdb.d/init.sql

EXPOSE 3306
