package com.seonghyeon.migration.service;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MongoService {

    static final Logger logger = LoggerFactory.getLogger(MongoService.class.getName());

    @Autowired
    private MongoClient mongoClient;

    public String getTestData() {

        MongoDatabase mongoDatabase = mongoClient.getDatabase("node");

        MongoCollection<Document> collection = mongoDatabase.getCollection("book");
        FindIterable<Document> findIterable  = collection.find();
        MongoCursor<Document> cursor = findIterable.iterator();

        String result = "";

        while (cursor.hasNext()) {
            Document doc = cursor.next();
            logger.info(doc.toJson());
            result = doc.toJson();
        }

        return result;
    }
}
