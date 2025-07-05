package model;

import java.util.ArrayList;

public class EventManager {
    private static EventManager instance;  // Singleton instance
    private final ArrayList<Event> eventList;

    public EventManager() {
        eventList = new ArrayList<>();
    }

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

    /**
     * Update event properties WITHOUT replacing the object reference,
     * so that Registration still points to the same event.
     */
    public void updateEvent(int index, Event updatedEvent) {
        if (index >= 0 && index < eventList.size()) {
            Event existing = eventList.get(index);
            existing.setName(updatedEvent.getName());
            existing.setDate(updatedEvent.getDate());
            existing.setVenue(updatedEvent.getVenue());
            existing.setType(updatedEvent.getType());
            existing.setCapacity(updatedEvent.getCapacity());
            existing.setFee(updatedEvent.getFee());
        }
    }

    public void cancelEvent(int index) {
        if (index >= 0 && index < eventList.size()) {
            eventList.get(index).cancel();
        }
    }

    
}
