////////////////////////////////////////////////////////////////////
// [TOMMASO] [POPPI] [1201270]
////////////////////////////////////////////////////////////////////

package it.unipd.tos.model;

import static org.junit.Assert.*;
import java.time.LocalDate;
import org.junit.Test;

import it.unipd.tos.model.User;

public class UserTest {
    @Test
    public void user_costructor_test() {
        int id = 1;
        String nome = "Antonio";
        LocalDate n = LocalDate.of(1999, 5, 3);
        User antonio = new User(id, nome, n);
        assertEquals(id, antonio.getId());
        assertEquals(nome, antonio.getName());
        assertEquals(n, antonio.getDate());
    }
}