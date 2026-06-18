# eSports-Team-Scheduling-Program
Designed for my school's eSports team to help achieve scheduling efficiency in collaboration with the coach. It uses 2 types of data, Players &amp; Events, and both can be created and assigned to one another. Then a table displays upcoming events by proximity of data and players by alphabetical order of their first name. **This is also my first time developing a full program from scratch that involves GUI, and therefore, this serves also as a learning project for me.**

**Basic Function Outlines:**
1. Add & remove players from the team
2. Add & remove events from the program
3. Assign players to events
4. Friendly and intuitive user interface
5. Displays the details of upcoming events and the participants
6. Saves registered information upon closing




**Startup instruction**
1. Download into IntelliJ
2. Add Gson & LGoodDatePicker as dependencies/libraries in project structure
4. Run the Main.java file




**Below are some of the components of the program:**



**GUI Elements**

  Graphical user interface(GUI) elements like buttons are a major part of my program. Most of my logic lives within the GUI class that extends Java Swing’s JFrame. The GUI is mainly composed of combinations of JPanels including three main pages–the home page, Events page, and the Players page. Because my GUI requires frequent page switches, a CardLayout(a Swing layout manager that treats JPanels like a deck of cards, allowing for only one JPanel to be displayed at a time) was implemented along with a CardLayoutContainer to store every JPanel(pages), identifiable by unique string keys, allowing for fast access of pages by calling CardLayout specific methods.
  The home page serves as the first element that the user will see when the program is launched. On it, are three buttons–the Events button, the Players button, and the Manual Save button–all of which have separate purposes when clicked. The Events page displays information about specific events using a JTable, a Java Swing display element with customizable rows and columns, with the table sorted from closest date to the furthest date. The Players page, similar to the Events Page’s layout, has a Home button and JTable to display information.
  <img width="1398" height="699" alt="Screenshot 2026-06-17 at 4 34 11 PM" src="https://github.com/user-attachments/assets/59aed86d-5f42-40be-8c40-eb1a22e7f0ce" />





















<img width="1398" height="699" alt="Screenshot 2026-06-17 at 4 34 53 PM" src="https://github.com/user-attachments/assets/66e453c9-52a8-4960-89d8-ffc33cb1464d" />





















<img width="1398" height="699" alt="Screenshot 2026-06-17 at 4 31 42 PM" src="https://github.com/user-attachments/assets/6071885d-5c0b-491f-8254-00440d410534" />

















<img width="1398" height="699" alt="Screenshot 2026-06-17 at 4 29 18 PM" src="https://github.com/user-attachments/assets/ed5e0fa8-9917-4cc1-a15c-6d00da698578" />


**Input Validation**

  Input validation is a preventative process to ensure properly formatted data enters a program. It is important to ensure that the inputs the users provided are correct. In my program’s GUI, there are input validation checks in the form of message dialogue pop ups to ensure that the data entered is intended and correct. If entered data is incorrect, the user must provide the correct form of data. Every element requiring user input has a validation check, including inputs required for creation of Event, Player, and assignment of Player to Event.

**ArrayLists**

  The main data structure used in my program is ArrayLists–a Java data structure that can store primitive data types and objects in a list style and stores elements in the order of addition by default. ArrayLists are the most appropriate data structure for a program as it can store Java Objects and allows for fast access of data. In my Main.java class, which launches the code, there are a total of four static ArrayLists to store all rostered Players, Events(both Practices & Competitions), and then Practices and Competitions. These ArrayLists are then accessed by the GUI and the JTable to display information like all events & players information.

**HashMaps** (I could have used ArrayLists here, but felt like using a HashMap, a new data structure to me, for fun!)

HashMap is a Java data structure that stores data in key-value pairs, where the unique key is used to retrieve the stored data. In my program, HashMaps were used in my Practice and Competition classes to store Player objects that are attending this Event, known as attendees. The names of Players were set as the String key, and the Player object as the value. HashMaps are useful for my program as they automatically handle duplicate keys so that a Player will not be able to be assigned to the same Event twice as the String key must be unique. The JTable’s attendee’s column is populated using a method that returns Player names stored in the HashMaps.

**Libraries**

In my project, a library called LGoodDatePicker was used to add a date and time picker in my GUI. The user can pick a precise date in month-day-time format that is then used as the date needed to create an Event(Practice/Competition) object. The picked date and time is compatible with Java’s LocalDateTime import, thus allowing me to compare the dates and determine the proximity difference for sorting.

**Data Persistence**

It is crucial to ensure that my program’s data persist over different launches. For this reason, I cannot rely on RAM memory. To tackle this issue, I created 3 classes–DataManager, EventAdapter, and LocalDateTimeAdapter–that allows me to serialize all of my program’s data to a string format called JSON. Then that data is stored in a file called data.json, which is then loaded whenever the program is launched again. Data is saved automatically(whenever objects are created/removed/assigned) and manually(via a button on the home page) during run time.



