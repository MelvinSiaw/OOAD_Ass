package model;

public class Registration {
    private final Participant participant;
    private final Event event;
    private final boolean catering;
    private final boolean transportation;
    private final int groupSize;

    public Registration(Participant participant, Event event, boolean catering, boolean transportation, int groupSize) {
        this.participant = participant;
        this.event = event;
        this.catering = catering;
        this.transportation = transportation;
        this.groupSize = groupSize;
    }

    public Participant getParticipant() { return participant; }
    public Event getEvent() { return event; }
    public boolean hasCatering() { return catering; }
    public boolean hasTransportation() { return transportation; }
    public int getGroupSize() { return groupSize; }


    @Override
    public String toString() {
        return participant.getName()
            + " (Group size: " + groupSize + ") registered for "
            + event.getName();
    }
}
