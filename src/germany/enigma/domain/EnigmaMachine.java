package germany.enigma.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class EnigmaMachine implements EnigmaMachineSubject {

    private List<Rotor> rotors;
    private List<KeyPressObserver> keyPressObservers;
    private List<RotorObserver> rotorObservers;
    private Reflector reflector;

    public void setup() {
        KeyboardRotorAware firstRotor = new KeyboardRotorAware(new char[]{'Z'}, 'A', null, this);
        Rotor secondRotor = new Rotor(new char[]{'Z'}, 'A', firstRotor, this);
        Rotor thirdRotor = new Rotor(new char[]{'Z'}, 'A', secondRotor, this);
        reflector = new Reflector();

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

            // Encrypts forwards
            for (Rotor rotor : rotors) {
                transformed = rotor.transformCharacter(transformed);
            }

            // Puts into Reflector
            transformed = reflector.reflect(transformed);

            // Encrypts backwards
            ListIterator<Rotor> rotorListIterator = rotors.listIterator(rotors.size());
            while (rotorListIterator.hasPrevious()) {
                Rotor rotor = rotorListIterator.previous();
                transformed = rotor.reverseCharacter(transformed);
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
