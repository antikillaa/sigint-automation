package users_management;

import app_context.properties.MongoConnectionProperties;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import error_reporter.ErrorReporter;
import org.bson.Document;

class MongoConnector {

    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private static MongoConnector instance;

    private MongoConnector() {
        initConnection();
    }

    static MongoConnector getMongoConnector() {
        if (instance == null) {
            instance = new MongoConnector();
        }
        return instance;
    }


    private void initConnection() {

        String host = MongoConnectionProperties.getHost();
        Integer port = Integer.valueOf(MongoConnectionProperties.getPort());
        mongoClient = new MongoClient(host, port);
    }

    void useDatabase(String database){
        mongoDatabase = mongoClient.getDatabase(database);
    }

    MongoCollection<Document> getCollection(String collectionName) {
        if (mongoDatabase == null) {
            ErrorReporter.raiseError("You must select database first!");
        }
        return mongoDatabase.getCollection(collectionName);

    }
}
