#!/bin/bash

HOME_BIN="/home/nnero/bin"

echo "copying jars to deploy dir..."

cp article_app/libs/build-article-app-1.0.jar ${HOME_BIN}
cp service_article/libs/build-service-article-1.0.jar ${HOME_BIN}
cp service_crawler/libs/build-service-crawler-1.0.jar ${HOME_BIN}
cp service_user/libs/build-service-user-1.0.jar ${HOME_BIN}
cp service_zuul/libs/build-service-zuul-1.0.jar ${HOME_BIN}
cp service_eureka/libs/build-service-eureka-1.0.jar ${HOME_BIN}

echo "copy done."
