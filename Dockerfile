FROM frolvlad/alpine-oraclejre8
MAINTAINER lyc
RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories && \
apk update && apk add libssl1.0 libx11 libxext libxrender libstdc++ freetype fontconfig
ADD jar/SmartAgriculture.jar SmartAgriculture.jar
EXPOSE 8066
RUN apk update --no-cache && apk add --no-cache tzdata
ENV TZ Asia/Shanghai
ENTRYPOINT ["java","-Xms1024M","-Xmx1024M","-jar","SmartAgriculture.jar"]