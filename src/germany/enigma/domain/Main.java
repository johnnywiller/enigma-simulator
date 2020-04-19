package germany.enigma.domain;

public class Main {

    public static void main(String[] args) {
        EnigmaMachine enigmaMachine = new EnigmaMachine();

        enigmaMachine.setup();

        System.out.println(enigmaMachine.encryptMessage("HELLOWORLDS"));
    }
}
