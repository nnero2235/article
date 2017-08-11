#!/bin/bash
echo "init mysql"

HOST_NAME="localhost"
PORT="3306"
USER_NAME="root"
PASSWORD="nnero"

DB_NAME="article"
TABLE_ARTICLE_SUMMARY="article_summary"
TABLE_ARTICLE_DETAIL="article_detail"
TABEL_USER="user"
TABLE_ARTICLE_LIKES="article_likes"
TABLE_ARTICLE_COMMENTS="article_comments"

CREATE_DATABASE="create database if not exists ${DB_NAME};"

CREATE_TABLE_AS="create table if not exists ${TABLE_ARTICLE_SUMMARY} (
    as_id int not null primary key auto_increment,
    origin_id char(12) not null,
    title varchar(30) not null,
    summary varchar(50) not null,
    author varchar(20),
    write_time datetime,
    last_modify_time datetime not null,
    category varchar(20) default 'all');"

CREATE_TABLE_AD="create table if not exists ${TABLE_ARTICLE_DETAIL} (
    ad_id int not null primary key auto_increment,
    origin_id char(12) not null,
    content text not null);"

CREATE_TABLE_USER="create table if not exists ${TABEL_USER} (
    id int not null primary key auto_increment,
    u_id char(16) not null unique,
    nickname varchar(10) not null unique,
    pwd char(16) not null,
    tel char(11) not null unique,
    sex char(1) default 'n',
    age int default 0,
    create_time datetime not null,
    last_modify_time datetime not null)"

CREATE_TABLE_AL="create table if not exists ${TABLE_ARTICLE_LIKES} (
    al_id int not null primary key auto_increment,
    origin_id char(12) not null,
    u_id char(16) not null,
    like_time datetime not null)"

CREATE_TABLE_AC="create table if not exists ${TABLE_ARTICLE_COMMENTS} (
    ac_id int not null primary key auto_increment,
    origin_id char(12) not null,
    u_id char(16) not null,
    comment varchar(50) not null,
    comment_time datetime not null)"

mysql -h${HOST_NAME}  -P${PORT}  -u${USER_NAME} -p${PASSWORD} -e "${CREATE_DATABASE}"
mysql -h${HOST_NAME}  -P${PORT}  -u${USER_NAME} -p${PASSWORD} -e "${CREATE_TABLE_AS}"
mysql -h${HOST_NAME}  -P${PORT}  -u${USER_NAME} -p${PASSWORD} -e "${CREATE_TABLE_AD}"
mysql -h${HOST_NAME}  -P${PORT}  -u${USER_NAME} -p${PASSWORD} -e "${CREATE_TABLE_USER}"
mysql -h${HOST_NAME}  -P${PORT}  -u${USER_NAME} -p${PASSWORD} -e "${CREATE_TABLE_AL}"
mysql -h${HOST_NAME}  -P${PORT}  -u${USER_NAME} -p${PASSWORD} -e "${CREATE_TABLE_AC}"

echo "MySQL init done!"
