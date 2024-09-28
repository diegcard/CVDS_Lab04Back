package edu.eci.cvds.pattens;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class Lab04BackApplicationTests {

    @Test
    public void applicationStartsSuccessfully() {
        Lab04BackApplication.main(new String[] {});
    }

    @Test
    public void applicationFailsToStartWithNullArgs() {
        assertThrows(IllegalArgumentException.class, () -> Lab04BackApplication.main(null));
    }

}
