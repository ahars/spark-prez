#!/bin/bash

curl -XDELETE 'http://localhost:9200/spark/'

curl -XPUT http://localhost:9200/spark

curl -XPOST http://localhost:9200/spark/_mapping/tweets -d '{ "tweets": { "properties": { "user": { "type": "string", "index": "not_analyzed" }, "text": { "type": "string" }, "createdAt": { "type": "date", "format": "date_time" }, "language": { "type": "string", "index": "not_analyzed" } } } }'
