package users_management;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class StoragePermissionsManager {

    private static final String STORAGE = "auth";
    private static final String COLLECTION = "permission";
    private MongoConnector mongoConnector = MongoConnector.getMongoConnector();


    public List<String> getPermissions() {
        mongoConnector.useDatabase(STORAGE);
        MongoCollection<Document> collection = mongoConnector.getCollection(COLLECTION);
        FindIterable<Document> documents = collection.find();
        List<String> permissions = new ArrayList<>();
        for (Document doc: documents) {
            permissions.add(doc.getString("_id"));
        }
        return permissions;
    }



}
