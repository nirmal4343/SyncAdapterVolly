package com.pryamid.contentprovider;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.pryamid.contentprovider.config.ProviderConfig;
import com.pryamid.contentprovider.model.Employees;

/**
 * Created by thakurn on 6/17/2015.
 */
public class EmployeeListAdapter extends BaseAdapter {

    final String TAG = EmployeeListAdapter.class.getName();

    private final Activity context;

    private final Employees employees;

    public EmployeeListAdapter(Activity context , Employees employees) {

        this.context = context;

        this.employees = employees;
    }

    @Override
    public int getCount() {

        return employees.size();
    }

    @Override
    public Object getItem(int position) {

        return employees.get(position);
    }

    @Override
    public long getItemId(int position) {

        return employees.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.emp_list_item, null);
        }

        TextView nameTxt = (TextView) convertView.findViewById(R.id.emp_name_id);

        TextView titleTxt = (TextView) convertView.findViewById(R.id.emp_title_id);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.icon);

        nameTxt.setText(employees.get(position).getFirstName());

        titleTxt.setText(employees.get(position).getTitle());

        loadImages(imageView, employees.get(position).getImageDownloadPath());
        
        return convertView;

    }

    private void loadImages(final ImageView imageView, final String imageUrl) {

        ImageLoader imageLoader = AppController.getInstance().getImageLoader();

        imageLoader.get(imageUrl, new ImageLoader.ImageListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Image Load Error: " + error.getMessage());
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {
                    // load image into image View
                    imageView.setImageBitmap(response.getBitmap());
                }
            }
        });
    }
}