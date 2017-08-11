#!/bin/bash

HOME_BIN="/home/nnero/bin"

echo "start service..."

service article-web.service start
service service-eureka.service start
service service-crawler.service start
service service-user.service start
service service-article.service start
service service-zuul.service start

echo "start done."
