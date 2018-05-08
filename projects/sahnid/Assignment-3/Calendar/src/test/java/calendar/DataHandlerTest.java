
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
    GregorianCalendar g2 = new GregorianCalendar(2018, 1, 17);
    x1 = g1;
    x2 = g2;
  }


  @Test(timeout = 4000)
  public void test00()  throws Throwable  {
    DataHandler dh = new DataHandler();
    List<CalDay> appts = dh.getApptRange(x1, x2);
    assertEquals(7, appts.size());
  }

  @Test
  public void test01()  throws Throwable  {
    Appt a = new Appt(15, 1, 2018, "Test Appt", "This is app Test Appt", "xyz@gmail.com");
    DataHandler dh = new DataHandler(System.getProperty("user.dir") +
    System.getProperty("file.separator") + "testData1.xml");
    assert(a.getValid());
    assert(dh.saveAppt(a));
    assert(dh.save());
    List<CalDay> appts = dh.getApptRange(x1, x2);
    assertEquals(7, appts.size());
    assert(dh.deleteAppt(a));
  }

  @Test
  public void test02() throws Throwable{
    Appt b = new Appt(11, 1, 2018, "Test Appt 2", "Second Test Appt", "test@test.com");

    int[] recurDaysArr={2,3,4};
    b.setRecurrence(recurDaysArr, Appt.RECUR_BY_WEEKLY, 2, 4);
    assert(b.isRecurring());
    DataHandler dh = new DataHandler(System.getProperty("user.dir") +
    System.getProperty("file.separator") + "testData2.xml");
    assert(dh.saveAppt(b));
    assert(dh.save());
    List<CalDay> appts = dh.getApptRange(x1, x2);
    assertEquals(7, appts.size());
    assert(dh.deleteAppt(b));

  }

  @Test
  public void test03() throws Throwable{
    Appt c = new Appt(17, 1, 2018, "third test app", "3333", "tester2@test.com");
    DataHandler dh = new DataHandler(System.getProperty("user.dir") +
    System.getProperty("file.separator") + "testData3.xml");
    assert(dh.saveAppt(c));
    c.setRecurrence(new int[0], Appt.RECUR_BY_MONTHLY, 2, 4);

    //assert(dh.save());
    assert(dh.deleteAppt(c));

  }

  @Test
  public void test04() throws Throwable{
    Appt d = new Appt(35, 0, -1, "not valid appts!", "", "");
    d.setValid();
    DataHandler dh = new DataHandler(System.getProperty("user.dir") +
    System.getProperty("file.separator") + "testData4.xml");

    assertEquals(false, dh.saveAppt(d));
    assertEquals(false, dh.deleteAppt(d));

  }

}
