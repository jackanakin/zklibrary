./mvnw clean install
docker build -f docker/app/Dockerfile --build-arg JAR_FILE=target/library-0.0.1-SNAPSHOT.jar -t jackanakin/zklibrary .
docker push jackanakin/zklibrary

docker build -f docker/proxy/Dockerfile --build-arg CONF_FILE=docker/proxy/default.conf -t jackanakin/zklibrary_nginx .
docker push jackanakin/zklibrary_nginx
