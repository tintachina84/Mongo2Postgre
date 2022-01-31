package com.seonghyeon.migration.service;

import com.seonghyeon.migration.model.Book;
import com.seonghyeon.migration.repository.BookRepository;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ExecuteService extends Mongo2Postgre {

    @Autowired
    private MongoClient mongoClient;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void execMigration() {
        MongoDatabase db = this.mongoClient.getDatabase("node");

        MongoCollection<Document> coll = db.getCollection("book");
        int num = this.migrate(coll, this.bookRepository, Book.class);
        log.info("inserted : " + num);
    }
}
