package go.server.DB;

import go.game.Colour;
import go.game.GameState;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class GameStateCodec implements Codec<GameState> {


    @Override
    public GameState decode(BsonReader bsonReader, DecoderContext decoderContext) {
        bsonReader.readStartDocument();
        int size = bsonReader.readInt32("size");
        bsonReader.readName("board");
        bsonReader.readStartArray();
        Colour[][] board = new Colour[size][size];
        for (int i = 0; i < size; i++) {
            bsonReader.readStartArray();
            for (int j = 0; j < size; j++) {
                int colour = bsonReader.readInt32();
                board[i][j] = Colour.values()[colour];
            }
            bsonReader.readEndArray();
        }
        bsonReader.readEndArray();
        int turn = bsonReader.readInt32("turn");
        int player1Captures = bsonReader.readInt32("player1Captures");
        int player2Captures = bsonReader.readInt32("player2Captures");
        bsonReader.readName("player");
        int player = bsonReader.readInt32();
        bsonReader.readName("finished");
        boolean finished = bsonReader.readBoolean();
        bsonReader.readName("passed");
        boolean passed = bsonReader.readBoolean();
        bsonReader.readEndDocument();

        return new GameState(size, board, turn, player1Captures, player2Captures, finished, Colour.values()[player], passed);
    }

    @Override
    public void encode(BsonWriter bsonWriter, GameState gameState, EncoderContext encoderContext) {
        bsonWriter.writeStartDocument();
        bsonWriter.writeInt32("size", gameState.getSize());
        bsonWriter.writeName("board");
        bsonWriter.writeStartArray();
        for (int i = 0; i < gameState.getSize(); i++) {
            bsonWriter.writeStartArray();
            for (int j = 0; j < gameState.getSize(); j++) {
                bsonWriter.writeInt32(gameState.getBoard()[i][j].ordinal());
            }
            bsonWriter.writeEndArray();
        }
        bsonWriter.writeEndArray();
        bsonWriter.writeInt32("turn", gameState.getTurn());
        bsonWriter.writeInt32("player1Captures", gameState.getPlayer1Captures());
        bsonWriter.writeInt32("player2Captures", gameState.getPlayer2Captures());
        bsonWriter.writeName("player");
        bsonWriter.writeInt32(gameState.getActivePlayer().ordinal());
        bsonWriter.writeName("finished");
        bsonWriter.writeBoolean(gameState.getFinished());
        bsonWriter.writeName("passed");
        bsonWriter.writeBoolean(gameState.getPassed());
        bsonWriter.writeEndDocument();
    }

    @Override
    public Class<GameState> getEncoderClass() {
        return GameState.class;
    }
}
