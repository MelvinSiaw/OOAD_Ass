package model;

public class Event {
    private String name;
    private String date;
    private String venue;
    private String type;
    private int capacity;
    private double fee;
    private boolean cancelled;


    public Event(String name, String date, String venue, String type, int capacity, double fee) {
        this.name = name;
        this.date = date;
        this.venue = venue;
        this.type = type;
        this.capacity = capacity;
        this.fee = fee;
        this.cancelled = false;

    }

    public void cancel() {
        this.cancelled = true;
    }

    // getters
    public String getName() { return name; }
    public String getDate() { return date; }
    public String getVenue() { return venue; }
    public String getType() { return type; }
    public int getCapacity() { return capacity; }
    public double getFee() { return fee; }
    public boolean isCancelled() { return cancelled; }

    // setters
    public void setName(String name) { this.name = name; }
    public void setDate(String date) { this.date = date; }
    public void setVenue(String venue) { this.venue = venue; }
    public void setType(String type) { this.type = type; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public void setFee(double fee) { this.fee = fee; }

    @Override
    public String toString() {
    String status = cancelled ? " [CANCELLED]" : "";
    return name + " (" + date + ", " + venue + ", " + type + ", RM" + fee + ")" + status;
}

}
