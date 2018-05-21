package calendar;


import org.junit.Test;
import java.util.*;

import static org.junit.Assert.*;



/**
 * Random Test Generator  for CalDay class.
 */

public class CalDayRandomTest {
	
    /**
     * Generate Random Tests that tests CalDay Class.
     */

    private static final long TestTimeout = 10 * 1000 * 1; /* Timeout at 30 seconds */
	private static final int NUM_TESTS=100;

    private Appt genAppt(Random random, Boolean invalid){

        String title="Birthday Party";
        String description="This is my birthday party.";
        String emailAddress="xyz@gmail.com";        
        int startHour=ValuesGenerator.getRandomIntBetween(random, 1, 11);
        int startMinute=ValuesGenerator.getRandomIntBetween(random, 1, 11);
        int startDay=ValuesGenerator.getRandomIntBetween(random, 1, 11);
        if (invalid){
            startDay = -1;
        }
        int startMonth=ValuesGenerator.getRandomIntBetween(random, 1, 11);
        int startYear=ValuesGenerator.getRandomIntBetween(random, 2018, 2019);
        //System.out.format("%d %d %d %d %d \n", startHour, startMinute, startDay, startMonth, startYear);
        Appt appt = new Appt(startHour,
        startMinute ,
        startDay ,
        startMonth ,
        startYear ,
        title,
        description,
        emailAddress);

        appt.setValid();
        return appt;
    }

    @Test
    public void randomtest()  throws Throwable  {
        
		long startTime = Calendar.getInstance().getTimeInMillis();
        long elapsed = Calendar.getInstance().getTimeInMillis() - startTime;
         

        try{
            for (int iteration = 0; elapsed < TestTimeout; iteration++) {
                long randomseed =System.currentTimeMillis(); //10
                //			System.out.println(" Seed:"+randomseed );
                Random random = new Random(randomseed);

                GregorianCalendar cal = ValuesGenerator.generateRandomCalendar(random);

                Appt x1 = genAppt(random, false);
                Appt x2 = genAppt(random, false);
                Appt x3 = genAppt(random, true);
                Appt x4 = genAppt(random, false);

                CalDay calDay = new CalDay(cal);
                calDay.addAppt(x1);
                calDay.addAppt(x2);
                calDay.addAppt(x3);
                calDay.addAppt(x4);

                LinkedList<Appt> appts = calDay.getAppts();
                assertEquals(3, appts.size());

                elapsed = (Calendar.getInstance().getTimeInMillis() - startTime);
                if((iteration%10000)==0 && iteration!=0 ){
                     // System.out.println("elapsed time: "+ elapsed + " of "+TestTimeout);
                }
            }
        }catch(NullPointerException e){

        }


	

    }
}