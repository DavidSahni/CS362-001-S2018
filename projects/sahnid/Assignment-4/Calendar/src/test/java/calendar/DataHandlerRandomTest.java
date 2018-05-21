package calendar;

import java.util.*;

import org.junit.Test;


import static org.junit.Assert.*;




/**
 * Random Test Generator  for DataHandler class.
 */

public class DataHandlerRandomTest {
    private static final long TestTimeout = 30 * 1000 * 1; /* Timeout at 30 seconds */
    private static final int NUM_TESTS=100;
    

    private String RandomSelectMethod(Random random){
        String[] methodArray = new String[] {"deleteAppt", "getApptRange"};// The list of the of methods to be tested in the Appt class
    
        int n = random.nextInt(methodArray.length);// get a random number between 0 (inclusive) and  methodArray.length (exclusive)
                    
        return methodArray[n] ; // return the method name 
        }
    

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
    /**
     * Generate Random Tests that tests DataHandler Class.
     */
	 @Test
	  public void randomTest()  throws Throwable  {
		         
		long startTime = Calendar.getInstance().getTimeInMillis();
        long elapsed = Calendar.getInstance().getTimeInMillis() - startTime;
         

        try{
            for (int iteration = 0; elapsed < TestTimeout; iteration++) {
                long randomseed = System.currentTimeMillis(); //10
                //			System.out.println(" Seed:"+randomseed );
                Random random = new Random(randomseed);
                
                Appt x1 = genAppt(random, false);
                Appt x2 = genAppt(random, false);
                int sizeArray=ValuesGenerator.getRandomIntBetween(random, 0, 8);
                int[] recurDays=ValuesGenerator.generateRandomArray(random, sizeArray);
                if (random.nextInt(25) == 0){
                    recurDays = null;
                }
                int recur=ApptRandomTest.RandomSelectRecur(random);
                int recurIncrement = ValuesGenerator.RandInt(random);
                int recurNumber=ApptRandomTest.RandomSelectRecurForEverNever(random);
                x2.setRecurrence(recurDays, recur, recurIncrement, recurNumber);

                Appt x3 = genAppt(random, false);
                Appt x4 = genAppt(random, true);
                DataHandler dh = new DataHandler("testData.xml");
                dh.saveAppt(x1);
                dh.saveAppt(x2);
                dh.saveAppt(x3);
                dh.saveAppt(x4);
                String methodName = RandomSelectMethod(random);
                if (methodName.equals("getApptRange")){
                    int month = x1.getStartMonth();
                    int day = ValuesGenerator.getRandomIntBetween(random, 1, 11);
                    int year = x1.getStartYear();
                    GregorianCalendar gc1 = new GregorianCalendar(year, month, day);
                    int addDays = ValuesGenerator.getRandomIntBetween(random, 1, 11);
                    if (ValuesGenerator.getRandomIntBetween(random, 1, 8) == 1){
                        addDays = -addDays;
                    }
                    GregorianCalendar gc2 = new GregorianCalendar(year, month, day+addDays);
                    try{
                        LinkedList<CalDay> calDays = dh.getApptRange(gc1, gc2);
                        assertEquals(addDays, calDays.size());
                        if (addDays > 0 && day < x1.getStartDay() && x1.getStartDay() < day + addDays){//at least one in the range
                            Boolean correct = false;
                            Iterator<CalDay> itr = calDays.listIterator(0);
                            //System.out.format("First Day: %d  Last Day: %d  x1: %d\n", day, day + addDays, x1.getStartDay());
                            while (itr.hasNext()){
                                CalDay cal = itr.next();
                                LinkedList<Appt> apps = cal.getAppts();
                                if (apps.size() == 0) { }
                                else{
                                    correct = true; //should be at least one app in the days
                                }
                            }
                            //assert(correct);
                        }
                    }catch(DateOutOfRangeException e){
                        System.out.println("found dates out of range");
                    }
                }else if(methodName.equals("deleteAppt")){
                    DataHandler ph = new DataHandler("testData.xml", false);
                    x2.setXmlElement(null);
                    ph.deleteAppt(x2);
                    ph.deleteAppt(x1);
                    dh.deleteAppt(x1);
                    dh.deleteAppt(x2);
                    dh.deleteAppt(x3);
                    dh.deleteAppt(x4);
                }
		 
                elapsed = (Calendar.getInstance().getTimeInMillis() - startTime);
                    System.out.println("elapsed time: "+ elapsed + " of "+TestTimeout);
            }
        }catch(NullPointerException e){
            System.out.println("null ptr exception" + e);
                }
    }


	
}
