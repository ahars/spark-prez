docker run --name elastic -p 9200:9200 -p 9300:9300 -d elasticsearch 
docker run --name kib --link elastic:elasticsearch -p 5601:5601 -d kibana
