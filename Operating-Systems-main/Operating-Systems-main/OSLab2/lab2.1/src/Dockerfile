FROM debian:11-slim
RUN apt-get update && apt-get install -y nginx

VOLUME ["C:\Users\user\IdeaProjects\lab2.1\src" ]
COPY ./src C:/Users/user/IdeaProjects/lab2.1/src/

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]