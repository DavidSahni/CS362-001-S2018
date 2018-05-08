/** app JUnit test class to test the class ApptTest. */

package calendar;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import calendar.Appt;
import calendar.CalendarUtil;
import jdk.nashorn.internal.AssertsEnabled;
public class ApptTest  {

  private Appt app;

  
  @Before public void setup(){
    Appt a = new Appt(12, 30, 9, 1, 2018, "Test Appt", "This is app Test Appt", "xyz@gmail.com");
    app = a;
  }

  @Test(timeout = 4000)
  public void test00()  throws Throwable  {
      Appt appt0 = new Appt(15, 30, 9, 14, 2018, "Birthday Party", "This is my birthday party", "xyz@gmail.com");
      String string0 = appt0.toString();
      assertEquals(2, appt0.getRecurBy());//monthly!
      assertFalse(appt0.isRecurring());
      assertEquals("\t14/9/2018 at 3:30pm ,Birthday Party, This is my birthday party\n", string0);
      assertEquals(0, appt0.getRecurIncrement());
      assertEquals(null, appt0.getXmlElement());
  }
@Test(timeout = 4000)
 public void test01()  throws Throwable  {
  Appt appt0 = new Appt(9, 14, 2018, "Birthday Party", "This is my birthday party", "xyz@gmail.com");
  assertEquals(2, appt0.getRecurBy());//monthly!
  assertFalse(appt0.isRecurring());
  assertEquals(0, appt0.getRecurIncrement());
  assertEquals(false, appt0.hasTimeSet());
  assertEquals(-1, appt0.getStartMinute());
  assertEquals(-1, appt0.getStartHour()); 
}

@Test
public void testSetMonth(){
  app.setValid();
  assertEquals(true, app.getValid());
  app.setStartMonth(-12);
  app.setValid();
  assertEquals(false, app.getValid());
  app.setStartMonth(13);
  app.setValid();
  assertEquals(false, app.getValid());
  app.setStartMonth(12);
  app.setValid();
  assertEquals(true, app.getValid());
  app.setStartMonth(1);
  app.setValid();
  assertEquals(true, app.getValid());
}

@Test
public void testSetMinute(){
  app.setValid();
  assertEquals(true, app.getValid());
  app.setStartMinute(10000);
  app.setValid();
  assertEquals(false, app.getValid());
  app.setStartMinute(-1000);
  app.setValid();
  assertEquals(false, app.getValid());
  app.setStartMinute(59);
  app.setValid();
  assertEquals(true, app.getValid());
  app.setStartMinute(0);
  app.setValid();
  assertEquals(true, app.getValid());
}

@Test
public void testSetHour(){
  app.setValid();
  assertEquals(true, app.getValid());
  app.setStartHour(300);
  app.setValid();
  assertEquals(false, app.getValid());// bug found!
  app.setStartHour(-1200);
  app.setValid();
  assertEquals(false, app.getValid());// bug found!
  app.setStartHour(0);
  app.setValid();
  assertEquals(true, app.getValid());
  app.setStartHour(23);
  app.setValid();
  assertEquals(true, app.getValid());
}

@Test
public void testSetYear(){
  app.setValid();
  assertEquals(true, app.getValid());
  app.setStartYear(-2);
  app.setValid();
  assertEquals(false, app.getValid());
  app.setStartYear(0);
  app.setValid();
  assertEquals(false, app.getValid());
}

@Test
public void testSetDay(){
  app.setValid();
  assertEquals(true, app.getValid());
  app.setStartDay(32);
  app.setValid();
  assertEquals(false, app.getValid());
  app.setStartDay(31);
  app.setValid();
  assertEquals(true, app.getValid());
  app.setStartDay(1);
  app.setValid();
  assertEquals(true, app.getValid());
}


@Test(timeout=4000)
public void testSetValid(){
  app.setValid();
  assertEquals(true, app.getValid());
}



@Test(timeout=4000)
public void testSetters(){
  app.setTitle(null);
  assertEquals("", app.getTitle());
  app.setTitle("Test Title");
  assertEquals("Test Title", app.getTitle());  

  app.setDescription("");
  assertEquals("", app.getDescription());

  int[] x = null;
  app.setRecurrence(x, 2, 0, 0);
  assertEquals(2, app.getRecurBy());
  int[] y = new int[0];
  //assertEquals(y, app.getRecurDays()); breaks, bug found!
}

@Test(timeout=4000)
public void testIsOn(){
  int day = app.getStartDay();
  int m = app.getStartMonth();
  int y = app.getStartYear();
  assertEquals(true, app.isOn(day, m, y));
  assertEquals(false, app.isOn(day+1, m, y));
  assertEquals(false, app.isOn(day, m+1, y));
  assertEquals(false, app.isOn(day, m, y-1));
}
@Test
public void testEmail(){
  assertEquals("xyz@gmail.com", app.getEmailAddress());
  //app.setEmailAddress(null);
  Appt a = new Appt(12, 30, 9, 1, 2018, "Test Appt", "This is app Test Appt", null);
  assertEquals("", a.getEmailAddress());
}

}
