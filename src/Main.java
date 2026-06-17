import java.util.ArrayList;

public class Main {
    //arrayList that stores all players, updated every time a player is added/remove
    static ArrayList<Player> allPlayersRostered = new ArrayList<Player>();
    //arrayList that stores all events, updated every time an events gets added/removed
    static ArrayList<Event> allEvents = new ArrayList<Event>();
    static ArrayList<Event> allPractices = new ArrayList<>();
    static ArrayList<Event> allCompetitions = new ArrayList<>();
    public static void main(String[] args) {
        DataManager.loadData();  //load saved data from last use
        GUI gui = new GUI("UA eSports Scheduling System"); //create the graphical user interface
    //end of main method
    }
//end of class
}