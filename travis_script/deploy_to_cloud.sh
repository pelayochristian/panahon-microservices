#!/bin/bash
echo ">>>>>>>>>>>>>>>>>>>>>>>>>> Start Stopping container..."
total_counter=5
stop_status=1
counter=1
# Check stop_status in removing container
while [ $stop_status -ne 0 ]; do

  echo "Stop counter: $counter/$total_counter..."

  # If the stop counter is 5 continue execution
  if [ $counter -eq $total_counter ]; then
    break
  fi
  sleep 5

  # Docker stop container
  docker-compose stop
  stop_status=$?
  counter=$(expr $counter + 1)
done
echo ">>>>>>>>>>>>>>>>>>>>>>>>>> Done Stopping conatainer..."
echo
echo
echo ">>>>>>>>>>>>>>>>>>>>>>>>>> Start Removing conatainer..."
remove_status=1
counter=1
# Check remove_status in removing container
while [ $remove_status -ne 0 ]; do
  echo "Removing counter: $counter/$total_counter..."

  # If the remove counter is 5 continue execution
  if [ $counter -eq $total_counter ]; then
    break
  fi
  sleep 5

  # Docker remove container
  docker-compose rm -f
  remove_status=$?
  counter=$(expr $counter + 1)
done
echo ">>>>>>>>>>>>>>>>>>>>>>>>>> Start Removing conatainer..."
echo
echo
docker-compose pull
docker-compose -f docker/docker-compose.yml up -d
echo "DONE Deployment."
