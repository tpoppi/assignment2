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
	
	@Test
    public void sconto_piu_di_50_euro() {
        TakeAwayBillImpl bill = new TakeAwayBillImpl();
        User u = new User(1, "Tommaso", LocalDate.of(1999, 5, 3));
        List<MenuItem> ord = new ArrayList<MenuItem>();
        ord.add(new MenuItem(itemType.Budini, "Pistacchio", 25));
        ord.add(new MenuItem(itemType.Gelati, "Pistacchio", 25));
        ord.add(new MenuItem(itemType.Gelati, "Pistacchio", 11));
        try {
            assertEquals(bill.getOrderPrice(ord, u, LocalTime.of(18, 26)), 54.9, 0.0001);
        } catch (RestaurantBillException e) {
            fail();
        }
    }

    @Test
    public void sconto_piu_di_5_gelati_e_piu_di_50_euro() {
        TakeAwayBillImpl bill = new TakeAwayBillImpl();
        User u = new User(1, "Tommaso", LocalDate.of(1999, 5, 3));
        List<MenuItem> ord = new ArrayList<MenuItem>();
        for(int i=0;i<6;i++) {
            ord.add(new MenuItem(itemType.Gelati, "Pistacchio", 10));
        }
        try {
            assertEquals(bill.getOrderPrice(ord, u, LocalTime.of(18, 26)), 49.5, 0.0001);
        } catch (RestaurantBillException e) {
            fail();
        }
    }
    
    @Test(expected=RestaurantBillException.class)
    public void eccezione_piu_di_30_elementi() throws RestaurantBillException {
        TakeAwayBillImpl bill = new TakeAwayBillImpl();
        User u = new User(1, "Tommaso", LocalDate.of(1999, 5, 3));
        List<MenuItem> ord = new ArrayList<MenuItem>();
        for(int i=0;i<35;i++) {
            ord.add(new MenuItem(itemType.Gelati, "Pistacchio", 3));
        }
        bill.getOrderPrice(ord, u, LocalTime.of(18, 23));
    }
    
    @Test
    public void commissione_meno_di_10_euro() {
        TakeAwayBillImpl bill = new TakeAwayBillImpl();
        User u = new User(1, "Tommaso", LocalDate.of(1999, 5, 3));
        List<MenuItem> ord = new ArrayList<MenuItem>();
        for(int i=0;i<5;i++) {
            ord.add(new MenuItem(itemType.Gelati, "Pistacchio", 1));
        }
        try {
            assertEquals(bill.getOrderPrice(ord, u, LocalTime.of(18, 26)), 5.5, 0.0001);
        } catch (RestaurantBillException e) {
            fail();
        }
    }

    @Test
    public void sconto_piu_di_5_gelati_senza_sconto_piu_di_50_euro() {
        TakeAwayBillImpl bill = new TakeAwayBillImpl();
        User u = new User(1, "Tommaso", LocalDate.of(1999, 5, 3));
        List<MenuItem> ord = new ArrayList<MenuItem>();
        for(int i=0;i<4;i++) {
            ord.add(new MenuItem(itemType.Gelati, "Pistacchio", 10));
        }
        ord.add(new MenuItem(itemType.Gelati, "Pistacchio", 6));
        ord.add(new MenuItem(itemType.Gelati, "Pistacchio", 5));
        try {
            assertEquals(bill.getOrderPrice(ord, u, LocalTime.of(18, 26)), 48.5, 0.0001);
        } catch (RestaurantBillException e) {
            fail();
        }
    }

}
