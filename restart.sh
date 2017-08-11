#!/bin/bash

HOME_BIN="/home/nnero/bin"

echo "restart service..."

service article_web.service restart
service service_eureka.service restart
service service_crawler.service restart
service service_user.service restart
service service_article.service restart
service service_zuul.service restart

echo "restart done."
