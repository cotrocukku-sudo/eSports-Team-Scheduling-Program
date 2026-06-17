//This class is used to save the data of the program
//includes methods to save and load data

import com.google.gson.*;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DataManager {

    private static final String SAVE_FILE = "data.json";

    private static Gson buildGson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(Event.class, new EventAdapter())
                .setPrettyPrinting()
                .create();
    }

    public static void saveData() {
        // convert each player's event lists into name strings before saving
        for (Player p : Main.allPlayersRostered) {
            p.savedPracticeNames.clear();
            p.savedCompetitionNames.clear();
            for (Event e : p.AssignedPracticesList) {
                p.savedPracticeNames.add(e.getName());
            }
            for (Event e : p.AssignedCompetitionsList) {
                p.savedCompetitionNames.add(e.getName());
            }
        }

        Gson gson = buildGson();
        SaveData data = new SaveData();
        data.players = Main.allPlayersRostered;
        data.events = Main.allEvents;

        try (Writer writer = new FileWriter(SAVE_FILE)) {
            gson.toJson(data, writer);
            System.out.println("Data saved");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //method to load saved data from last use
    public static void loadData() {
        File file = new File(SAVE_FILE);
        if (!file.exists()) return;

        Gson gson = buildGson();
        try (Reader reader = new FileReader(file)) {
            SaveData data = gson.fromJson(reader, SaveData.class);
            if (data != null) {
                Main.allEvents = data.events != null ? data.events : new ArrayList<>();
                Main.allPlayersRostered = data.players != null ? data.players : new ArrayList<>();

                // rebuild allPractices and allCompetitions
                for (Event e : Main.allEvents) {
                    if (e instanceof Practice) Main.allPractices.add(e);
                    else if (e instanceof Competition) Main.allCompetitions.add(e);
                }

                // reconnect each player's event lists using saved names
                for (Player p : Main.allPlayersRostered) {
                    p.AssignedPracticesList = new ArrayList<>();
                    p.AssignedCompetitionsList = new ArrayList<>();
                    for (String name : p.savedPracticeNames) {
                        for (Event e : Main.allPractices) {
                            if (e.getName().equals(name)) {
                                p.AssignedPracticesList.add(e);
                                break;
                            }
                        }
                    }
                    for (String name : p.savedCompetitionNames) {
                        for (Event e : Main.allCompetitions) {
                            if (e.getName().equals(name)) {
                                p.AssignedCompetitionsList.add(e);
                                break;
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class SaveData {
        ArrayList<Player> players;
        ArrayList<Event> events;
    }
}