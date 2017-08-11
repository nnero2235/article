#!/bin/bash

HOME_BIN="/home/nnero/bin"

echo "start service..."

service article_web.service start
service service_eureka.service start
service service_crawler.service start
service service_user.service start
service service_article.service start
service service_zuul.service start

echo "start done."
