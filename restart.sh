#!/bin/bash

HOME_BIN="/home/nnero/bin"

echo "restart service..."

service article-web.service restart
service service-eureka.service restart
service service-crawler.service restart
service service-user.service restart
service service-article.service restart
service service-zuul.service restart

echo "restart done."
