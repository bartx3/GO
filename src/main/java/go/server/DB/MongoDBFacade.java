package go.server.DB;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import go.communications.Credentials;
import go.game.Game;
import go.game.GameState;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static go.server.Server.logger;

public class MongoDBFacade implements DBFacade {
    private MongoDatabase database;

    public MongoDBFacade() {
        String connectionString = "mongodb+srv://student:1234@mydb1.ewqpyk0.mongodb.net/?retryWrites=true&w=majority";  // Wiem, że niebezpieczne
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();
        CodecRegistry defaultCodecRegistry = MongoClientSettings.getDefaultCodecRegistry();
        CodecRegistry fromProvider = CodecRegistries.fromCodecs(new GameStateCodec());
        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(defaultCodecRegistry, fromProvider);

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .codecRegistry(codecRegistry)
                .build();

        MongoClient mongoClient = MongoClients.create(settings);
        database = mongoClient.getDatabase("gobase");
    }

    @Override
    public boolean register(Credentials credentials) {
        MongoCollection<Document> collection = database.getCollection("Credentials");
        Document document = new Document("username", credentials.getUsername())
                .append("password", credentials.getPassword());
        // Check if user already exists
        Document query = new Document("username", credentials.getUsername());
        if (collection.find(query).first() != null) {
            return false;
        }
        // If user does not exist, insert new user
        collection.insertOne(document);
        return true;
    }

    @Override
    public boolean login(Credentials credentials) {
        MongoCollection<Document> collection = database.getCollection("Credentials");
        Document query = new Document("username", credentials.getUsername())
                .append("password", credentials.getPassword());
        // Check if user exists and password matches
        if (collection.find(query).first() != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean saveGame(Game game) {
        MongoCollection<Document> collection = database.getCollection("Game");
        Document query = new Document("id", game.getId());
        Document document = new Document("id", game.getId())
                .append("player1", game.player1)
                .append("player2", game.player2)
                .append("size", game.getSize())
                .append("state", game.gameStates);
        collection.insertOne(document);
        // Check if game already exists
        if (!collection.find(query).first().isEmpty()) {
            //overwrite game
            collection.updateOne(
                    Filters.eq("id", game.getId()),
                    query
            );
        }
        else
            collection.insertOne(document);
        return true;
    }

    @Override
    public long newGame(String player1, String player2, int size) {
        MongoCollection<Document> collection = database.getCollection("Game");
        long id = generateUniqueGameID();
        Document document = new Document("id", id)
                .append("player1", player1)
                .append("player2", player2)
                .append("size", size)
                .append("state", new ArrayList<GameState>());
        collection.insertOne(document);
        return id;
    }

    private long generateUniqueGameID() {
        //count from 0 to first value not in database as id
        MongoCollection<Document> collection = database.getCollection("Game");
        long id = 0;
        while (collection.find(new Document("id", id)).first() != null) {
            id++;
        }
        return id;
    }

    @Override
    public Game loadGame(long id) {
        MongoCollection<Document> collection = database.getCollection("Game");
        Document query = new Document("id", id);
        Document document = collection.find(query).first();
        if (document != null) {
            List<GameState> state = (List<GameState>) document.get("state");
            return new Game(document.getString("player1"), document.getString("player2"), document.getInteger("size"), state, document.getLong("id"));
        }
        return null;
    }

    @Override
    public ArrayList<Long> getGameIds() {
        MongoCollection<Document> collection = database.getCollection("Game");
        List<Document> documents = collection.find().into(new ArrayList<>());
        ArrayList<Long> gameIds = new ArrayList<>();
        for (Document document : documents) {
            gameIds.add(document.getLong("id"));
        }
        return gameIds;
    }

    @Override
    public ArrayList<String> getUsernames() {
        MongoCollection<Document> collection = database.getCollection("Credentials");
        List<Document> documents = collection.find().into(new ArrayList<>());
        ArrayList<String> usernames = new ArrayList<>();
        for (Document document : documents) {
            usernames.add(document.getString("username"));
        }
        return usernames;
    }
}