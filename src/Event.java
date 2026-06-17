//Event is an interface that serves as a skeleton
//for Practice and Competition

import java.time.LocalDateTime;

public interface Event {

    default void setName(String name){}
    default void setDate(LocalDateTime date){}
    default void setLocation(String location){}


    default void addPlayer(Player player){}
    default String getAttendeesNames(){return null;}

    default String getName(){return null;}
    default LocalDateTime getDate(){return null;}
    default String getLocation(){return null;}
    default String getEventType(){return null;}
    default String getGame(){return null;}
    default void removePlayer(Player player){}


}
