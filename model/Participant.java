package model;
public class Participant {
    private final String name;
    private final String id;

    public Participant(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() { return name; }
    public String getId() { return id; }
}
