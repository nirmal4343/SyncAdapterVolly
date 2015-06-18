package com.pryamid.contentprovider.connection;

import android.content.Context;

import com.pryamid.contentprovider.MainActivity;
import com.pryamid.contentprovider.MainActivityFragment;
import com.pryamid.contentprovider.model.Status;


/**
 * <h1> RestFull Interface  </h1>
 * Declare an API used to proceed with RestFull implementation
 *
 * @author  Nirmal Thakur
 * @version 1.0
 * @Date 5/18/2015
 */

interface GetRESTData {

    /**
     * RestFull API declaration to pull JSON response from wUnderground server
     *
     * @param  "null"
     * @return Status.
     */
    public Status getEmployee(MainActivityFragment.VolleyCallback callback) throws Exception;

}