# SERVLET AS REST RESOURCES

This code is for testing out the servlet as rest api running without any additional dependencies
This intergrated with mysql db
with UI as angularjs/bootstrap

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install the software and how to install them

```
you will need tomcat server; the one I tested is with apache-tomcat-7.0.75
you will need mysql server with root / root and database as servebasic

```

### Installing

A step by step series of examples that tell you have to get a development env running

Say what the step will be

```
================DATABASE (mysql) ====================

create database servebasic;

use databse servebasic;

CREATE TABLE `USERS` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(30) DEFAULT NULL,
  `lastname` varchar(20) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `active` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 ;


==================DATASOURCE configurations=====================
<Resource name="jdbc/servebasic" 
		  global="jdbc/servebasic" 
		  auth="Container" 
		  type="javax.sql.DataSource" 
		  driverClassName="com.mysql.jdbc.Driver" 
		  url="jdbc:mysql://localhost:3306/servebasic" 
		  username="root" 
		  password="root" 
		  
		  maxActive="100" 
		  maxIdle="20" 
		  minIdle="5" 
		  maxWait="10000"/>

=======================WAR=====================================
Angularjs 1.4.5 / bootstrap / jsp + servlet 

in meta-inf folder context.xml have entry for datasource....

====================Tomcat configuration=========================
in the lib dir apache-tomcat-7.0.75\lib put the driver file as mysql-connector-java-5.1.43-bin.jar
you can download this from  https://dev.mysql.com/downloads/connector/j/5.1.html

```

End with an example of getting some data out of the system or using it for a little demo

## Running the tests

You can run any rest client and try to use the url for api
Get the list of all users   -   http://localhost:8080/servebasic/api?query=getallusers
Get particular user  - http://localhost:8080/servebasic/api?id=9
New Sample for netflow has been added...
## Deployment

Add additional notes about how to deploy this on a live system

## Built With

* [Eclipse]- The web framework used


## Contributing


## Versioning


## Authors

* **Yogesh dakre** - *Initial work* - [Yogyaideas](http://slcdaffodils.github.io/daffo)



## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* FUN at the work
* Inspiration
* etc
