package model;
import java.util.ArrayList;

public class EventManager {
    private static EventManager instance;  // Singleton instance
    private final ArrayList<Event> eventList;

    public EventManager() {
        eventList = new ArrayList<>();
    }

    // Singleton getter
    public static EventManager getInstance() {
        if (instance == null) {
            instance = new EventManager();
        }
        return instance;
    }

    public void addEvent(Event event) {
        eventList.add(event);
    }

    public ArrayList<Event> getAllEvents() {
    return eventList;
    }

    public void updateEvent(int index, Event updatedEvent) {
        if (index >= 0 && index < eventList.size()) {
            eventList.set(index, updatedEvent);
        }
    }

    public void cancelEvent(int index) {
        if (index >= 0 && index < eventList.size()) {
            eventList.get(index).cancel();
        }
    }

    public void deleteEvent(int index) {
        if (index >= 0 && index < eventList.size()) {
            eventList.remove(index);
        }
    }
}
