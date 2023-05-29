FROM frolvlad/alpine-oraclejre8
MAINTAINER jiangzhuohang
WORKDIR /work
RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories && \
apk update && apk add libssl1.0 libx11 libxext libxrender libstdc++ freetype fontconfig
COPY face-search-core/src/main/resources/model /work/model
COPY meeting_controller/target/meeting_backend.jar /work/jar/
RUN apk update --no-cache && apk add --no-cache tzdata
ENV TZ Asia/Shanghai
EXPOSE 8866
ENTRYPOINT ["java","-server","-XX:MaxDirectMemorySize=400M","-jar","-Dspring.profiles.active=docker","jar/meeting_backend.jar"]
