#!/bin/bash

if [ $# -ne 1 ]; then
        echo wrong number of parameters \(need one\)
        exit 0
fi

curl -XPUT http://$1:9200/spark

curl -XPOST http://$1:9200/spark/_mapping/tweets -d '{ "tweets": { "properties": { "user": { "type": "string", "index": "not_analyzed" }, "text": { "type": "string" }, "createdAt": { "type": "date", "format": "date_time" }, "language": { "type": "string", "index": "not_analyzed" } } } }'
