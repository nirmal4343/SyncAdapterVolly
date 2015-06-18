package com.pryamid.contentprovider.connection;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.pryamid.contentprovider.AppController;
import com.pryamid.contentprovider.MainActivity;
import com.pryamid.contentprovider.MainActivityFragment;
import com.pryamid.contentprovider.config.ProviderConfig;
import com.pryamid.contentprovider.model.Status;
import com.pryamid.contentprovider.parser.EmployeeParser;

import java.io.IOException;

/**
 * <h1> RestFull Call  </h1>
 * Rest call API implementation
 *
 * @author  Nirmal Thakur
 * @version 1.0
 * @Date 5/18/2015
 */

public class RestCall implements GetRESTData{

    private static String TAG = RestCall.class.getSimpleName();

    private static String tag_string_req = "string_req";

    private static RestCall instance = null;

    private RestCall() {

        // Exists only to defeat instantiation.
    }

    /**
     * Singleton implementation of this class
     */
    public static synchronized RestCall getInstance() {

        if(instance == null) {

            instance = new RestCall();
        }
        return instance;
    }

    public Status getEmployee(final MainActivityFragment.VolleyCallback callback){

        final Status empStatus = new Status();

        StringRequest strReq = new StringRequest(Method.GET,ProviderConfig.BASE_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response){

                empStatus.setStatus(Status.STATUS_SUCCESS);

                empStatus.setMessage(response.toString());

                callback.onSuccess(empStatus);

            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {

                empStatus.setStatus(Status.STATUS_FAIL);

                empStatus.setMessage(error.getMessage());

            }
        });

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

        return empStatus;
    }
}
