FROM node:6.5.0
ENV APP_DIR '/opt/morepets/'
RUN mkdir -p $APP_DIR
COPY db.json $APP_DIR
COPY package.json $APP_DIR

WORKDIR $APP_DIR
RUN npm install
EXPOSE 3000
CMD [ "npm", "start" ]
