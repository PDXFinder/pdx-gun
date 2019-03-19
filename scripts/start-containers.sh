# stop running dontainers
docker stop $(docker ps -a -q)

# remove stopped containers
docker rm $(docker ps -a -q)

# remove database docker image
docker rmi $(docker images |grep 'abayomiebi/postgres')

# remove hasura docker image
docker rmi $(docker images |grep 'hasura/graphql-engine')

# start all containers
docker-compose up -d

