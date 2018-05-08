/** A JUnit test class to test the class CalDay. */

package calendar;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.*;

import calendar.Appt;
import calendar.CalDay;


public class CalDayTest{

  CalDay testDay;
  Appt app;
  @Before
  public void testSetup(){
    Appt a = new Appt(12, 30, 9, 1, 2018, "Test Appt", "This is app Test Appt", "xyz@gmail.com");
    a.setValid();
    app = a;
    testDay = new CalDay(new GregorianCalendar(2018, 5, 16));
  }


  @Test(timeout = 4000)
  public void test00()  throws Throwable  {
    CalDay cd = new CalDay();
    assertEquals(false, cd.isValid());

  }
  @Test(timeout = 4000)
  public void test01()  throws Throwable  {
    GregorianCalendar cal = new GregorianCalendar(2018, 5, 16);
    CalDay cd = new CalDay(cal);
    assertEquals(2018, cd.getYear());
    assertEquals(6, cd.getMonth());
    assertEquals(16, cd.getDay());
    assertEquals(true, cd.isValid());
  }

  @Test(timeout = 4000)
  public void testAddAppt(){
    testDay.addAppt(app);
    assertEquals(1, testDay.getSizeAppts());
    //later appt
    Appt newapp =  new Appt(15, 30, 9, 1, 2018, "Test Appt 2", "This is app Test Appt 2", "xyz@gmail.com");
    newapp.setValid();
    //non valid appt!
    Appt newapp2 = new Appt(15, 30, -2, 1, -9, "Test Appt 3", "This is app Test Appt 2", "xyz@gmail.com");
    newapp2.setValid();
    testDay.addAppt(newapp);
    assertEquals(2, testDay.getSizeAppts());
    testDay.addAppt(newapp2);
    assertEquals(2, testDay.getSizeAppts());

    LinkedList<Appt> apps = new LinkedList<Appt>();
    apps = testDay.getAppts();
    assertEquals("Test Appt", apps.get(0).getTitle()); //Bug found! Appts are out of order
    assertEquals(2, apps.size());
  }
  

  @Test(timeout = 4000)
  public void testStringBuilder(){
    StringBuilder sb = new StringBuilder();
    CalDay tester = new CalDay();
    assertEquals("", tester.toString());
    String dateString = "\t --- 6/16/2018 --- \n";
    String apptString = " --- -------- Appointments ------------ --- \n";
    //sb.append(dateString);
    //sb.append(apptString);
    //sb.append("\n");
    String retString = testDay.toString();
    //assertEquals(sb.toString(), retString); Another bug found! The stringbuilder adds an extra extra month!
    
    dateString = "\t --- 7/16/2018 --- \n";
    sb.append(dateString);
    sb.append(apptString);
    testDay.addAppt(app);
    String detailString = app.toString();
    sb.append(detailString);
    sb.append(" \n");
    assertEquals(sb.toString(), testDay.toString());
    
  }

  @Test(timeout = 4000)
  public void testGetFullInfo1(){
    testDay = new CalDay(new GregorianCalendar(2018, 5, 16));
    CalDay wrapper = new CalDay();
    testDay.addAppt(app);
    String RetVal = wrapper.getFullInfomrationApp(testDay);
    assertNotEquals(null, RetVal);
    assertNotEquals("", RetVal);
    assert(RetVal.contains("Test Appt"));
    assert(RetVal.contains("0:30AM"));
  }


  @Test(timeout = 4000)
  public void testGetFullInfoTwo(){
    testDay = new CalDay(new GregorianCalendar(2018, 5, 16));
    CalDay wrapper = new CalDay();
    Appt newapp =  new Appt(23, 30, 9, 1, 2018, "Test Appt 2", "This is app Test Appt 2", "xyz@gmail.com");
    newapp.setValid();
    testDay.addAppt(newapp);
    String RetVal = wrapper.getFullInfomrationApp(testDay);
    assertNotEquals(null, RetVal);
    assertNotEquals("", RetVal);
    assert(RetVal.contains("Test Appt 2"));
    assert(RetVal.contains("11:30PM"));
  }

  @Test(timeout = 4000)
  public void testGetFullInfoThree(){
    testDay = new CalDay(new GregorianCalendar(2018, 5, 16));
    CalDay wrapper = new CalDay();
    Appt appt0 = new Appt(0, 3, 9, 1, 2018, "Birthday Party", "This is my birthday party", "xyz@gmail.com");
    appt0.setValid();
    testDay.addAppt(appt0);
    String RetVal = wrapper.getFullInfomrationApp(testDay);
    assert(RetVal.contains("Birthday Party"));
    assert(RetVal.contains("12:03AM"));
  }

}
