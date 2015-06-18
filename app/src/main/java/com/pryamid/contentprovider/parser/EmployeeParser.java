package com.pryamid.contentprovider.parser;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pryamid.contentprovider.model.Employees;
import java.io.IOException;

/**
 * Created by thakurn on 6/17/2015.
 */
public class EmployeeParser {


    private ObjectMapper objectMapper = null;
    private JsonFactory jsonFactory = null;
    private JsonParser jp = null;
    private Employees mEmployees = null;

    public static final String TAG = EmployeeParser.class.getSimpleName();

    private static EmployeeParser mInstance;

    public static synchronized EmployeeParser getInstance() {

        if(mInstance != null) {

            return mInstance;
        }
        else {
            mInstance = new EmployeeParser();

            return mInstance;
        }
    }

    public Employees parseEmployees(String json)  {

        try
        {
            objectMapper = new ObjectMapper();

            mEmployees = objectMapper.readValue(json, Employees.class);
        }
        catch (JsonParseException e)
        {
            e.printStackTrace();

        } catch (JsonMappingException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();
        }

        return mEmployees;
    }
}
