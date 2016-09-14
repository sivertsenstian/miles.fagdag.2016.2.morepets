FROM nginx:1.11.3-alpine
COPY /resources/public/ /usr/share/nginx/html/morepets
