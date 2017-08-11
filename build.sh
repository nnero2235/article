#!/bin/bash

echo "update code..."
git pull
echo "update code done."

echo "building web"
cd article_app
gradle clean
gradle build -x test
cd ..
echo "building web done."

echo "building article"
cd service_article
gradle clean
gradle build -x test
cd ..
echo "building article done."

echo "building crawler"
cd service_crawler
gradle clean
gradle build -x test
cd ..
echo "building crawler done."

echo "building eureka"
cd service_eureka
gradle clean
gradle build -x test
cd ..
echo "building eureka done."

echo "building user"
cd service_user
gradle clean
gradle build -x test
cd ..
echo "building user done."

echo "building zuul"
cd service_zuul
gradle clean
gradle build -x test
cd ..
echo "building zuul done."
