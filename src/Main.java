import org.json.simple.*;
import org.json.simple.parser.*;
import java.util.*;
import java.io.*;
import java.nio.file.*;

public class Main {

    private static Main singletonInstance = null;

    private List<User> users;
    private Map<Integer, Game> games;
    private User currentUser;
    private final Scanner sc;

    private Main() {
        users = new ArrayList<>();
        games = new HashMap<>();
        this.currentUser = null;
        this.sc = new Scanner(System.in);
    }

    public static Main getSingletonInstance() {

        if(singletonInstance == null) {
            singletonInstance = new Main();
        }

        return singletonInstance;
    }

    public static void main(String[] args){
        Main chess = Main.getSingletonInstance();
        chess.read();
        chess.run();
        chess.write();
    }

    public void read(){
        System.out.println("Reading");

        Path accounts = Paths.get("src", "input", "accounts.json");
        Path games = Paths.get("src","input", "games.json");

        try {
            List<User> readUsers = JsonReaderUtil.readAccounts(accounts);
            int size = readUsers.size();

            for(int i = 0; i < size; i++){
                User temp = readUsers.get(i);

                if(temp.getEmail() == null || temp.getPassword() == null){
                    throw new InvalidCommandException("Error");
                }
            }

            this.users = new ArrayList<>(readUsers);

            Map<Integer, Game> readGames = JsonReaderUtil.readGamesAsMap(games);
            this.games = new HashMap<>(readGames);

            System.out.println("Success");
        } catch (IOException | ParseException | InvalidCommandException e){
            this.users = new ArrayList<>();
            this.games = new HashMap<>();
        }
    }

