sleep 3
echo "============ START PUSHING IMAGES TO DOCKER HUB ============"
docker login -u "$DOCKER_USERNAME" -p "$DOCKER_PASSWORD" docker.io
docker push pelayochristian/eurekaserver:$BUILD_NAME
docker push pelayochristian/configserver:$BUILD_NAME
docker push pelayochristian/gatewayserver:$BUILD_NAME
docker push pelayochristian/newsservice:$BUILD_NAME
echo "============ DONE PUSHING IMAGES TO DOCKER HUB ============"
