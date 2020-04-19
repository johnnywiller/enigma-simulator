package germany.enigma.domain;

import java.util.Map;

import static java.util.Map.entry;

public class Reflector {

    private Map<Character, Character> reflections = Map.ofEntries(
            entry('A', 'B'),
            entry('B', 'A'),
            entry('C', 'D'),
            entry('D', 'C'),
            entry('E', 'F'),
            entry('F', 'E'),
            entry('G', 'H'),
            entry('H', 'G'),
            entry('I', 'J'),
            entry('J', 'I'),
            entry('K', 'L'),
            entry('L', 'K'),
            entry('M', 'N'),
            entry('N', 'M'),
            entry('O', 'P'),
            entry('P', 'O'),
            entry('Q', 'R'),
            entry('R', 'Q'),
            entry('S', 'T'),
            entry('T', 'S'),
            entry('U', 'V'),
            entry('V', 'U'),
            entry('W', 'X'),
            entry('X', 'W'),
            entry('Y', 'Z'),
            entry('Z', 'Y')
    );

    public char reflect(char c) {
        return reflections.get(c);
    }
}
