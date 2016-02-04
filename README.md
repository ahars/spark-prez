# spark-prez

## Recap

- Exercise on RDD and DataFrames
- Exercice on Spark Streaming
- Exercice on Spark ML

## Requirements

- ElasticSearch
- Kibana
- Maven

## ElasticSearch for Streaming and ML

Launch ElasticSearch on localhost:9200
```
$ ./elastic/launch_elasticsearch.sh
```

Launch Kibana on localhost:5601
```
$ ./elastic/launch_kibana.sh
```

Initialize indexes on ElasticSearch
```
$ ./elastic/create_index.sh
```

## Spark

### Trees processing in RDD & DataFrames

- input : trees of Paris (open data file)
- process : "count * group by espece"
- output : console

### Tweets processing with Spark Streaming

- input : Tweets with hashtag "Android" (with Twitter4J)
- process : Language detection on the tweet
- output : indexing in ElasticSearch and visualization on Kibana

To display the Kibana dashboard, modify the content of the script
```
$ ./elastic/print_tweets_dashboard.sh
```
to use your web browser

You need to add connection id obtained from the Twitter developper API in the conf file ```spark/src/main/resources/twitter4j.properties```

This project contains a way to collect tweets in files to process them when you can't get an access to Internet..

### Titanic survivors prediction

- input : some passengers of the Titanic (files available on Kaggle)
- process : predictions about the survivors (Random Forests algorithm)
- output : indexing in ElasticSearch and visualization on Kibana

To display the Kibana dashboard, modify the content of the script
```
$ ./elastic/print_titanic_dashboard.sh
```
to use your web browser

