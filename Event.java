public class Event {
    private String name;
    private String date;
    private String venue;
    private String type;
    private int capacity;
    private double fee;

    public Event(String name, String date, String venue, String type, int capacity, double fee) {
        this.name = name;
        this.date = date;
        this.venue = venue;
        this.type = type;
        this.capacity = capacity;
        this.fee = fee;
    }
}
