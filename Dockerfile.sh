./mvnw clean install
docker build -f docker/app/Dockerfile --build-arg JAR_FILE=target/library-0.0.1-SNAPSHOT.jar -t jackanakin/zklibrary .
docker push jackanakin/zklibrary