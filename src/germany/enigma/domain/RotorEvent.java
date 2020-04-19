package germany.enigma.domain;

public class RotorEvent extends EnigmaMachineEvent {

    private Rotor rotor;
    private RotorEventType eventType;

    public RotorEvent(Rotor rotor, RotorEventType eventType) {
        this.rotor = rotor;
        this.eventType = eventType;
    }

    public Rotor getRotor() {
        return rotor;
    }

    public RotorEventType getEventType() {
        return eventType;
    }
}
