//This class defines the Practice object
//and stores information of individual practices:
//name, date, location, and attendees
//Practice implements Event, which outlines needed methods

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Practice implements Event{

    private String name;
    private LocalDateTime date;
    private String location;
    //HashMap to store the attending players of this practice
    HashMap<String, Player> attendees = new HashMap<>();

    //constructor of Practice
    public Practice(String name, LocalDateTime date, String location){
        this.name = name;
        this.date = date;
        this.location = location;
        //end of constructor
    }

    @Override
    public void addPlayer(Player p){attendees.put(p.getName(), p);} //add attending player to this practice
    @Override
    public void removePlayer(Player p) {attendees.remove(p.getName());}//remove attendees

    //method to return all attendee names
    @Override
    public String getAttendeesNames() {
        return attendees.values().stream()
                .map(Player::getName)
                .collect(Collectors.joining(", "));
    }

    //implement all set methods from the Event interface
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
    public String getEventType(){return "Practice";};


    //toString method to return all basic information about a practice
    public String toString(){
        return getName()+", "+getDate()+", "+getLocation();
    }

}
