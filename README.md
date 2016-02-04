# spark-prez

## Recap

1) Exercise on RDD and DataFrames
2) Exercice on Spark Streaming
3) Exercice on Spark ML

## Requirements

- ElasticSearch
- Kibana
- Maven

## ElasticSearch for Streaming and ML

Launch ElasticSearch & Kibana :
```
$ ./elastic/launch_elasticsearch.sh
```
```
$ ./elastic/launch_kibana.sh
```

Initialize indexes on ElasticSearch :
```
$ ./elastic/create_index.sh
```

## Spark

### Trees processing in RDD & DataFrames

- input : trees of Paris (.csv; open data)
- process : count * group by espece
- output : console

### Tweets processing with Spark Streaming

- input : Tweets with hashtag "Android" with Twitter4J
- process : Language detection on the tweet
- output : indexing in ElasticSearch and visualization on Kibana

To display the Kibana dashboard, modify the content of the script
```
$ ./elastic/print_tweets_dashboard.sh
```
to use your web browser.

This project contains a way to collect tweets in files to process them when you can't get an access to Internet..

###

- input : some passengers of the Titanic
- process : predictions about the survivors
- output : indexing in ElasticSearch and visualization on Kibana

To display the Kibana dashboard, modify the content of the script
```
$ ./elastic/print_titanic_dashboard.sh
```
to use your web browser.

