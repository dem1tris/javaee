FROM postgres

ENV POSTGRES_DB main
ENV POSTGRES_USER root
ENV POSTGRES_PASSWORD 12341234

EXPOSE 5432

ADD init-postgres-earthdistance.sh /docker-entrypoint-initdb.d/