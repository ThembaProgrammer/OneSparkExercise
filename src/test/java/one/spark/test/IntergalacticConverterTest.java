package java.one.spark;

import one.spark.IntergalacticConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntergalacticConverterTest {
    final IntergalacticConverter converter = new IntergalacticConverter();

    @Test
    @DisplayName("Test intergalactic units to romans")
    @Order(1)
    public void setUp() {
        converter.processLine("prok is V");
        converter.processLine("pish is X");
        converter.processLine("tegj is L");

        assertAll(
                () -> assertEquals(converter.getIntergalacticToRoman().size(), 3),
                () -> assertEquals(converter.getIntergalacticToRoman().get("tegj"), "L"),
                () -> assertEquals(converter.getIntergalacticToRoman().get("pish"), "X"),
                () -> assertEquals(converter.getIntergalacticToRoman().get("prok"), "V")
        );
    }

    @Test
    @DisplayName("Test mineral resource credits")
    @Order(2)
    void testCredits(){
        converter.processLine("glob glob Silver is 34 Credits");
        converter.processLine("glob prok Gold is 57800 Credits");
        converter.processLine("pish pish Iron is 3910 Credits");

        assertAll(
                () -> assertEquals(converter.getUnitToCredit().size(), 3),
                () -> assertEquals(converter.getUnitToCredit().get("Silver"), 17.0),
                () -> assertEquals(converter.getUnitToCredit().get("Gold"), 14450),
                () -> assertEquals(converter.getUnitToCredit().get("Iron"), 3910)
        );
    }

    @Test
    @DisplayName("Test mineral resource credits")
    @Order(3)
    void testQueries(){
        assertAll(
                () -> assertEquals(converter.processLine("how much is pish tegj glob glob ?"), "pish tegj glob glob is 42"),
                () -> assertEquals(converter.processLine("how many Credits is glob prok Silver ?"), "glob prok Silver is 68 Credits"),
                () -> assertEquals(converter.processLine("how many Credits is glob prok Gold ?"), "glob prok Gold is 57800 Credits"),
                () -> assertEquals(converter.processLine("how many Credits is glob prok Iron ?"), "glob prok Iron is 782 Credits"),
                () -> assertEquals(converter.processLine("how much wood could a woodchuck chuck if a woodchuck could chuck wood?"), "I have no idea what you are talking about")
        );
    }
}