    public void write() {

        System.out.println("Writing");

        Path accounts = Paths.get("src", "input", "accounts.json");
        Path games = Paths.get("src", "input", "games.json");

        try {
            JSONArray accArray = new JSONArray();
            int size = users.size();

            for(int i = 0; i < size; i++){
                User temp = users.get(i);
                JSONObject userObj = new JSONObject();

                userObj.put("email", temp.getEmail());
                userObj.put("password", temp.getPassword());
                userObj.put("points", temp.getPoints());

                JSONArray gamesArr = new JSONArray();
                List<Game> userGames = temp.getActiveGames();

                if(userGames != null){
                    int sizeG = userGames.size();

                    for(int j = 0; j < sizeG; j++){
                        Game g = userGames.get(j);

                        if(g != null){
                            gamesArr.add(g.getId());
                        }
                    }
                }

                userObj.put("games", gamesArr);

                accArray.add(userObj);
            }

            FileWriter fileWriter = new FileWriter(accounts.toFile());

            fileWriter.write(accArray.toJSONString());
            fileWriter.close();

            JSONArray gamesArray = new JSONArray();

            Collection<Game> value = this.games.values();
            ArrayList<Game> tempGames = new ArrayList<>(value);
            int totalGames = tempGames.size();

            for (int i = 0; i < totalGames; i++) {

                if(i >= tempGames.size()){
                    break;
                }

                Game game = tempGames.get(i);
                JSONObject gameObj = new JSONObject();
                gameObj.put("id", game.getId());

                JSONArray playersArr = new JSONArray();
                Player p1 = game.getPlayer1();
                Player p2 = game.getPlayer2();

                JSONObject player1 = new JSONObject();
                player1.put("email", p1.getPlayerName());
                player1.put("color", p1.getColor().toString());
                playersArr.add(player1);

                JSONObject player2 = new JSONObject();
                player2.put("email", p2.getPlayerName());
                player2.put("color", p2.getColor().toString());
                playersArr.add(player2);

                gameObj.put("players", playersArr);

                JSONArray boardPieces = new JSONArray();
                TreeSet<ChessPair<Position, Piece>> boardSet = game.getBoard().getBoard();
                ArrayList<ChessPair<Position, Piece>> tempBoard = new ArrayList<>(boardSet);
                int sizeB = tempBoard.size();

                for (int j = 0; j < sizeB; j++) {
                    ChessPair<Position, Piece> pair = tempBoard.get(j);
                    JSONObject pieceObj = new JSONObject();
                    Piece piece = pair.getValue();
                    Position pos = pair.getKey();

                    pieceObj.put("type", String.valueOf(piece.type()));
                    pieceObj.put("color", piece.getColor().toString());
                    pieceObj.put("position", "" + pos.getX() + pos.getY());
                    boardPieces.add(pieceObj);
                }
                gameObj.put("board", boardPieces);

                JSONArray movesArr = new JSONArray();
                List<Move> movesList = game.getMoves();
                int sizeM = movesList.size();

                for (int k = 0; k < sizeM; k++) {
                    Move move = movesList.get(k);
                    JSONObject moveObj = new JSONObject();
                    moveObj.put("playerColor", move.getPieceColor().toString());
                    moveObj.put("from", "" + move.getBefore().getX() + move.getBefore().getY());
                    moveObj.put("to", "" + move.getAfter().getX() + move.getAfter().getY());

                    if (move.getCaptured() != null) {
                        JSONObject capturedObj = new JSONObject();
                        capturedObj.put("type", String.valueOf(move.getCaptured().type()));
                        capturedObj.put("color", move.getCaptured().getColor().toString());
                        moveObj.put("captured", capturedObj);
                    }

                    movesArr.add(moveObj);
                }
                gameObj.put("moves", movesArr);

                gameObj.put("currentPlayerColor", game.getPlayerIndex().getColor().toString());

                gamesArray.add(gameObj);
            }

            FileWriter gameWrite = new FileWriter(games.toFile());
            gameWrite.write(gamesArray.toJSONString());
            gameWrite.close();

            System.out.println("Success");
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    public User login(String email, String password){
        int size = users.size();

        for(int i = 0; i < size; i++){
            User temp = users.get(i);
            String userEmail = temp.getEmail();
            String userPassword = temp.getPassword();

            if(userEmail.equals(email) && userPassword.equals(password)){
                currentUser = temp;
                return temp;
            }
        }

        return null;
    }

    public User newAccount(String email, String password) {
        User temp = new User(email, password);
        users.add(temp);
        currentUser = temp;
        return temp;
    }

    public void run(){
        System.out.println("Welcome");

        while (currentUser == null) {
            System.out.println();
            System.out.println("Options:");
            System.out.println("login email password");
            System.out.println("new email password");
            System.out.println("exit");

            String line = sc.nextLine();
            String[] parts = line.split(" ");

            if (parts.length == 0) {
                System.out.println("Invalid");
                continue;
            }

            String word = parts[0];

            if (word.equalsIgnoreCase("exit")) {
                return;
            }

            if (parts.length != 3) {
                System.out.println("Invalid");
                continue;
            }

            String email = parts[1];
            String password = parts[2];

            if (word.equalsIgnoreCase("login")) {
                User user = login(email, password);

                if (user != null) {
                    System.out.println("Success");
                } else {
                    System.out.println("Failed");
                }
            } else if (word.equalsIgnoreCase("new")) {
                newAccount(email, password);
                System.out.println("Success");
            } else {
                System.out.println("Failed");
            }
        }

        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println("Menu " + currentUser.getEmail());
            System.out.println("1. New Game:Computer");
            System.out.println("2. New Game:Player");
            System.out.println("3. Resume Game");
            System.out.println("4. Logout");
            System.out.print("Choice: ");

            String choice = sc.nextLine();

            if (choice.equals("1")) {
                newGame(true);
            } else if (choice.equals("2")) {
                newGame(false);
            } else if (choice.equals("3")) {
                resumeGame();
            } else if (choice.equals("4")) {
                System.out.println("Logout");
                currentUser = null;
                running = false;
            } else {
                System.out.println("Invalid");
            }
        }
    }

    private void newGame(boolean Computer) {
        Player player1;
        Player player2;

        if (Computer) {
            player1 = new Player(currentUser.getEmail(), Colors.WHITE);
            player2 = new Player("Computer", Colors.BLACK);
        } else {
            System.out.print("Enter second players name: ");
            String name = sc.nextLine();
            player1 = new Player(currentUser.getEmail(), Colors.WHITE);
            player2 = new Player(name, Colors.BLACK);
        }

        int id = games.size() + 1;
        Game game = new Game(id, player1, player2);

        games.put(id, game);
        currentUser.addGame(game);

        playGame(game, Computer);
    }

    private void resumeGame() {
        List<Game> activeGames = currentUser.getActiveGames();

        if (activeGames.isEmpty()) {
            System.out.println("No games!");
            return;
        }

        System.out.println();
        System.out.println("Saved games:");

        int size = activeGames.size();

        for (int i = 0; i < size; i++) {
            Game oneGame = activeGames.get(i);
            System.out.println((i+1) + ". Game ID " + oneGame.getId() + " - " + oneGame.getPlayer1().getPlayerName() + " vs " + oneGame.getPlayer2().getPlayerName());
        }

        System.out.print("Select: ");
        String input = sc.nextLine();

        try {
            int numbers = Integer.parseInt(input);

            if (numbers < 1 || numbers > size) {
                System.out.println("Invalid");
                return;
            }

            Game game = activeGames.get(numbers - 1);
            game.resume();

            boolean Computer = game.getPlayer1().getPlayerName().equals("Computer") || game.getPlayer2().getPlayerName().equals("Computer");

            playGame(game, Computer);

        } catch (NumberFormatException e) {
            System.out.println("Invalid");
        }
    }

    private void playGame(Game game, boolean Computer) {
        game.start();

        GameLogger gameLogger = new GameLogger();
        game.addObserver(gameLogger);


        boolean active = true;

        while (active) {
            createBoard(game.getBoard());
            Player current = game.getPlayerIndex();
            System.out.println();
            System.out.println("Turn: " + current.getColor() + " (" + current.getPlayerName() + ")");

            if (game.checkForCheckMate()) {
                System.out.println("CHECKMATE!");
                System.out.println(current.getColor() + " loses");
                break;
            }

            if (Computer && current.getPlayerName().equals("Computer")) {
                boolean currentMove = turn(game,true);

                if(currentMove){
                    game.switchPlayer();
                } else {
                    System.out.println("Game over");
                    active = false;
                }
            } else {
                boolean continued = turn(game, false);

                if (!continued) {
                    System.out.println(current.getPlayerName() + " left");
                    active = false;
                } else {
                    game.switchPlayer();
                }
            }
        }
    }

    private boolean turn(Game game, boolean isComputer) {
        Player current = game.getPlayerIndex();

        if (isComputer) {
            current.getColorPieces(game.getBoard());
            List<ChessPair<Position, Piece>> ownedPieces = current.getOwnedPieces();
            ArrayList<ChessPair<Position, Piece>> temp = new ArrayList<>(ownedPieces);
            Random random = new Random();

            while (!temp.isEmpty()) {
                int randomNumber = random.nextInt(temp.size());
                ChessPair<Position, Piece> pair = temp.get(randomNumber);
                Position from = pair.getKey();
                Piece piece = pair.getValue();

                List<Position> moves = piece.getPossibleMoves(game.getBoard());

                if (moves.isEmpty()) {
                    temp.remove(randomNumber);
                    continue;
                }

                int move = random.nextInt(moves.size());
                Position to = moves.get(move);

                if (!game.getBoard().isValidMove(from, to)) {
                    temp.remove(randomNumber);
                    continue;
                }

                try {
                    System.out.println("Computer moves " + piece.type() + " from " + from + " to " + to);
                    current.makeMove(from, to, game.getBoard(), "Queen");
                    game.addMove(current, from, to);
                    return true;
                } catch (InvalidMoveException e) {
                    temp.remove(randomNumber);
                }
            }

            System.out.println("Computer no moves");
            return false;
        } else {
            System.out.println("Your turn " + current.getColor());

            while (true) {
                System.out.print("Enter move or 'leave': ");
                String input = sc.nextLine();

                if (input.equalsIgnoreCase("leave")) {
                    Random random = new Random();
                    current.getColorPieces(game.getBoard());
                    List<ChessPair<Position, Piece>> foundPieces = current.getOwnedPieces();
                    ArrayList<ChessPair<Position, Piece>> tempList = new ArrayList<>(foundPieces);

                    if(!tempList.isEmpty()){
                        int randomNumbers = random.nextInt(tempList.size());
                        Position from = tempList.get(randomNumbers).getKey();
                        Piece piece = tempList.get(randomNumbers).getValue();
                        List<Position> moves = piece.getPossibleMoves(game.getBoard());

                        if(!moves.isEmpty()){
                            Position to = moves.get(random.nextInt(moves.size()));

                            try{
                                current.makeMove(from, to, game.getBoard(), "Queen");
                                game.addMove(current, from, to);
                                System.out.println("Random move: " + from + " to " + to);
                            } catch(Exception ex){
                            }
                        }
                    }

                    return false;
                }

                try {
                    Position[] moveArr = readMove(input);
                    Position from = moveArr[0];
                    Position to = moveArr[1];

                    String promotion = "Queen";
                    Piece movingPiece = game.getBoard().getPieceAt(from);

                    if (movingPiece instanceof Pawn && (to.getY() == 1 || to.getY() == 8)) {
                        promotion = "Queen";
                    }

                    current.makeMove(from, to, game.getBoard(), promotion);
                    game.addMove(current, from, to);
                    return true;
                } catch (InvalidCommandException | InvalidMoveException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
    }

    private Position[] readMove(String input) throws InvalidCommandException {
        if (input == null || !input.matches("^[A-Ha-h][1-8]-[A-Ha-h][1-8]$")) {
            throw new InvalidCommandException("Invalid");
        }

        String[] parts = input.toUpperCase().split("-");
        char fromX = parts[0].charAt(0);
        int fromY = Integer.parseInt(parts[0].substring(1));
        char toX = parts[1].charAt(0);
        int toY = Integer.parseInt(parts[1].substring(1));
        return new Position[]{new Position(fromX, fromY), new Position(toX, toY)};
    }

    private void createBoard(Board board) {
        System.out.println();
        System.out.println("    A    B    C    D    E    F    G    H");
        System.out.println(" ------------------------------------------");

        for (int y = 8; y >= 1; y--) {
            System.out.print(y + "|");

            for (char x = 'A'; x <= 'H'; x++) {
                Piece piece = board.getPieceAt(new Position(x, y));

                if (piece == null) {
                    System.out.print(" ..  ");
                } else {
                    char type = piece.type();
                    char color;
                    if(piece.getColor() == Colors.WHITE){
                        color = 'W';
                    } else {
                        color = 'B';
                    }
                    System.out.print(" " + type + "-" + color + " ");
                }
            }

            System.out.println("|" + y);
        }

        System.out.println(" ------------------------------------------");
        System.out.println("    A    B    C    D    E    F    G    H");
    }
}
