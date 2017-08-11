#!/bin/bash

HOME_BIN="/home/nnero/bin"

echo "stop service..."

service article_web.service stop
service service_eureka.service stop
service service_crawler.service stop
service service_user.service stop
service service_article.service stop
service service_zuul.service stop

echo "stop all done."
