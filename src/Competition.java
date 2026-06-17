//This class defines the Competition object
//and stores information of individual competitions:
//name, date, location,game, and attendees
//Competition implements Event, which outlines needed methods

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Competition implements Event{

    private String name;
    private LocalDateTime date;
    private String location;
    private String game;
    //Hashmap for the attending players of this Competition
    HashMap<String, Player> attendees = new HashMap<>();

    //constructor of Competition
    public Competition(String name, LocalDateTime date, String location, String game){
        this.name = name;
        this.date = date;
        this.location = location;
        this.game = game;
    }

    @Override
    public void addPlayer(Player p){attendees.put(p.getName(), p);}//add attendees to this Competition
    @Override
    public void removePlayer(Player p){attendees.remove(p.getName());}//REMOVE attendees

    //method to return the name of attendees of this Competition
    @Override
    public String getAttendeesNames() {
        return attendees.values().stream()
                .map(Player::getName)
                .collect(Collectors.joining(", "));
    }


    //exclusive setGame and getGame for this class
    public void setGame(String game){this.game = game;}
    public String getGame(){return game;}

    @Override
    public void setName(String name){this.name = name;}
    @Override
    public void setDate(LocalDateTime date){this.date = date;}
    @Override
    public void setLocation(String location){this.location = location;}

    //implement all get methods from the Event interface
    @Override
    public String getName(){return name;}
    @Override
    public LocalDateTime getDate(){return date;}
    @Override
    public String getLocation(){return location;}
    @Override
    public String getEventType(){return "Competition";}


    public String toString(){
        return getName()+", "+getDate()+", "+getLocation()+", "+getGame();
    }
}
