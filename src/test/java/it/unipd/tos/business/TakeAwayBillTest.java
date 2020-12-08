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
    
    @Test
    public void scontoMinorenni() {
        TakeAwayBillImpl bill = new TakeAwayBillImpl();
        User u = new User(1, "Tommaso", LocalDate.of(2004, 5, 3));
        List<MenuItem> ord = new ArrayList<MenuItem>();
        for(int i=0;i<5;i++) {
            ord.add(new MenuItem(itemType.Gelati, "Pistacchio", 10));
        }
        try {
            // sconto minorenni accettato
            assertEquals(bill.getOrderPrice(ord, u, LocalTime.of(18, 26, 24)), 0.00, 0.0001);
            // sconto minorenni rifiutato
            assertEquals(bill.getOrderPrice(ord, u, LocalTime.of(18, 26, 25)), 50, 0.0001);

        } catch (RestaurantBillException e) {
            fail();
        }
    }

    @Test
    public void max_numero_scontoMinorenni() {
        TakeAwayBillImpl bill = new TakeAwayBillImpl();
        List<User> utenti = new ArrayList<User>();
        for (int i = 0; i < 13; i++) {
            utenti.add(new User(i, "Tommaso", LocalDate.of(2004, 5, 3)));
        }
        utenti.add(new User(13, "Tommaso", LocalDate.of(1999, 5, 3)));
        utenti.add(new User(14, "Franco", LocalDate.of(2008, 6, 8)));

        List<MenuItem> ord = new ArrayList<MenuItem>();
        for(int i=0;i<5;i++) {
            ord.add(new MenuItem(itemType.Gelati, "Pistacchio", 10));
        }
        try {
            //maggiorenni a cui non si puo applicare lo sconto
            assertEquals(bill.getOrderPrice(ord, utenti.get(13), LocalTime.of(18, 26, 24)), 50, 0.0001);
            assertEquals(bill.getOrderPrice(ord, utenti.get(13), LocalTime.of(16, 26, 24)), 50, 0.0001);
            assertEquals(bill.getOrderPrice(ord, utenti.get(13), LocalTime.of(20, 26, 24)), 50, 0.0001);
            //minorenni a cui non vengono applicati gli sconti
            assertEquals(bill.getOrderPrice(ord, utenti.get(14), LocalTime.of(17, 26, 24)), 50, 0.0001);
            assertEquals(bill.getOrderPrice(ord, utenti.get(14), LocalTime.of(21, 26, 24)), 50, 0.0001);
            //minorenni a cui vengono applicati gli sconti
            assertEquals(bill.getOrderPrice(ord, utenti.get(0), LocalTime.of(18, 26, 24)), 0.00, 0.0001);
            for (int i = 0; i < 10; i++) {
                if (i == 0) {
                    assertEquals(bill.getOrderPrice(ord, utenti.get(i), LocalTime.of(18, 26, 24)), 50, 0.0001);
                } else {
                    assertEquals(bill.getOrderPrice(ord, utenti.get(i), LocalTime.of(18, 26, 24)), 0.00, 0.0001);
                }
            }
            //maggiorenni che non vengono scontati
            assertEquals(bill.getOrderPrice(ord, utenti.get(10), LocalTime.of(18, 26, 25)), 50, 0.0001);
            assertEquals(bill.getOrderPrice(ord, utenti.get(11), LocalTime.of(18, 26, 29)), 50, 0.0001);
            // Minorenni che non vengono scontati perchè abbiamo raggiunto il massimo degli sconti
            assertEquals(bill.getOrderPrice(ord, utenti.get(12), LocalTime.of(18, 26, 24)), 50, 0.0001);

        } catch (RestaurantBillException e) {
            fail();
        }
    }


}
