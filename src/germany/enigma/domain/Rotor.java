package germany.enigma.domain;

import static germany.enigma.domain.RotorEventType.SHIFTED_LETTER;

public class Rotor implements RotorObserver {

    private static final byte ROTOR_SIZE = 26;
    private static final byte STEPPING_SIZE = 1;
    private static final char BASE_CHAR = 'A';
    /**
     * Represents what character contains the step mechanism.
     * Some versions of the Enigma Machine had more than one step char per Rotor,
     * so we implement this with an array
     */
    private char[] steppingCharacters;

    /**
     * Initial character represents the initial shifting.
     * Our machine is based on the letter capital A.
     * Meaning if the initialCharacter is C, we consider as a position shifted by 2
     */
    private char initialCharacter;


    /**
     * How many times this rotor was shifted, module 26
     */
    private int shiftCount;

    private Rotor previousRotor;

    /**
     * Enigma Machine to notify about rotor events
     */
    private EnigmaMachineSubject enigmaMachineSubject;

    public Rotor(char[] steppingCharacters, char initialCharacter, Rotor previousRotor, EnigmaMachineSubject enigmaMachineSubject) {
        this.steppingCharacters = steppingCharacters;
        this.initialCharacter = initialCharacter;
        this.previousRotor = previousRotor;
        this.enigmaMachineSubject = enigmaMachineSubject;
    }

    @Override
    public void observe(RotorEvent rotorEvent) {
        if (rotorEvent.getEventType() == SHIFTED_LETTER &&
                previousRotor.equals(rotorEvent.getRotor()))
            shift();
    }

    /**
     * It transforms a plain character into a ciphered one
     *
     * @param plain
     * @return
     */
    public char transformCharacter(char plain) {
        char transformed = (char) (((plain - BASE_CHAR + shiftCount) % ROTOR_SIZE) + BASE_CHAR);
        return transformed;
    }

    public char reverseCharacter(char transformed) {
        char reversed = (char) (((transformed + BASE_CHAR - shiftCount) % ROTOR_SIZE) + BASE_CHAR);
        return reversed;
    }

    /**
     * Shifts the rotor by STEPPING_SIZE positions
     */
    protected void shift() {
        shiftCount = (shiftCount + STEPPING_SIZE) % ROTOR_SIZE;
        emitShiftEvent();
    }

    private void emitShiftEvent() {
        if (shouldSendEvent()) {
            RotorEvent event = new RotorEvent(this, SHIFTED_LETTER);
            enigmaMachineSubject.notify(event);
        }
    }

    private boolean shouldSendEvent() {
        for (char steppingCharacter : steppingCharacters)
            if (steppingCharacter - BASE_CHAR == shiftCount)
                return true;
        return false;
    }
}
