package com.pryamid.contentprovider;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.pryamid.contentprovider.connection.RestCall;
import com.pryamid.contentprovider.model.Employees;
import com.pryamid.contentprovider.model.Status;
import com.pryamid.contentprovider.parser.EmployeeParser;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        RestCall.getInstance().getEmployee(new VolleyCallback(){

            @Override
            public void onSuccess(Status result) {

                ListView listView = (ListView) getActivity().findViewById(R.id.emp_list_id);

                Employees employees = EmployeeParser.getInstance().parseEmployees(result.getMessage());

                EmployeeListAdapter adapter = new EmployeeListAdapter(getActivity(),employees);

                listView.setAdapter(adapter);

                adapter.notifyDataSetChanged();
            }
        });
    }

    public interface VolleyCallback{

        void onSuccess(Status status);
    }


}
