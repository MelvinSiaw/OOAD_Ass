package model;
import java.util.ArrayList;

public class RegistrationManager {
    private static RegistrationManager instance;
    private final ArrayList<Registration> registrations;

    private RegistrationManager() {
        registrations = new ArrayList<>();
    }

    public static RegistrationManager getInstance() {
        if (instance == null) {
            instance = new RegistrationManager();
        }
        return instance;
    }

    public void addRegistration(Registration reg) {
        registrations.add(reg);
    }

    public ArrayList<Registration> getAllRegistrations() {
        return registrations;
    }
}
