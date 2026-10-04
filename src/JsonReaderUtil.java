import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class for reading JSON documents using JSON.simple ("simple-json").
 * Updated to work with User, Game, Player, Board, and Move classes from the project.
 */
public final class JsonReaderUtil {

    private JsonReaderUtil() {
    }

    /**
     * Reads the accounts from the given JSON file path.
     * Returns List of User objects.
     */
    public static List<User> readAccounts(Path path) throws IOException, ParseException {
        if (path == null || !Files.exists(path)) {
            return new ArrayList<>();
        }
        try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            JSONParser parser = new JSONParser();
            Object root = parser.parse(reader);
            JSONArray arr = asArray(root);
            List<User> result = new ArrayList<>();

            if (arr == null) {
                return result;
            }

            for (Object item : arr) {
                JSONObject obj = asObject(item);
                if (obj == null) {
                    continue;
                }

                String email = asString(obj.get("email"));
                String password = asString(obj.get("password"));
                int points = asInt(obj.get("points"), 0);

                // Create User with empty games list - games will be linked later
                User user = new User(email, password, new ArrayList<>(), points);

                result.add(user);
            }
            return result;
        }
    }

    /**
     * Reads the games from the given JSON file path and returns them as a map by id.
     */
    public static Map<Integer, Game> readGamesAsMap(Path path) throws IOException, ParseException {
        Map<Integer, Game> map = new HashMap<>();
        if (path == null || !Files.exists(path)) {
            return map;
        }
        try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            JSONParser parser = new JSONParser();
            Object root = parser.parse(reader);
            JSONArray arr = asArray(root);
            if (arr == null) return map;

            for (Object item : arr) {
                JSONObject obj = asObject(item);
                if (obj == null) {
                    continue;
                }

                int id = asInt(obj.get("id"), -1);
                if (id < 0) {
                    continue;
                }

                // Parse players
                JSONArray playersArr = asArray(obj.get("players"));
                Player player1 = null;
                Player player2 = null;

                if (playersArr != null && playersArr.size() >= 2) {
                    JSONObject p1Obj = asObject(playersArr.get(0));
                    JSONObject p2Obj = asObject(playersArr.get(1));

                    String email1 = asString(p1Obj.get("email"));
                    String color1Str = asString(p1Obj.get("color"));
                    Colors color1 = Colors.valueOf(color1Str);

                    String email2 = asString(p2Obj.get("email"));
                    String color2Str = asString(p2Obj.get("color"));
                    Colors color2 = Colors.valueOf(color2Str);

                    player1 = new Player(email1, color1);
                    player2 = new Player(email2, color2);
                }

                // Create board and populate it
                Board board = new Board();
                JSONArray boardArr = asArray(obj.get("board"));
                if (boardArr != null) {
                    for (Object bItem : boardArr) {
                        JSONObject bObj = asObject(bItem);
                        if (bObj == null) continue;

                        String type = asString(bObj.get("type"));
                        String colorStr = asString(bObj.get("color"));
                        String posStr = asString(bObj.get("position"));

                        Colors color = Colors.valueOf(colorStr);
                        Position pos = parsePosition(posStr);

                        Piece piece = createPiece(type, color, pos);
                        if (piece != null) {
                            ChessPair<Position, Piece> pair = new ChessPair<>(pos, piece);
                            board.getBoard().add(pair);
                        }
                    }
                }

                // Parse moves
                List<Move> moves = new ArrayList<>();
                JSONArray movesArr = asArray(obj.get("moves"));
                if (movesArr != null) {
                    for (Object mItem : movesArr) {
                        JSONObject mObj = asObject(mItem);
                        if (mObj == null) continue;

                        String colorStr = asString(mObj.get("playerColor"));
                        Colors playerColor = Colors.valueOf(colorStr);
                        Position from = parsePosition(asString(mObj.get("from")));
                        Position to = parsePosition(asString(mObj.get("to")));

                        // Check if there was a captured piece
                        Piece captured = null;
                        JSONObject capturedObj = asObject(mObj.get("captured"));
                        if (capturedObj != null) {
                            String captType = asString(capturedObj.get("type"));
                            String captColorStr = asString(capturedObj.get("color"));
                            Colors captColor = Colors.valueOf(captColorStr);
                            captured = createPiece(captType, captColor, to);
                        }

                        Move move = new Move(playerColor, from, to, captured);
                        moves.add(move);
                    }
                }

                // Determine current player index
                String currentColorStr = asString(obj.get("currentPlayerColor"));
                Colors currentColor = Colors.valueOf(currentColorStr);
                int index = (player1 != null && player1.getColor() == currentColor) ? 0 : 1;

                // Create Game
                Game game = new Game(id, board, player1, player2, moves, index);
                map.put(id, game);
            }
        }
        return map;
    }

    /**
     * Parses a position string like "A1" into a Position object.
     */
    private static Position parsePosition(String posStr) {
        if (posStr == null || posStr.length() != 2) return null;
        char x = posStr.charAt(0);
        int y = Integer.parseInt(posStr.substring(1));
        return new Position(x, y);
    }

    /**
     * Creates the appropriate Piece subclass based on type character.
     */
    private static Piece createPiece(String type, Colors color, Position pos) {
        if (type == null || type.isEmpty()) return null;
        try {
            return Factory.createPiece(color,type.charAt(0),pos);
        } catch (InvalidCommandException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    // -------- Helper converters --------

    private static JSONArray asArray(Object o) {
        return (o instanceof JSONArray) ? (JSONArray) o : null;
    }

    private static JSONObject asObject(Object o) {
        return (o instanceof JSONObject) ? (JSONObject) o : null;
    }

    private static String asString(Object o) {
        return o == null ? null : String.valueOf(o);
    }

    private static int asInt(Object o, int def) {
        if (o instanceof Number) return ((Number) o).intValue();
        try {
            return o != null ? Integer.parseInt(String.valueOf(o)) : def;
        } catch (NumberFormatException e) {
            return def;
        }
    }

    private static long asLong(Object o, long def) {
        if (o instanceof Number) return ((Number) o).longValue();
        try {
            return o != null ? Long.parseLong(String.valueOf(o)) : def;
        } catch (NumberFormatException e) {
            return def;
        }
    }
}
