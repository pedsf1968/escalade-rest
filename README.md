# escalade-REST
API REST du projet de site d'escalade

[![Build Status](https://travis-ci.org/pedsf1968/escalade-rest.svg?branch=master)](https://travis-ci.org/pedsf1968/escalade-rest)
![GitHub](https://img.shields.io/github/license/pedsf1968/escalade-rest)


# Object :
Rest controller Api for Escalade project. It's an add-on of escalade-api project. After identification, the user can send http requests.
Only Escalade users can be identified.   

#Build with docker
- Use the command below to build the docker image :

docker build -t  escalade-rest .

#Run container with docker
docker run -d -rm -p 9191:9191 -e jasypt.encryptor.password=$JASYPT_ENCRYPTOR_SECRET escalade-rest:latest

- bind application on port 8888
- use your $JASYPT_ENCRYPTOR_SECRET envyronment property to decrypt sensible datas.

#Uses
##Identification
- Send POST request to http://localhost:9191/authenticate with the body :
{
    "login": "email",
    "password":"password"
} 

If you are authorized the API send a Bearer Token the the others requests.

##Requests
##GET requests
- /site
- /site/full
- /topo
- /topo/full
- /sector
- /sector/full
- /voie
- /voie/full
- /longueur
- /longueur/full

##POST requests
- /sector
- /sector/full
- /voie
- /voie/full
- /longueur
- /longueur/full
