## Task 1
```bash
$ gradlew.bat run --args="1 OSM_FILE_PATH"
```

## Run DB
```bash
$ docker build --tag ivanishkin-javaee -f Dockerfile init-db
$ docker run -d -p 5432:5432 ivanishkin-javaee
```

## Task 2
```bash
$ gradlew.bat run --args="2.1 OSM_FILE_PATH"
$ gradlew.bat run --args="2.2 OSM_FILE_PATH"
```

## Task 3
```bash
$ gradlew.bat run -b "javaee-spring/build.gradle"
$ curl "http://localhost:8080/node/radius?lon=82.2038228&lat=54.4618596&radius=10000"
```