## Config
- Java 8
- maven 3.*

## Tools for testing api

- Postman or curl with bash

## Compile
``mvn compile``

## Run

``mvn spring-boot:run``

## Tests

#### Create apartments with address

````
curl -X POST -H "Content-Type: application/json" -d @src/main/resources/static/jdd-aparts.json http://localhost:8080/api/apartments
````

### Create bookings with 2 apartments

````
curl -X POST -H "Content-Type: application/json" -d @src/main/resources/static/jdd-bookings.json http://localhost:8080/api/bookings
````

### Search cities that have available apartments
````
curl -X GET "http://localhost:8080/api/cities?beginDate=05-05-2021&endDate=25-06-2021"

curl -X GET "http://localhost:8080/api/cities?beginDate=07-07-2021&endDate=26-07-2021"
````

### Search available apartments by city (ex : 92230 ==> Gennervilliers)

````
curl -X GET "http://localhost:8080/api/cities/92230/apartments?beginDate=05-05-2021&endDate=25-06-2021"
````

### Get all bookings list

````
curl -X GET "http://localhost:8080/api/bookings" -H "Accept: application/json"
````

### Get apartment details by reference

````
curl -X GET "http://localhost:8080/api/apartments/b1230c3d-fa3c-47cb-b071-82f06e3b68a2"
````

### Get all apartments list with bookings historic

````
curl -X GET "http://localhost:8080/api/apartments" -H "Accept: application/json" 
````