**WHAT IS IT**

It's prototype of web service application for storing files.
Main 2 features of API is to store file and to get stored previously file.
But you can't directly store a file in service's storage.
You need to send meta data about this file first and after that file can be taken by this meta information.
Meta data is simple json object storing in database, like this:
```
{
    "id": "foo",
    "fullPath": "/tmp/files/foo",
    "name": "foo.txt",
    "size": 7,
    "md5Hash": "a54gv35635wefvbfvbs",
    "cacheControl": null,
    "contentDisposition": "text/plain",
    "contentEncoding": null,
    "contentLanguage": null,
    "contentType": "text/plain",
    "custom": {},
    "created": "2017-11-03 17:12:13",
    "updated": "2017-11-03 17:12:13"
}
```
Main fields here are `id` and `fullPath`. Following uploaded file will placed in `fullPath` path
and you can get it from servcie only by `id`.

**ARCHITECTURE**

Application written on java 1.8 powered by Spring Boot framework and uses all advantages of this platform like Auto Configuration, DI, JPA integration, Spring Test, etc.

Under the hood, application has 2 controllers serving HTTP requests from a client `MetaDataController` and `FilesController`.
As soon they are using to process requests only, all core business logic places in service layer in `MetaDataService` responding for
processing metadata and `FileService` responding for handling uploaded files.

Project uses embedded `H2` DB for storing meta data between requests. Communication with database happens on service layer
 over JPA repository `MetaDataRepository`. To store data in DB it represents like POJO entity `MetaData`.
But entities using on service and data layers only, for communication and transfer data projects uses DTO approach like
`MetaDataDto`. For example, in DTO you can use JSON representation and customise date time format fields and not touching
entity object.

Config file `application.properties` uses only for to pass custom parameters like root directory where uploaded files will be store
or DB credentials.

**HOW TO BUILD & RUN**

This project manage by Maven and packages into runnable jar file. For build and package, please run:
```
$ mvn clean package
```
After command will completes, you can find `files-1.0.jar` file in `target/` dir.

To run the application with custom parameters, you also need to provide properties file with custom values.
 For that copy `application.properties` file somewhere on your environment and edit there `fs.storage.dir` attribute.
Attribute `fs.storage.dir` should be absolute path to directory without trailing slash.

To start application, just run command:
```
$ java -jar  -Dspring.config.location=<dir-with-property-file>/application.properties <dir-with-jar>/files-1.0.jar
```

for ex. to run from projects's root dir:

```
$ java -jar  -Dspring.config.location=src/main/resources/application.properties ./target/files-1.0.jar
```

**HOW TO COMMUNICATE**

For communication with service and testing you can use Postman client. In `postman/` dir you can find
exported collection requests for this web service.
There are 5 requests:

* Get all MetaData - GET request to get pageable list of MetaData from DB
* Get MetaData by ID - GET request to get MetaData object by ID
* Create MetaData - POST request to save MetaData object in DB. Has predefined sample of object in Body section.
* Load file by metadata ID - POST request to upload file for MetaData object with specific ID. Body section has a form with   uploaded file. You can select file from local.
* Get file by metadata ID - GET request to get uploaded file by connected MetaData object with specific ID.

which describes all service API.

Just import in into your Postman client and you can use this requests to test your
instance of service.

All requests configured for service running on `http://localhost:8080` default Spring Boot parameters.
If you update port or will run on different host, please update requests' URL.
