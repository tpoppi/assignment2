////////////////////////////////////////////////////////////////////
// [TOMMASO] [POPPI] [1201270]
////////////////////////////////////////////////////////////////////

package it.unipd.tos.business;

import static org.junit.Assert.*;
import java.util.List;
import it.unipd.tos.business.TakeAwayBillImpl;
import it.unipd.tos.business.exception.RestaurantBillException;
import it.unipd.tos.model.MenuItem;
import it.unipd.tos.model.itemType;
import it.unipd.tos.model.User;
import java.time.LocalDate;
import java.util.ArrayList;
import java.time.*;

import org.junit.Test;


public class TakeAwayBillTest {
	
	@Test
    public void controllo_totale() {
        TakeAwayBillImpl bill = new TakeAwayBillImpl();
        User u = new User(1, "Armando", LocalDate.of(1998, 6, 11));
        List<MenuItem> ord = new ArrayList<MenuItem>();
        ord.add(new MenuItem(itemType.Budini, "Cioccolata", 5));
        ord.add(new MenuItem(itemType.Bevande, "Cioccolata", 5));
        try {
            assertEquals(bill.getOrderPrice(ord, u, LocalTime.of(18, 26)), 10, 0.0001);
        } catch (RestaurantBillException e) {
            fail();
        }
    }
	
	@Test
    public void sconto_piu_di_5_gelati() {
        TakeAwayBillImpl bill = new TakeAwayBillImpl();
        User u = new User(1, "Gianluca", LocalDate.of(1999, 6, 11));
        List<MenuItem> ord = new ArrayList<MenuItem>();
        for(int i=0;i<6;i++) {
            ord.add(new MenuItem(itemType.Gelati, "Pistacchio", 3));
        }
        ord.add(new MenuItem(itemType.Budini, "Cioccolata", 5));
        ord.add(new MenuItem(itemType.Bevande, "Cioccolata", 5));
        try {
            assertEquals(bill.getOrderPrice(ord, u, LocalTime.of(18, 26)), 26.5, 0.0001);
        } catch (RestaurantBillException e) {
            fail();
        }
    }
}
