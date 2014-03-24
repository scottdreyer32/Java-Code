package all;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CountDownTimer {
    private int hours, minutes, seconds, totalSeconds;
    boolean isFinished;
	
    private static int count = 0;

    /** Don't put a colon after the parameter name
     /**
     * Computes total number of seconds represented by the given params
     * @param hours:    Number of hours input
     * @param minutes:   Number of minutes input
     * @param seconds:   Number of seconds input
     * @return Calculates and returns the total amount of seconds
     * @exception  If params are neg throws IllegalArgumentException.
     */
    public int totalSeconds()
    {
            return totalSeconds;
    }
    /**
     * Checks and validates the parameters for the timer
     * @exception throws exceptions for invalid numbers
     */
    public void validate()
    {
        //validate parameters
        if(seconds>59 || minutes>59){

            throw new IllegalArgumentException("Seconds or " +
                    "minutes greater than 60.");
        }
        if(hours<0 || minutes<0||seconds<0)
        {
            throw new IllegalArgumentException("Something is " +
                    "less than 0");
        }

    }

    /**
     * Gets the current amount of hours
     * @return returns the current amount of hours in the timer
     */
    public int getHours()
    {
        return hours;
    }

    /**
     * Gets the current amount of minutes in the timer
     * @return returns the amount of minutes
     */
    public int getMinutes()
    {
        return minutes;
    }
    /**
     * Gets the current amount of seconds in the timer
     * @return returns the amount of seconds
     */
    public int getSeconds()
    {
        return seconds;
    }

    /**
     * Default constructor sets timer to zero
     */
    public CountDownTimer()
    {
        hours = 0;
        minutes = 0;
        seconds = 0;
        count += 1;

    }

    //*** Should have @throws tag also 
    /**
     * Initializes instance variables with provided values
     * @param hours:     number of hours in the timer
     * @param minutes:   number of minutes in the timer
     * @param seconds:   number of seconds in the timer
     */
    public CountDownTimer(int hours, int minutes, int seconds)
    {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        this.totalSeconds = hours*3600 + minutes*60+seconds;
        if(hours>0||minutes>0||seconds>0){
        	isFinished = false;
        }
        this.validate();
        count += 1;

    }

    //*** Should have @throws tag also

    /**
     * Initializes instance variables with provided values
     * @param minutes:  number of minutes in the timer
     * @param seconds:  number of seconds in the timer
     */
    public CountDownTimer(int minutes, int seconds)
    {

        this.hours = 0;
        this.minutes = minutes;
        this.seconds = seconds;
        this.validate();
        count += 1;
    }

    //*** Should have @throws tag also
    /**
     * Initializes instance variables with provided values
     * @param seconds:  number of seconds in the timer
     */
    public CountDownTimer(int seconds)
    {

        this.hours = 0;
        this.minutes = 0;
        this.seconds = seconds;
        this.validate();
        count += 1;
    }

    /**
     * Makes new CountDownTimer equal to other
     * @param other:  a CountDownTimer object
     */
    public CountDownTimer (CountDownTimer other)
    {
        this.validate();
        this.hours = other.getHours();
        this.minutes = other.getMinutes();
        this.seconds = other.getSeconds();
        count += 1;

    }

    /**
     * Accepts a string as a parameter
     * "1:21:30" hrs,mins,sec
     * "15:30 is min,sec
     * "30" is sec
     * @param startTime  A string input that is separated for integers
     * @exception throws exception if doesn't parse or invalid format
     */
    public CountDownTimer(String startTime)
    {

        int dividers = 0;
        int last = startTime.length();
        for (int index = 0; index<startTime.length(); index++)
        {
            if (startTime.charAt(index) == ':'){
                dividers += 1;
            }

        }
        if(startTime.length()==0)
        {
            this.hours = 0;
            this.minutes = 0;
            this.seconds = 0;
        }
        else if ((last>0 && startTime.charAt(last-1) == ':')
                || startTime.charAt(0)==':')
        {
            throw new IllegalArgumentException("Can't start with or " +
                    "end with a colon");
        }
        if(dividers>2){
            throw new IllegalArgumentException("Invalid format");
        }
        else if (dividers==2){
            String[] parts = startTime.split(":");
            hours = Integer.parseInt(parts[0]);
            minutes = Integer.parseInt(parts[1]);
            seconds = Integer.parseInt(parts[2]);
        }
        else if(dividers==1){
            String[] parts = startTime.split(":");
            hours = 0;
            minutes = Integer.parseInt(parts[0]);
            seconds = Integer.parseInt(parts[1]);

        }
        else
        {
            hours = 0;
            minutes=0;
            seconds = Integer.parseInt(startTime);
        }
        this.validate();
        count += 1;

    }
   
    //method true if "this" CountDownTimer object is the exact same
    //as other object(other object must be cast as CDT object)

    /**
     * Compares "this" CountDownTimer object to the "other"
     * after "other" is cast as a CDT object.
     * @param other object that is cast as a CDT object.
     * @return true if equal and false if not
     */
    public boolean equals(Object other)
    {
        if(other == null){
            return false;
        }
        CountDownTimer myTimer = (CountDownTimer) other;
        myTimer.validate();
        this.validate();


        if(this.totalSeconds() == myTimer.totalSeconds()) {
            return true;
        }
        else
        {
            return false;
        }
    }
    /*
    returns a value >= 1 if this CDT object is greater than the other
    returns a value<=1 if the this CDT is less than other
    returns 0 if both are equal
     */

    /**
     * Compares two CountDownTimer objects
     * @param other a CountDownTimer object
     * @return returns a value >= 1 if this CDT object is greater than the other
     * returns a value<=1 if the this CDT is less than other
     * returns 0 if both are equal
     */
    public int compareTo(CountDownTimer other)
    {
        int myTimer = this.totalSeconds();
        int otherTimer = other.totalSeconds();

        if(myTimer > otherTimer){
            return 1;
        }
        else if(myTimer < otherTimer){
            return -1;
        }
        else
            return 0;
    }

    /**
     * Subtracts number of seconds to this CDT object
     * @param seconds  number of seconds to subtract
     */
    public void subtract(int secs)
    {
        int temp = totalSeconds;

        if(temp < secs || secs<0)
        {
            throw new IllegalArgumentException();
        }
        else
        {
            // This algorithm subtracts anything over 0sec and converts to min and hours
            totalSeconds-=secs;
        	temp-= secs;
            this.hours = temp/3600;
            temp -= hours * 3600;
            this.minutes = temp/60;
            temp -= minutes * 60;
            this.seconds = temp;
        }
        if(totalSeconds==0){
        	isFinished=true;
        }
       
    }
    /**
     * Method that decrements the this CDT by 1 second
     */
    public void dec()
    {
        this.subtract(1);
    }
    /*
    method returns a string that represents a CDT with the following
     format "1:06:01". Display the hours as is; if minutes<10 then
     display with a leading "0", and always display seconds with 2
     digits.
     */

    /**
     * Method prepares a string that represents the CDT object
     *
     * @return returns a string that represents the CDT object
     */
    public String toString()
    {
        String timer;
        String min;
        String hrs;
        String sec;
        if(minutes < 10){
            min= "0" + minutes;
        }
        else
            min = "" + minutes;
        if(seconds < 10){
            sec = "0" + seconds;
        }
        else
            sec = "" + seconds;

        hrs = "" + hours;

        timer = hrs + ":" + min + ":" + sec;

        return timer;
    }
    public String hourToString()
    {
        String hrs;
      
        if(minutes < 10){
            hrs= "0" + minutes;
        }
        else
            hrs = "" + minutes;
        return hrs;
    }
    public String minToString()
    {
       
        String min;
        if(minutes < 10){
            min= "0" + minutes;
        }
        else
            min = "" + minutes;
      
        return min;
    }
    public String secToString()
    {
        String sec;
        
        if(seconds < 10){
            sec = "0" + seconds;
        }
        else
            sec = "" + seconds;
        return sec;
    }

    /**
     * Saves the current CDT object
     * @param fileName the name of the file that is being saved
     * @throws IOException
     */
    public void save(String fileName) throws IOException
    {

        FileWriter writer = new FileWriter(fileName);
        BufferedWriter out = new BufferedWriter(writer);
        out.write(this.toString());
        out.close();

    }

    /**
     * Loads a CDT object from a saved file
     * @param fileName  the name of the file that is being loaded
     * @throws FileNotFoundException
     */
    public void load(String fileName)throws FileNotFoundException {
        Scanner scanFile = new Scanner(new File(fileName));
        CountDownTimer timer = new CountDownTimer(scanFile.next());
        scanFile.close();
        this.hours = timer.getHours();
        this.minutes = timer.getMinutes();
        this.seconds = timer.getSeconds();
    }

    /**
     * Shows how many CDT objects have been made
     * @return   returns the number of timers created
     */
    public static int getNumberCreated()
    {
        return count;
    }
    public boolean isFinished(){
    	return isFinished;
    }

    /**
     * Runs a test
     */
  /*  public static void main(String[] args) {
        CountDownTimer myTimer = new CountDownTimer(0,10,0);
        myTimer.subtract(1);
        System.out.println(""+myTimer.getMinutes());
    }*/

}


