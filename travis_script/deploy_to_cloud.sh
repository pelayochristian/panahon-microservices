sleep 3
echo "============ START CLOUD-DEPLOYMENT ============"
sshpass -p "$SERVER_PASS" ssh $USER:$PORT

echo "Locating the $PROJECT_NAME directory..."
cd application/panahon

echo "============================================="
echo "Cleaning up docker..."
docker stop $(docker ps -a)
docker rm $(docker ps -a)
echo "Removing images...."
docker rmi pelayochristian/eurekaserver:$BUILD_NAME
docker rmi pelayochristian/configserver:$BUILD_NAME
docker rmi pelayochristian/gatewayserver:$BUILD_NAME
docker rmi pelayochristian/newsservice:$BUILD_NAME
echo "Done Removing images"
echo "============================================="

echo "Update $PROJECT_NAME repository..."
git pull origin master
echo "Done updating."
docker-compose up
echo "============ DONE CLOUD-DEPLOYMENT ============"

