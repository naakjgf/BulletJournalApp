[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/x6ckGcN8)
# 3500 PA05 Project Repo

[PA Write Up](https://markefontenot.notion.site/PA-05-8263d28a81a7473d8372c6579abd6481)

# Emileigh56789: Your Personalized Planner & Organizer

**Stay organized, manage your time efficiently, and visualize your week at a glance with the new Emileigh56789 app. Designed using the powerful JavaFX GUI, this digital bullet journal provides a streamlined and intuitive interface, neatly displaying your tasks and events horizontally for each day of the week.**

### Key Features:

- **Week View:** Experience a digital rendition of your classic bullet journal spread. Name your week and view all seven days displayed in a clean, uncluttered interface.

- **Task & Event Creation:** Craft detailed tasks and events.

- **Persistence:** Save your progress anytime with our efficient .bujo file format. When you open the app, load your previously saved bullet journal data and carry on right from where you left off. Your work is never lost with the 'Save to File' and 'Open File' features.

- **Commitment Warnings:** Keep a check on your schedule's intensity. Set a limit to the number of events and tasks per day and get helpful warnings when you exceed these maximums. Your set limits will also be saved in your .bujo file.

- **Task Queue:** Feeling overwhelmed with your weekly commitments? View all your tasks for the week in a convenient sidebar, allowing you to manage your to-dos effectively.

- **Menu Bar & Shortcuts:** Access important commands directly from the menu bar or use handy shortcuts for actions like creating new tasks or events, saving your work, and more.

- **Mini Viewer:** Open a detailed view of any single event or task in a mini window.

- **Weekly Overview:** Get a quick snapshot of your weekly productivity. View total events and tasks, and see your task completion progress at a glance.

- **Takesie-backsies:** Made a mistake? Delete tasks and events with ease, and witness real-time updates in the GUI.

- **Mind Changes:** Keep your plans flexible with the ability to edit any aspect of your existing events or tasks right from the Week view.

- **Week Tabs:** Run multiple weeks simultaneously, each in a separate tab, and switch between them as you wish.

- **Deployable Application:** Just run the .jar file and launch your personal Emileigh56789.

- **Splash Screen:** Be greeted with a warm welcome screen every time you launch the application.

- **Privacy Lock:** Secure your bullet journal entries with military grade encryption using password protection.

- **Weekly Starters:** Use a .bujo file as a template for a new week. Your past events and tasks won't be carried over, but your other preferences will.

# Screenshot
![Screenshot](https://github.com/CS-3500-OOD/pa05-emileigh56789/blob/main/screenshot.png?raw=true)

# SOLID Principles
### Single Responsibility
An example of single responsibility can be seen in the controllers section. Each of the controllers handles unique parts of the program that belong to a single responsibility. For example, the SettingsController handles the Settings GUI functionality, while MenuController handles creating the menu bar and managing menu bar action. And the JournalController handles the overarching actions which then delegate to the more specific controllers.

### Open/Closed Principle
One area where we do well to respect the Open/Closed Principle is in JournalControllerImpl. The class has defined several methods that handle different actions (like handleMenuAction, createNewJournal, createNewTask, createNewEvent, loadFile, etc.). Each method is focused on a specific functionality, so if you want to add new features or handle more cases, you can do so by extending the class or by adding more methods. This shows that the class is open for extension. The class is also partially closed for modification, because each method is doing a single job. For example, if a change is required in how new tasks are created, only the createNewTask method needs to be modified and the rest of the class remains unaffected.

### Liskov's Substitution
Liskov's substitution principle states that any reference to a subclass should be replaceable with its super class. This can be seen in any of the subclasses/superclasses in the project. We made a conscious effort to not override or change the implementation of a superclass functionality within any of the classes. For a specific example, the TaskEditingView extends the TaskManagerView, yet it does not override or change any of the implemented methods in the superclass, nor change the functionality of them.

### Interface Segregation
A great example of interface segregation can be seen in the model. The ScheduleItem interface provides general methods that are common to all schedule items. It only provides the methods that are common to all schedule items. Then there are two interfaces, EventInterface and TaskInterface, each extending ScheduleItem. EventInterface is specialized for events, with methods for getting and setting start times and durations. TaskInterface, on the other hand, is designed specifically for tasks, with methods for marking tasks as complete or incomplete. Lastly, the concrete classes Task and Event implement TaskInterface and EventInterface respectfully and therefore all methods from ScheduleItem. This architecture follows the Interface Segregation Principle well. Clients that are only interested in Tasks do not need to depend on the functionality specific to Events, and vice versa. Furthermore, because each interface is small and focused, it's easier to implement them without unnecessary complexity.

### Dependency Inversion
Dependency inversion states that high level components should not depend on low level components, both should depend on abstractions. An example of where you can see that in the program is with the ScheduleManager interface. The ScheduleManagerImpl class implements the ScheduleManager interface and its abstracted functionality. The JournalControllerImpl utilizes the ScheduleManager interface to abstract the functionality in the ScheduleManagerImpl when running functionality like createNewWeek() or saveData().

## How we could extend our program to add an additional, non-implemented Feature from above.
### Let's say for example we wanted to create a notification system for tasks and events.

**Single Responsibility**\
To maintain single responsibility, we will introduce a NotificationController dedicated to managing all operations around notifications, such as creating, scheduling, and displaying notifications. Functionality from this class can be called from the JournalControllerImpl

**Open/Closed Principle**\
NotificationController will possess methods like createNotification, scheduleNotification, and displayNotification. For future features like repeat notifications or snoozes, we can extend this class and add the necessary methods. Thus, it remains open for extension but closed for modification.

**Liskov's Substitution**\
We'll establish a Notification abstract class with a display method and create specific subclasses, like TaskNotification and EventNotification, each offering their version of display. This ensures that our specific notification objects are substitutable with their superclass, Notification.

**Interface Segregation**\
We'll create a NotificationInterface that outlines methods a Notification class should implement, such as schedule and cancel. Specific notification types like TaskNotification and EventNotification will implement this interface, ensuring clients only deal with relevant methods, adhering to Interface Segregation Principle.

**Dependency Inversion**\
Our NotificationController will depend on the abstraction NotificationInterface, not specific classes. It will use the schedule method defined in NotificationInterface, promoting decoupling and flexibility in our architecture.


# Instructions to run
1. Install dependencies
   1. You will need [JDK 17](https://openjdk.org/projects/jdk/17/)
   2. Download [JavaFX SDK](https://gluonhq.com/products/javafx/)
2. Download the [compiled run.jar file](https://github.com/CS-3500-OOD/pa05-emileigh56789/raw/main/run.jar)
3. Run the downloaded `run.jar` file using the following command (Replace module-path with SDK lib folder)
```bash
java --module-path /path/to/javafx-sdk-x.xx.x/lib --add-modules=javafx.controls,javafx.fxml -jar run.jar
```

Alternatively, you could also download the [MacOS binary from the releases page](https://github.com/CS-3500-OOD/pa05-emileigh56789/releases)

# Image Credits
[Most art used - Midjourney AI](https://midjourney.com/)

Splash Screen - Edited Fortnite Splash Screen

[Button press images - DeviantArt user Ashleigh98765](https://www.deviantart.com/ashleigh98765)