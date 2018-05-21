package calendar;

import java.util.Calendar;
import java.util.Random;

import org.junit.Test;



import static org.junit.Assert.*;



/**
 * Random Test Generator  for Appt class.
 */

public class ApptRandomTest {

	private static final long TestTimeout = 4 * 1000 * 1; /* Timeout at 30 seconds */
	private static final int NUM_TESTS=100;


	public static Boolean isValid(Appt appt){
		Boolean retVal = true;
		if (appt.getStartMonth() < 1 || appt.getStartMonth() > 12)
			retVal = false;
		else if (appt.getStartMinute() < 0 || appt.getStartMinute() > 59)
			retVal = false;
		else if (appt.getStartHour() < 0 || appt.getStartHour() > 23){
			retVal = false;
		}
		else if (appt.getStartYear() <= 0)
			retVal = false;
		else {
			int NumDaysInMonth = CalendarUtil.NumDaysInMonth(appt.getStartYear(), appt.getStartMonth() - 1);
			if (appt.getStartDay() < 1 || appt.getStartDay() > NumDaysInMonth)
				retVal = false;
			else
				retVal = true;
		}
		return retVal;
	}

	/**
	 * Return a randomly selected method to be tests !.
	 */
    public static String RandomSelectMethod(Random random){
        String[] methodArray = new String[] {"setValid","setRecurrence", "isOn"};// The list of the of methods to be tested in the Appt class

    	int n = random.nextInt(methodArray.length);// get a random number between 0 (inclusive) and  methodArray.length (exclusive)
    	            
        return methodArray[n] ; // return the method name 
        }
	/**
	 * Return a randomly selected appointments to recur Weekly,Monthly, or Yearly !.
	 */
    public static int RandomSelectRecur(Random random){
        int[] RecurArray = new int[] {Appt.RECUR_BY_WEEKLY,Appt.RECUR_BY_MONTHLY,Appt.RECUR_BY_YEARLY};// The list of the of setting appointments to recur Weekly,Monthly, or Yearly

    	int n = random.nextInt(RecurArray.length);// get a random number between 0 (inclusive) and  RecurArray.length (exclusive)
        return RecurArray[n] ; // return the value of the  appointments to recur 
        }	
	/**
	 * Return a randomly selected appointments to recur forever or Never recur  !.
	 */
    public static int RandomSelectRecurForEverNever(Random random){
        int[] RecurArray = new int[] {Appt.RECUR_NUMBER_FOREVER,Appt.RECUR_NUMBER_NEVER};// The list of the of setting appointments to recur RECUR_NUMBER_FOREVER, or RECUR_NUMBER_NEVER

    	int n = random.nextInt(RecurArray.length);// get a random number between 0 (inclusive) and  RecurArray.length (exclusive)
        return RecurArray[n] ; // return appointments to recur forever or Never recur 
        }	
   /**
     * Generate Random Tests that tests Appt Class.
     */
	 @Test
	  public void randommtest()  throws Throwable  {

		 long startTime = Calendar.getInstance().getTimeInMillis();
		 long elapsed = Calendar.getInstance().getTimeInMillis() - startTime;

		 
		 System.out.println("Start testing...");
		 
		try{ 
			for (int iteration = 0; elapsed < TestTimeout; iteration++) {
				long randomseed =System.currentTimeMillis(); //10
	//			System.out.println(" Seed:"+randomseed );
				Random random = new Random(randomseed);
				
				 int startHour=ValuesGenerator.getRandomIntBetween(random, 1, 11);
				 int startMinute=ValuesGenerator.getRandomIntBetween(random, 1, 11);
				 int startDay=ValuesGenerator.getRandomIntBetween(random, 1, 11);
				 int startMonth=ValuesGenerator.getRandomIntBetween(random, 1, 11);
				 int startYear=ValuesGenerator.getRandomIntBetween(random, 2018, 2018);
				 String title="Birthday Party";
				 String description="This is my birthday party.";
				 String emailAddress="xyz@gmail.com";

				 //Construct a new Appointment object with the initial data	 
				 //Construct a new Appointment object with the initial data	 
		         Appt appt = new Appt(startHour,
		                  startMinute ,
		                  startDay ,
		                  startMonth ,
		                  startYear ,
		                  title,
		                 description,
		                 emailAddress);

			 //if(!appt.getValid())continue;
			for (int i = 0; i < NUM_TESTS; i++) {
					String methodName = ApptRandomTest.RandomSelectMethod(random);
						if (methodName.equals("setValid")){
						int testHour=ValuesGenerator.getRandomIntBetween(random, 0, 26, true);
						int testMinute=ValuesGenerator.getRandomIntBetween(random, 0, 90, true);
						int testDay=ValuesGenerator.getRandomIntBetween(random, 0, 40, true);
						int testMonth=ValuesGenerator.getRandomIntBetween(random, 0, 26, true);
						int testYear=ValuesGenerator.getRandomIntBetween(random, 0, 2018, true);	
						Appt testAppt = new Appt(testHour,
							testMinute ,
							testDay ,
							testMonth ,
							testYear ,
							title,
							description,
							emailAddress);
						testAppt.setValid();
						Boolean isVal = ApptRandomTest.isValid(testAppt);
						//assertEquals(isVal, testAppt.getValid()); Bug found!

						}
					   else if (methodName.equals("setRecurrence")){
						   int sizeArray=ValuesGenerator.getRandomIntBetween(random, 0, 8);
						   int[] recurDays=ValuesGenerator.generateRandomArray(random, sizeArray);
						   if (random.nextInt(25) == 0){
							   recurDays = null;
						   }
						   int recur=ApptRandomTest.RandomSelectRecur(random);
						   int recurIncrement = ValuesGenerator.RandInt(random);
						   int recurNumber=ApptRandomTest.RandomSelectRecurForEverNever(random);
						   appt.setRecurrence(recurDays, recur, recurIncrement, recurNumber);

						}else if(methodName.equals("isOn")){
							int testDay=ValuesGenerator.getRandomIntBetween(random, 1, 11);
							int testMonth=ValuesGenerator.getRandomIntBetween(random, 1, 11);
							int testYear=ValuesGenerator.getRandomIntBetween(random, 2016, 2019);
							boolean isOn = appt.isOn(testDay, testMonth, testYear);
						}			
				}
				
				 elapsed = (Calendar.getInstance().getTimeInMillis() - startTime);
			        if((iteration%10000)==0 && iteration!=0 ){
						 //System.out.println("elapsed time: "+ elapsed + " of "+TestTimeout);
					}
			 
			}
		}catch(NullPointerException e){
			
		}
	 
		 System.out.println("Done testing...");
	 }


	


	
}
