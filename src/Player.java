//This class defines the Player object
//and stores information about individual player:
//name, age, and grade level

import java.util.ArrayList;
import java.util.Comparator;

public class Player{

    //class variables
    private String name;
    private int age;
    private String playerGradeLevel;

    //use an ArrayList to store events and sort them in order from the closest
    // upcoming to the furthest date away
    transient ArrayList<Event> AssignedPracticesList = new ArrayList<Event>();
    transient ArrayList<Event> AssignedCompetitionsList = new ArrayList<Event>();
    ArrayList<String> savedPracticeNames = new ArrayList<>();
    ArrayList<String> savedCompetitionNames = new ArrayList<>();


    //constructor of Player
    public Player(String name, int age, String playerGradeLevel){
        this.name = name;
        this.age = age;
        this.playerGradeLevel = playerGradeLevel;
    //end of constructor
    }

    //method to assign a practice to this player
    public void assignPractice(Event event){
        //sorting algorithm is need to ensure events are sorted from the closest date to the furthest date
            AssignedPracticesList.add(event);
            sortPracticesByDate();
    }
    //method to assign a competition to this player
    public void assignCompetition(Event event){
        //sorting algorithm is need to ensure events are sorted from the closest date to the furthest date
            AssignedCompetitionsList.add(event);
            sortCompetitionsByDate();
    }

    //method to remove a practice from this player
    public void removePractice(Event event){
        //make sure that the sorting sequence is correct and that it doesn't have a empty slot
        AssignedPracticesList.remove(event);
        sortPracticesByDate();
    }

    //method to remove a competition from this player
    public void removeCompetition(Event event){
        AssignedCompetitionsList.remove(event);
        sortCompetitionsByDate();
    }

    //method to sort practices by date
    public void sortPracticesByDate(){
        AssignedPracticesList.sort(Comparator.comparing(Event::getDate));
    }

    //method to sort competitions by date
    public void sortCompetitionsByDate(){
        AssignedCompetitionsList.sort(Comparator.comparing(Event::getDate));
    }

    //method to set name
    public void setName(String name){
        this.name = name;
    }
    //method to set age
    public void setAge(int age){
        this.age = age;
    }
    //method to set ID
    public void setPlayerGradeLevel(String playerGradeLevel){
        this.playerGradeLevel = playerGradeLevel;
    }
    //method to get name
    public String getName(){
        return name;
    }
    //method to get age
    public int getAge(){
        return age;
    }
    //method to get ID
    public String getPlayerGradeLevel(){
        return playerGradeLevel;
    }
    //method to get assigned practices
    public String getAssignedPractices(){
        String assignedPractices = "";
        for(int i = 0; i < AssignedPracticesList.size(); i++){
            assignedPractices += AssignedPracticesList.get(i).getName()
                    +", "+ AssignedPracticesList.get(i).getDate()+"\n";
        }
    return assignedPractices;
    }
    //method to get assigned practices
    public String getAssignedCompetitions(){
        String assignedCompetitions = "";
        for(int i = 0; i < AssignedCompetitionsList.size(); i++){
            assignedCompetitions += AssignedCompetitionsList.get(i).getName()
                    +", "+ AssignedCompetitionsList.get(i).getDate()+"\n";
        }
    return assignedCompetitions;
    }


    //method to print out basic player info
    public String toString(){
        return getName() + ", " + getAge() + ", " + getPlayerGradeLevel()+" grade";
    }

}
