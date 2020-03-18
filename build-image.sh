# build, test and package artifact
mvn clean install

# generate database seed
java -jar data-gun.jar

# stop running dontainers
docker stop $(docker ps -a -q)

# remove stopped containers
docker rm $(docker ps -a -q)

# remove database docker image
docker rmi $(docker images |grep 'abayomiebi/postgres')

# remove hasura docker image
docker rmi $(docker images |grep 'hasura/graphql-engine')

# build docker image
docker build -t abayomiebi/postgres .

# push to docker hub
docker push abayomiebi/postgres

