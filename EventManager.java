import java.util.ArrayList;

public class EventManager {
    private ArrayList<Event> eventList;

    public EventManager() {
        eventList = new ArrayList<>();
    }

    public void addEvent(Event event) {
        eventList.add(event);
    }

    public ArrayList<Event> getEvents() {
        return eventList;
    }

    public void updateEvent(int index, Event updatedEvent) {
        eventList.set(index, updatedEvent);
    }

    public void deleteEvent(int index) {
        eventList.remove(index);
    }
}
