FROM nginx:1.22-alpine

MAINTAINER github/jackanakin

ARG CONF_FILE

COPY ${CONF_FILE} /etc/nginx/conf.d/default.conf

EXPOSE 80/tcp

ENTRYPOINT ["nginx", "-g", "daemon off;"]