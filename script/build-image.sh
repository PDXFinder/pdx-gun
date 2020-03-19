# build, test and package artifact
mvn -f ../pom.xml  clean install

# generate database seed
java -jar pdx-gun.jar

# stop running dontainers
docker stop $(docker ps -a -q)

# remove stopped containers
docker rm $(docker ps -a -q)

# remove database docker image
docker rmi $(docker images |grep 'abayomiebi/pdx-gun')

# remove hasura docker image
docker rmi $(docker images |grep 'hasura/graphql-engine')

# build docker image
docker build -f ../Dockerfile -t abayomiebi/pdx-gun .

# push to docker hub
docker push abayomiebi/postgres

