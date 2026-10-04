
import java.util.*;

public class User {

    private String email;
    private String password;
    private List<Game> games;
    private int points;

    public User(String email, String password){
        this.email = email;
        this.password = password;
        games = new ArrayList<>();
        this.points = 0;
    }

    public User(String email, String password, List<Game> games, int points){
        this.email = email;
        this.password = password;
        this.games = new ArrayList<>(games);
        this.points = points;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password =password;
    }

    public List<Game> getActiveGames() {
        List<Game> activeGames = new ArrayList<>();
        int size = games.size();
        for( int i = 0; i < size; i++){
            Game game = games.get(i);
            activeGames.add(game);
        }
        return activeGames;
    }


    public void setGames(List<Game> games){
        this.games = new ArrayList<>(games);
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }


    public void addGame(Game game){
        games.add(game);
    }

    public void removeGame(Game game) {
        if(game != null) {
            int size = games.size();
            for( int i =0; i< size; i++) {
                int id = games.get(i).getId();
                if( id == game.getId()){
                    games.remove(i);
                    break;
                }
            }
        }
    }

}
