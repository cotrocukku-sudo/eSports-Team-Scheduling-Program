
import com.github.lgooddatepicker.components.DateTimePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class GUI extends javax.swing.JFrame implements ActionListener{

    public GUI(String title){
        //create JFrame
        JFrame GuiWindow = new JFrame(title);
        //set the size of JFrame
        GuiWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GuiWindow.setResizable(false);//make GUI fixed size
        GuiWindow.setSize(1400,700); //set the size of the GUI
        GuiWindow.setLocationRelativeTo(null); //center the GUI
        GuiWindow.setLayout(new BorderLayout());
        GuiWindow.setVisible(true);

        //create JPanel container
        JPanel cardPanelsContainer = new JPanel();
        CardLayout cardLayout = new CardLayout();
        cardPanelsContainer.setLayout(cardLayout);
        GuiWindow.add(cardPanelsContainer);


        //***************************************************************************************************
        //BELOW ARE THE CODE FOR HOME PAGE
        //***************************************************************************************************
        JPanel homePanel = new JPanel();
        homePanel.setLayout(new BoxLayout(homePanel, BoxLayout.Y_AXIS));
        cardPanelsContainer.add(homePanel,"Home");
        homePanel.setSize(1000,700);
        homePanel.setVisible(true);
        cardLayout.show(cardPanelsContainer,"Home");


        //create an Events button that displays all upcoming events,
        // info, ability to edit(remove/add) on a new Panel
        JButton EventsButton = new JButton("Events");
        EventsButton.setMaximumSize(new Dimension(300,100));
        EventsButton.addActionListener(e -> cardLayout.show(cardPanelsContainer,"Events"));
        homePanel.add(EventsButton);
        EventsButton.setVisible(true);
        //create a Players button displays all assigned upcoming events,
        // info, ability to edit roster(remove/add) on a new Panel
        JButton PlayersButton = new JButton("Players");
        PlayersButton.setMaximumSize(new Dimension(300,100));
        PlayersButton.addActionListener(e -> cardLayout.show(cardPanelsContainer,"Players"));
        homePanel.add(PlayersButton);
        PlayersButton.setVisible(true);

        JButton saveButton = new JButton("Manual Save");
        saveButton.setMaximumSize(new Dimension(300,100));
        saveButton.addActionListener(e -> DataManager.saveData());
        homePanel.add(saveButton);













        //**************************************************************************************
        //BELOW ARE THE CODE FOR EVENTS PAGE
        //**************************************************************************************
        JPanel eventsPanel = new JPanel();
        eventsPanel.setLayout(new BoxLayout(eventsPanel, BoxLayout.Y_AXIS));
        cardPanelsContainer.add(eventsPanel,"Events");

        //create home button that leads back to home page
        JButton homeButtonEvents = new JButton("Home");
        homeButtonEvents.addActionListener(e ->  cardLayout.show(cardPanelsContainer,"Home"));
        eventsPanel.add(homeButtonEvents);

        //create a small window that asks for information needed for creating an event
        JPanel addEventPanel = new JPanel();
        addEventPanel.setLayout(new BoxLayout(addEventPanel, BoxLayout.Y_AXIS));
        addEventPanel.setBackground(Color.ORANGE);
        cardPanelsContainer.add(addEventPanel,"Add Event");

        //ADD COMPONENTS TO addEventPanel, gather info for EventName, Date, location, etc
        //add a dropdown to select event type
        JComboBox eventsTypeSelector = new JComboBox();
        eventsTypeSelector.addItem("Select Event Type");
        eventsTypeSelector.addItem("Practice");
        eventsTypeSelector.addItem("Competition");
        addEventPanel.add(eventsTypeSelector);
        eventsTypeSelector.setMaximumSize(new Dimension(300,30));

        //add a textField to input event name
        JTextField addEventNameInfoTextbox = new JTextField("ENTER EVENT NAME");
        addEventNameInfoTextbox.setMaximumSize(new Dimension(300,30));
        addEventPanel.add(addEventNameInfoTextbox);

        //add a date picker
        DateTimePicker addEventDatePicker = new DateTimePicker();
        addEventPanel.add(addEventDatePicker);
        addEventDatePicker.setMaximumSize(new Dimension(500,30));

        //add a textField to input location
        JTextField addEventLocationInfoTextbox = new JTextField("ENTER LOCATION");
        addEventPanel.add(addEventLocationInfoTextbox);
        addEventLocationInfoTextbox.setMaximumSize(new Dimension(300,30));

        //add a textField to input Game, ONLY SHOW IF COMPETITION IS SELECTED
        JTextField addEventGameInfoTextbox = new JTextField("ENTER GAME");
        addEventPanel.add(addEventGameInfoTextbox);
        addEventGameInfoTextbox.setMaximumSize(new Dimension(300,30));
        addEventGameInfoTextbox.setVisible(false);

        //add action listener for event selector
        eventsTypeSelector.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(eventsTypeSelector.getSelectedItem().equals("Competition")){
                    addEventGameInfoTextbox.setVisible(true);
                } else {
                    addEventGameInfoTextbox.setVisible(false);
                }
                addEventPanel.revalidate();
                addEventPanel.repaint();
            }
        });


        //button to shows the addEventPanel
        JButton addEventPageButton = new JButton("Add Event");
        addEventPageButton.setSize(150,50);
        addEventPageButton.addActionListener(e -> cardLayout.show(cardPanelsContainer,"Add Event"));
        eventsPanel.add(addEventPageButton);
        addEventPageButton.setVisible(true);

        //display all events info using a JTable
        String[] eventColumnNames = {"Event Type","Name","date","location","game","Attendees"};
        DefaultTableModel eventTableModel = new DefaultTableModel(eventColumnNames, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 1) {
                    return LocalDateTime.class;
                }
                return String.class;
            }
        };
        for(Event event : Main.allEvents){
            eventTableModel.addRow(new Object[]{
                    event.getEventType(),
                    event.getName(),
                    event.getDate(),
                    event.getLocation(),
                    event.getGame(),
                    event.getAttendeesNames()
            });
        }
        JTable eventTable = new JTable(eventTableModel);
        TableRowSorter<DefaultTableModel> eventTableSorter =
                new TableRowSorter<>(eventTableModel);
        eventTableSorter.setComparator(2, Comparator.naturalOrder()); // column 1 = date
        eventTable.setRowSorter(eventTableSorter);
        eventTableSorter.setSortKeys(List.of(
                new RowSorter.SortKey(2, SortOrder.ASCENDING)
        ));
        eventTableSorter.sort();
        for (int i = 0; i < eventTableModel.getColumnCount(); i++) {
            eventTableSorter.setSortable(i, false);
        }
        JScrollPane eventScrollPaneTable = new JScrollPane(eventTable);
        //set color
        eventTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    switch (column) {
                        case 0 -> c.setBackground(new Color(200,220,255));
                        case 1 -> c.setBackground(new Color(255,255,200));
                        case 2 -> c.setBackground(new Color(200,255,200));
                        case 3 -> c.setBackground(new Color(255,220,220));
                        case 4 -> c.setBackground(new Color(0,150,255));
                        case 5 -> c.setBackground(new Color(255,220,2));
                    }
                }
                return c;
            }
        });
        //scrollPaneTable.setSize(500,500);
        eventsPanel.add(eventScrollPaneTable);


        //Cancel button to head back to Events page
        JButton addEventsCancelButton = new JButton("Cancel");
        addEventsCancelButton.addActionListener(e -> {
            eventsTypeSelector.setSelectedIndex(0);
            addEventNameInfoTextbox.setText("ENTER EVENT NAME");
            addEventDatePicker.clear();
            addEventLocationInfoTextbox.setText("ENTER LOCATION");
            addEventGameInfoTextbox.setText("ENTER GAME");
            addEventGameInfoTextbox.setVisible(false);
            addEventPanel.revalidate();  // add this
            addEventPanel.repaint();     // add this
            cardLayout.show(cardPanelsContainer,"Events");
        });
        addEventsCancelButton.setSize(150,50);
        addEventPanel.add(addEventsCancelButton);

        //add components for assign event panel
        JComboBox eventsComboBox = new JComboBox();
        eventsComboBox.setMaximumSize(new Dimension(550,30));
        eventsComboBox.addItem("Select Event");
        for(Event event : Main.allEvents){
            eventsComboBox.addItem(event);
        }



        //players combo box
        JComboBox playersComboBox = new JComboBox();
        playersComboBox.setMaximumSize(new Dimension(550,30));
        playersComboBox.addItem("Select Player");
        for(Player player : Main.allPlayersRostered){
            playersComboBox.addItem(player);
        }

        //button that takes inputted values and create a event, include game info only in competition
        JButton createEventButton = new JButton("Create Event");
        createEventButton.setSize(150,50);
        createEventButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if (eventsTypeSelector.getSelectedIndex() == 0) {
                    JOptionPane.showMessageDialog(null, "Please select an event type.");
                    return;
                }
                if (addEventNameInfoTextbox.getText().trim().isEmpty() ||
                        addEventNameInfoTextbox.getText().equals("ENTER EVENT NAME")) {
                    JOptionPane.showMessageDialog(null, "Please enter an event name.");
                    return;
                }
                if (addEventDatePicker.getDateTimeStrict() == null) {
                    JOptionPane.showMessageDialog(null, "Please select a valid date and time.");
                    return;
                }
                if (addEventLocationInfoTextbox.getText().trim().isEmpty() ||
                        addEventLocationInfoTextbox.getText().equals("ENTER LOCATION")) {
                    JOptionPane.showMessageDialog(null, "Please enter a location.");
                    return;
                }
                if (eventsTypeSelector.getSelectedItem().equals("Competition") &&
                        (addEventGameInfoTextbox.getText().trim().isEmpty() ||
                                addEventGameInfoTextbox.getText().equals("ENTER GAME"))) {
                    JOptionPane.showMessageDialog(null, "Please enter a game for the competition.");
                    return;
                }
                if(eventsTypeSelector.getSelectedItem().equals("Practice")){
                    Practice practice = new Practice(addEventNameInfoTextbox.getText(),
                            addEventDatePicker.getDateTimeStrict(),
                            addEventLocationInfoTextbox.getText());
                    Main.allEvents.add(practice);
                    Main.allPractices.add(practice);
                    Main.allEvents.sort(Comparator.comparing(Event::getDate));
                    Main.allPractices.sort(Comparator.comparing(Event::getDate));
                    cardLayout.show(cardPanelsContainer,"Events");
                    //add created event to display
                    eventTableModel.addRow(new Object[]{practice.getEventType(),
                                              practice.getName(),
                                              practice.getDate(),
                                              practice.getLocation(),
                                              null});
                    //add to assign event panel dropdown
                    eventsComboBox.removeAllItems();
                    eventsComboBox.addItem("Select Event");
                    for(Event event : Main.allEvents){
                        eventsComboBox.addItem(event);
                    }
                    DataManager.saveData();
                }else if(eventsTypeSelector.getSelectedItem().equals("Competition")){
                    Competition competition = new Competition(addEventNameInfoTextbox.getText(),
                            addEventDatePicker.getDateTimeStrict(),
                            addEventLocationInfoTextbox.getText(),
                            addEventGameInfoTextbox.getText());
                    Main.allEvents.add(competition);
                    Main.allCompetitions.add(competition);
                    Main.allEvents.sort(Comparator.comparing(Event::getDate));
                    Main.allCompetitions.sort(Comparator.comparing(Event::getDate));
                    cardLayout.show(cardPanelsContainer,"Events");
                    //add created event to display
                    eventTableModel.addRow(new Object[]{competition.getEventType(),
                            competition.getName(),
                             competition.getDate(),
                            competition.getLocation(),
                            competition.getGame()});
                    //add to assign event panel dropdown
                    eventsComboBox.removeAllItems();
                    eventsComboBox.addItem("Select Event");
                    for(Event event : Main.allEvents){
                        eventsComboBox.addItem(event);
                    }
                    DataManager.saveData();
                }
                eventsTypeSelector.setSelectedIndex(0);
                addEventNameInfoTextbox.setText("ENTER EVENT NAME");
                addEventDatePicker.clear();
                addEventLocationInfoTextbox.setText("ENTER LOCATION");
                addEventGameInfoTextbox.setText("ENTER GAME");
                addEventGameInfoTextbox.setVisible(false);
                addEventPanel.revalidate();
                addEventPanel.repaint();
            }
        });
        addEventPanel.add(createEventButton);
        createEventButton.setMaximumSize(new Dimension(150,30));

















        //****************************************************************************
        //BELOW ARE THE CODE FOR PLAYERS PAGE
        //****************************************************************************
        JPanel playersPanel = new JPanel();
        playersPanel.setLayout(new BoxLayout(playersPanel, BoxLayout.Y_AXIS));
        cardPanelsContainer.add(playersPanel,"Players");

        //create home button that leads back to home page
        JButton homeButtonPlayers = new JButton("Home");
        homeButtonPlayers.addActionListener(e ->  cardLayout.show(cardPanelsContainer,"Home"));
        playersPanel.add(homeButtonPlayers);

        //Create an add Player panel
        JPanel addPlayerPanel = new JPanel();
        addPlayerPanel.setLayout(new BoxLayout(addPlayerPanel, BoxLayout.Y_AXIS));
        addPlayerPanel.setBackground(new Color(24,196,249)); //blue
        cardPanelsContainer.add(addPlayerPanel,"Add Player");

        //ADD COMPONENTS TO addPlayerPanel, gather info for Name, Age, Grade level, etc

        JTextField addPlayerNameInfoTextbox = new JTextField("ENTER PLAYER NAME");
        addPlayerNameInfoTextbox.setMaximumSize(new Dimension(300,30));
        addPlayerPanel.add(addPlayerNameInfoTextbox);

        JTextField addPlayerAgeInfoTextbox = new JTextField("ENTER PLAYER AGE");
        addPlayerAgeInfoTextbox.setMaximumSize(new Dimension(300,30));
        addPlayerPanel.add(addPlayerAgeInfoTextbox);

        JComboBox playerGradeSelector = new JComboBox();
        playerGradeSelector.addItem("SELECT GRADE LEVEL");
        playerGradeSelector.addItem("6th");
        playerGradeSelector.addItem("7th");
        playerGradeSelector.addItem("8th");
        playerGradeSelector.addItem("9th");
        playerGradeSelector.addItem("10th");
        playerGradeSelector.addItem("11th");
        playerGradeSelector.addItem("12th");
        playerGradeSelector.addItem("Other");
        addPlayerPanel.add(playerGradeSelector);
        playerGradeSelector.setMaximumSize(new Dimension(300,30));

        JButton addPlayerCancelButton = new JButton("Cancel");
        addPlayerCancelButton.addActionListener(e -> {
            addPlayerNameInfoTextbox.setText("ENTER PLAYER NAME");
            addPlayerAgeInfoTextbox.setText("ENTER PLAYER AGE");
            playerGradeSelector.setSelectedIndex(0);
            cardLayout.show(cardPanelsContainer,"Players");
        });
        addPlayerCancelButton.setSize(150,50);
        addPlayerPanel.add(addPlayerCancelButton);


        //Buttons that show addPlayerPanel
        JButton addPlayerButton = new JButton("Add Player");
        addPlayerButton.setMaximumSize(new Dimension(150,30));
        addPlayerButton.addActionListener(e -> {cardLayout.show(cardPanelsContainer,"Add Player");});
        playersPanel.add(addPlayerButton);

        //create assign event panel
        JPanel assignEventsPanel = new JPanel();
        assignEventsPanel.setLayout(new BoxLayout(assignEventsPanel, BoxLayout.Y_AXIS));
        assignEventsPanel.setBackground(new Color(172,105,239));
        cardPanelsContainer.add(assignEventsPanel,"Assign Events");

        //add the eventsComboBox and playersComboBox
        assignEventsPanel.add(eventsComboBox);
        assignEventsPanel.add(playersComboBox);

        //button to cancel
        JButton assignPlayerCancelButton = new JButton("Cancel");
        assignPlayerCancelButton.addActionListener(e -> {cardLayout.show(cardPanelsContainer,"Players");});
        assignPlayerCancelButton.setSize(150,50);
        assignEventsPanel.add(assignPlayerCancelButton);

        //button that show assign event panel
        JButton assignEventsButton = new JButton("Assign Events");
        assignEventsButton.setMaximumSize(new Dimension(150,30));
        assignEventsButton.addActionListener(e -> {cardLayout.show(cardPanelsContainer,"Assign Events");});
        playersPanel.add(assignEventsButton);

        //display all players info using a JTable
        String[] playerColumnNames = {"Name","Age","Grade Level","Assigned Practices","Assigned Competitions"};
        DefaultTableModel playerTableModel = new DefaultTableModel(playerColumnNames, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 1) {
                    return LocalDateTime.class;
                }
                return String.class;
            }
        };
        for(Player player : Main.allPlayersRostered){
            playerTableModel.addRow(new Object[]{
                    player.getName(),
                    player.getAge(),
                    player.getPlayerGradeLevel(),
                    player.getAssignedPractices(),
                    player.getAssignedCompetitions()
            });
        }
        JTable playerTable = new JTable(playerTableModel);
        playerTable.setRowHeight(60);
        TableRowSorter<DefaultTableModel> playerTableSorter =
                new TableRowSorter<>(playerTableModel);
        playerTableSorter.setComparator(0, Comparator.naturalOrder()); // column 1 = date
        playerTable.setRowSorter(playerTableSorter);
        playerTableSorter.setSortKeys(List.of(
                new RowSorter.SortKey(0, SortOrder.ASCENDING)
        ));
        playerTableSorter.sort();
        JScrollPane playerScrollPaneTable = new JScrollPane(playerTable);
        //set color
        playerTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    switch (column) {
                        case 0 -> c.setBackground(new Color(200,220,255));
                        case 1 -> c.setBackground(new Color(255,255,200));
                        case 2 -> c.setBackground(new Color(200,255,200));
                        case 3 -> c.setBackground(new Color(100,255,200));
                        case 4 -> c.setBackground(new Color(200,255,100));
                    }
                }
                return c;
            }
        });
        //scrollPaneTable.setSize(500,500);
        playersPanel.add(playerScrollPaneTable);

        // Button to remove selected event
        JButton removeEventButton = new JButton("Remove Selected Event");
        removeEventButton.addActionListener(e -> {
            int selectedRow = eventTable.getSelectedRow();
            if (selectedRow != -1) {
                int modelRowEvent = eventTable.convertRowIndexToModel(selectedRow);
                Event selectedEvent = Main.allEvents.get(modelRowEvent);
                // Remove from main event lists
                Main.allEvents.remove(selectedEvent);
                if (selectedEvent instanceof Practice) {
                    Main.allPractices.remove(selectedEvent);
                } else if (selectedEvent instanceof Competition) {
                    Main.allCompetitions.remove(selectedEvent);}
                // Remove from table
                eventTableModel.removeRow(modelRowEvent);
                // Remove from assign dropdown
                eventsComboBox.removeItem(selectedEvent);
                // Remove the event from each player's assigned events
                for (Player p : Main.allPlayersRostered) {
                    p.removePractice(selectedEvent);
                    p.removeCompetition(selectedEvent);}
                // Update the player table to reflect changes
                for (Player p : Main.allPlayersRostered) {
                    for (int i = 0; i < playerTableModel.getRowCount(); i++) {
                        if (playerTableModel.getValueAt(i, 0).equals(p.getName())) {
                            playerTableModel.setValueAt(p.getAssignedPractices(), i, 3);
                            playerTableModel.setValueAt(p.getAssignedCompetitions(), i, 4);
                            break;
                        }
                    }
                }
            }
            DataManager.saveData();
        });
        eventsPanel.add(removeEventButton);

        // Button to remove selected player
        JButton removePlayerButton = new JButton("Remove Selected Player");
        removePlayerButton.addActionListener(e -> {
            int selectedRow = playerTable.getSelectedRow();
            if (selectedRow != -1) {
                int confirm = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to remove this player?",
                        "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm != JOptionPane.YES_OPTION) return;
                int modelRow = playerTable.convertRowIndexToModel(selectedRow);
                Player selectedPlayer = (Player) Main.allPlayersRostered.get(modelRow);
                for (Event event : Main.allEvents) {event.removePlayer(selectedPlayer);}
                for (int i = 0; i < eventTableModel.getRowCount(); i++) {
                    Event ev = Main.allEvents.get(i);
                    eventTableModel.setValueAt(ev.getAttendeesNames(), i, 5);
                }
                Main.allPlayersRostered.remove(selectedPlayer);
                playerTableModel.removeRow(modelRow);
                playersComboBox.removeItem(selectedPlayer);
            }
            DataManager.saveData();
        });
        playersPanel.add(removePlayerButton);

        //assign event to player button
        JButton assignEventToPlayerButton = new JButton("Assign Event To Player");
        assignEventToPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (eventsComboBox.getSelectedIndex() == 0) {
                    JOptionPane.showMessageDialog(null, "Please select an event.");
                    return;
                }
                if (playersComboBox.getSelectedIndex() == 0) {
                    JOptionPane.showMessageDialog(null, "Please select a player.");
                    return;
                }
                Event selectedEvent = (Event) eventsComboBox.getSelectedItem();
                Player selectedPlayer = (Player) playersComboBox.getSelectedItem();
                // check if already assigned
                if (selectedEvent.getEventType().equals("Practice") &&
                        selectedEvent.getAttendeesNames().contains(selectedPlayer.getName())) {
                    JOptionPane.showMessageDialog(null, "This player is already assigned to this practice.");
                    return;
                }
                if (selectedEvent.getEventType().equals("Competition") &&
                        selectedEvent.getAttendeesNames().contains(selectedPlayer.getName())) {
                    JOptionPane.showMessageDialog(null, "This player is already assigned to this competition.");
                    return;
                }
                 if(selectedEvent.getEventType().equals("Practice")){
                     selectedPlayer.assignPractice(selectedEvent);
                     DataManager.saveData();
                 }
                 else if(selectedEvent.getEventType().equals("Competition")){
                     selectedPlayer.assignCompetition(selectedEvent);
                     DataManager.saveData();
                 }
                for (int i = 0; i < playerTableModel.getRowCount(); i++) {
                    if (playerTableModel.getValueAt(i, 0)
                            .equals(selectedPlayer.getName())) {
                        playerTableModel.setValueAt(selectedPlayer.getAssignedPractices(), i, 3);
                        playerTableModel.setValueAt(selectedPlayer.getAssignedCompetitions(), i, 4);
                        break;
                    }
                }
                selectedEvent.addPlayer(selectedPlayer);
                DataManager.saveData();

                for (int i = 0; i < eventTableModel.getRowCount(); i++) {
                    if (eventTableModel.getValueAt(i, 1).equals(selectedEvent.getName())) {
                        eventTableModel.setValueAt(selectedEvent.getAttendeesNames(), i, 5); // column 5 = Attendants
                        break;
                    }
                }
                 cardLayout.show(cardPanelsContainer,"Players");
                DataManager.saveData();
                eventsComboBox.setSelectedIndex(0);
                playersComboBox.setSelectedIndex(0);
            }
        });
        assignEventsPanel.add(assignEventToPlayerButton);

        //button that show assign event panel



        //finally, create the person object
        JButton createPlayerButton = new JButton("Create Player");
        createPlayerButton.setMaximumSize(new Dimension(150,30));
        createPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (addPlayerNameInfoTextbox.getText().trim().isEmpty() ||
                        addPlayerNameInfoTextbox.getText().equals("ENTER PLAYER NAME")) {
                    JOptionPane.showMessageDialog(null, "Please enter a player name.");
                    return;
                }
                if (!addPlayerAgeInfoTextbox.getText().matches("\\d+")) {
                    JOptionPane.showMessageDialog(null, "Age must be a number.");
                    return;
                }
                if (playerGradeSelector.getSelectedIndex() == 0) {
                    JOptionPane.showMessageDialog(null, "Please select a grade level.");
                    return;
                }
                Player player = new Player(addPlayerNameInfoTextbox.getText(),
                        Integer.parseInt(addPlayerAgeInfoTextbox.getText()),
                        (String)playerGradeSelector.getSelectedItem());
                Main.allPlayersRostered.add(player);
                cardLayout.show(cardPanelsContainer,"Players");
                playerTableModel.addRow(new Object[]{
                        player.getName(),
                        player.getAge(),
                        player.getPlayerGradeLevel()
                });
                playersComboBox.removeAllItems();
                playersComboBox.addItem("Select Player");
                for(Player player0 : Main.allPlayersRostered){
                    playersComboBox.addItem(player0);
                }
                addPlayerNameInfoTextbox.setText("ENTER PLAYER NAME");
                addPlayerAgeInfoTextbox.setText("ENTER PLAYER AGE");
                playerGradeSelector.setSelectedIndex(0);
                DataManager.saveData();
            }
        });
        addPlayerPanel.add(createPlayerButton);

        //end of constructor
    }




    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
