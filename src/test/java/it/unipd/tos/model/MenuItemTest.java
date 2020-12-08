////////////////////////////////////////////////////////////////////
// [TOMMASO] [POPPI] [1201270]
////////////////////////////////////////////////////////////////////

package it.unipd.tos.model;

import static org.junit.Assert.*;
import it.unipd.tos.model.MenuItem;

import org.junit.Test;

public class MenuItemTest {

    @Test
    public void MenuItem_costructor_test() {
        itemType tipo = itemType.Gelati;
        String nome = "fragola";
        double prezzo = 2.1;
        MenuItem primo = new MenuItem(tipo, nome, prezzo);
        assertEquals(tipo, primo.getType());
        assertEquals(nome, primo.getName());
        assertEquals(prezzo, primo.getPrice(), 0.0001);
    }

}