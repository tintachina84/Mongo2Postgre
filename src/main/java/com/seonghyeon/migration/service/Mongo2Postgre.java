package com.seonghyeon.migration.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Id;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Slf4j
public abstract class Mongo2Postgre {

    @Autowired
    @Qualifier("migrateObjectMapper")
    private ObjectMapper mapper;

    @Transactional
    public abstract void execMigration();

    public <T, ID> int migrate(final MongoCollection<Document> coll, final CrudRepository<T, ID> repo, final Class<T> clazz) {
        int num = 0;

        try {
            repo.deleteAll();
            FindIterable<Document> iterator = coll.find();
            MongoCursor<Document> cursor = iterator.iterator();

            String idName = this.getIdFieldNameFromClass(clazz);

            while (cursor.hasNext()) {
                Document doc = cursor.next();
                log.info(doc.toJson());
                T data = this.readValue(doc.toJson(), idName, clazz);
                repo.save(data);
                num++;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return num;
    }

    private <T> String getIdFieldNameFromClass(final Class<T> clazz) {
        for (Field field : clazz.getDeclaredFields()) {
            Id id = field.getAnnotation(Id.class);
            if (id != null) {
                return field.getName();
            }
        }
        return null;
    }

    private <T> T readValue(final String json, final String idName, final Class<T> clazz) throws IOException {
        JsonNode jsonNode = this.mapper.readTree(json);
        if (idName != null && !jsonNode.has(idName) && jsonNode.has("_id") && !jsonNode.get("_id").isObject()) {
            ObjectNode oNode = (ObjectNode) jsonNode;
            oNode.set(idName, oNode.get("_id"));
            oNode.remove("_id");
        }
        return this.mapper.convertValue(jsonNode, clazz);
    }
}
