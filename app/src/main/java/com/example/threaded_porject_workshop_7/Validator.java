package com.example.threaded_porject_workshop_7;

import android.widget.EditText;
import android.widget.Toast;

import java.sql.*;

public class Validator
{
    /**
     * Checks if the text field contains a value
     * @param tf
     * @return true if present
     */
    public static boolean isPresent(EditText tf)
    {
        boolean result = true;

        //value is null or empty
        if(tf.getText().toString() == null || tf.getText().toString().isEmpty())
        {
            tf.setError("Please fill in this field.");
            result = false;
        }

        return result;
    }

    public static boolean isPresentWithoutMessage(EditText tf)
    {
        boolean result = true;

        //value is null or empty
        if(tf.getText().toString() == null || tf.getText().toString().isEmpty())
        {
            result = false;
        }

        return result;
    }

    /**
     * Checks if a double value is positive
     * @param tf
     * @return
     */
    public static boolean isDoublePositive(EditText tf)
{
    boolean result = true;
    //value isnt a double
    if(!tryParseDouble(tf.getText().toString()))
    {
        tf.setError("Please fill in a number.");
        result = false;
    }
    //value is negative
    else if(Double.parseDouble(tf.getText().toString()) < 0)
    {
        tf.setError("Please fill in a positive.");
        result = false;
    }
    return result;
}

    public static boolean isDoublePositiveWithoutMessage(EditText tf)
    {
        boolean result = true;
        //value isnt a double
        if(!tryParseDouble(tf.getText().toString()))
        {
            result = false;
        }
        //value is negative
        else if(Double.parseDouble(tf.getText().toString()) < 0)
        {
            result = false;
        }
        return result;
    }

    /**
     * Checks if a double value is in range
     * @param tf
     * @return
     */
    public static boolean isDoubleInRange(double min, double max, EditText tf)
    {
        boolean result = true;
        //value isnt a double
        if(!tryParseDouble(tf.getText().toString()))
        {
            tf.setError("Please fill in a number");
            result = false;
        }
        //value is out of range
        else if(Double.parseDouble(tf.getText().toString()) > max)
        {
            tf.setError("Invalid number");
            result = false;
        }
        return result;
    }

    /**
     * Checks if a int value is positive
     * @param tf
     * @return
     */
    public static boolean isIntPositive(EditText tf)
    {
        boolean result = true;
        //value isnt a int
        if(!tryParseInt(tf.getText().toString()))
        {
            tf.setError("Please fill in a number.");
            result = false;
        }
        //value is negative
        else if(Integer.parseInt(tf.getText().toString()) < 0)
        {
            tf.setError("Please fill in a positive number.");
            result = false;
        }
        return result;
    }

    public static boolean isIntPositiveWithoutMessage(EditText tf)
    {
        boolean result = true;
        //value isnt a int
        if(!tryParseInt(tf.getText().toString()))
        {
            result = false;
        }
        //value is negative
        else if(Integer.parseInt(tf.getText().toString()) < 0)
        {
            result = false;
        }
        return result;
    }

    /**
     * Checks if the start date is a valid entry
     * @param tf
     * @return
     */
    public static boolean isStartDateValid(EditText tf)
    {
        boolean result = true;
        //convert string to date
        try
        {
            //convert start date string to date object
            Date startDate = Date.valueOf(tf.getText().toString());

            //compare start date with current date
            Date today = new Date(System.currentTimeMillis());

            //start value is before current date
            if(startDate.before(today))
            {
                tf.setError("Please fill in a date that hasnt passed");
                result = false;
            }

        }
        catch (IllegalArgumentException ie) // failed to convert start date to date type object
        {
            tf.setError("Please fill in a valid start date");
            result = false;
        }
        return result;
    }

    /**
     * Checks if end date is a valid entry
     * @param tfStartDate
     * @param tfEndDate
     * @return
     */
    public static boolean isEndDateValid(EditText tfStartDate, EditText tfEndDate)
    {
        boolean result = true;
        try
        {
            //convert start date string to date type object
            Date startDate = Date.valueOf(tfStartDate.getText().toString());
            //convert end date string to date type object
            Date endDate = Date.valueOf(tfEndDate.getText().toString());

            //end date is before start date
            if(endDate.before(startDate))
            {
                tfEndDate.setError("Please fill in a date that passes the start date.");
                result = false;
            }
        }
        catch (IllegalArgumentException ie) // failed to convert string objects to date type objects
        {
            tfEndDate.setError("Please fill in a valid start date");
            result = false;
        }
        return result;
    }

    /**
     * Checks if a string object can convert to a double object
     * Inspired by a solution in stackoverflow
     * url: https://stackoverflow.com/questions/8391979/does-java-have-a-int-tryparse-that-doesnt-throw-an-exception-for-bad-data
     * @param value
     * @return
     */
    private static boolean tryParseDouble (String value)
    {
        boolean result = true;
        try
        {
            //parse string to double
            double doubleValue = Double.parseDouble(value);
        }
        catch (NumberFormatException e) // failed to parse string to double
        {
            result = false;
        }

        return result;
    }
    /**
     * Checks if a string object can convert to a int object
     * Inspired by a solution in stackoverflow
     * url: https://stackoverflow.com/questions/8391979/does-java-have-a-int-tryparse-that-doesnt-throw-an-exception-for-bad-data
     * @param value
     * @return
     */
    private static boolean tryParseInt (String value)
    {
        boolean result = true;
        try
        {
            //parse string to double
            int intValue = Integer.parseInt(value);
        }
        catch (NumberFormatException e) // failed to parse string to double
        {
            result = false;
        }

        return result;
    }
}
