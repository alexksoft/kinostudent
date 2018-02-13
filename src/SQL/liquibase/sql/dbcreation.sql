CREATE TABLE user_app
(
   id INT unsigned not null auto_increment primary key,
   name varchar(100),
   login varchar(100) NOT NULL,
   password varchar(100) NOT NULL,
   sha2_password varchar(100) NOT NULL,
   phone varchar(100),
   role varchar(20),
   remember_me_token longtext,
   is_deleted bit DEFAULT 0 NOT NULL,
   surname varchar(100),
   position varchar(100),
   is_administrator bit DEFAULT b'0' NOT NULL
   
) CHARACTER SET utf8 COLLATE utf8_general_ci;
