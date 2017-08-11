#!/bin/bash

HOME_BIN="/home/nnero/bin"

echo "stop service..."

service article-web.service stop
service service-eureka.service stop
service service-crawler.service stop
service service-user.service stop
service service-article.service stop
service service-zuul.service stop

echo "stop all done."
