package germany.enigma.domain;

import java.util.ArrayList;
import java.util.List;

public class EnigmaMachine implements EnigmaMachineSubject {

    private List<Rotor> rotors;
    private List<KeyPressObserver> keyPressObservers;
    private List<RotorObserver> rotorObservers;


    public void setup() {
        KeyboardRotorAware firstRotor = new KeyboardRotorAware(new char[]{'Z'}, 'A', null, this);
        Rotor secondRotor = new Rotor(new char[]{'Z'}, 'A', firstRotor, this);
        Rotor thirdRotor = new Rotor(new char[]{'Z'}, 'A', secondRotor, this);

        rotors = new ArrayList<>();
        keyPressObservers = new ArrayList<>();
        rotorObservers = new ArrayList<>();

        rotors.add(firstRotor);
        rotors.add(secondRotor);
        rotors.add(thirdRotor);

        keyPressObservers.add(firstRotor);

        rotorObservers.add(secondRotor);
        rotorObservers.add(thirdRotor);
    }

    public String encryptMessage(String message) {
        StringBuilder cipheredMessage = new StringBuilder();
        for (char c : message.toCharArray()) {
            dispatchKeyPressEvents(c);
            char transformed = c;
            for (Rotor rotor : rotors) {
                transformed = rotor.transformCharacter(transformed);
            }
            cipheredMessage.append(transformed);
        }
        return cipheredMessage.toString();
    }

    private void dispatchRotorEvents(RotorEvent rotorEvent) {
        rotorObservers.forEach(observer -> observer.observe(rotorEvent));
    }

    private void dispatchKeyPressEvents(char key) {
        keyPressObservers.forEach(observer -> observer.observe(key));
    }

    @Override
    public void notify(EnigmaMachineEvent enigmaMachineEvent) {
        if (enigmaMachineEvent instanceof RotorEvent)
            dispatchRotorEvents((RotorEvent) enigmaMachineEvent);
    }
}
