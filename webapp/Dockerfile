FROM node:lts-stretch-slim AS build-env
RUN mkdir /app
WORKDIR /app
COPY package.json /app/
RUN npm install
COPY src/ /app/src/
COPY public/ /app/public/
RUN npm run-script build


FROM nginx:1.15.2-alpine
COPY --from=build-env /app/build /var/www
COPY nginx.conf /etc/nginx/nginx.conf
EXPOSE 3000
