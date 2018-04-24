
/** A JUnit test class to test the class DataHandler. */


package calendar;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import calendar.Appt;
import calendar.CalDay;
import calendar.DataHandler;


import java.util.*;


public class DataHandlerTest{


  private GregorianCalendar x1;
  private GregorianCalendar x2;

  @Before
  public void testSetup(){
    GregorianCalendar g1 = new GregorianCalendar(2018, 1, 10);
    GregorianCalendar g2 = new GregorianCalendar(2018, 1, 20);
    x1 = g1;
    x2 = g2;

  }


  @Test(timeout = 4000)
  public void test00()  throws Throwable  {
    DataHandler dh = new DataHandler();
    List<CalDay> appts = dh.getApptRange(x1, x2);
    assertEquals(10, appts.size());
  }
  @Test(timeout = 4000)
  public void test01()  throws Throwable  {
    Appt a = new Appt(15, 1, 2018, "Test Appt", "This is app Test Appt", "xyz@gmail.com");
    Appt b = new Appt(11, 1, 2018, "Test Appt 2", "Second Test Appt", "test@test.com");
    Appt c = new Appt(20, 2, 2018, "third test app", "3333", "tester2@test.com");
    Appt d = new Appt(35, 0, -1, "not valid appts!", "", "");

    int[] recurDaysArr={2,3,4};
    b.setRecurrence(recurDaysArr, Appt.RECUR_BY_WEEKLY, 2, 4);
    assert(b.isRecurring());
    d.setValid();
    
    DataHandler dh = new DataHandler(System.getProperty("user.dir") +
    System.getProperty("file.separator") + "testData.xml");
    assert(dh.saveAppt(a));
    assert(dh.saveAppt(b));
    assert(dh.saveAppt(c));
    assertEquals(false, dh.saveAppt(d));
    assert(dh.save());
    List<CalDay> appts = dh.getApptRange(x1, x2);
    assertEquals(10, appts.size());
    assert(dh.deleteAppt(a));
    assert(dh.deleteAppt(b));
    assert(dh.deleteAppt(c));
  }

}
