package germany.enigma.domain;

/**
 * A rotor that has the ability of shifting by key strokes
 */
public class KeyboardRotorAware extends Rotor implements KeyPressObserver {

    public KeyboardRotorAware(char[] steppingCharacters, char initialCharacter, Rotor previousRotor, EnigmaMachineSubject enigmaMachineSubject) {
        super(steppingCharacters, initialCharacter, previousRotor, enigmaMachineSubject);
    }

    @Override
    public void observe(char pressedKey) {
        this.shift();
    }
}
