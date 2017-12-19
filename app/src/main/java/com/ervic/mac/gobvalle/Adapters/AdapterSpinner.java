package com.ervic.mac.gobvalle.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ervic.mac.gobvalle.Models.Types;
import com.ervic.mac.gobvalle.R;

import java.util.List;

/**
 * Created by ervic on 11/24/17.
 */

public class AdapterSpinner extends BaseAdapter {
    Context context;
    List<Types> types;
    LayoutInflater inflater;

    public AdapterSpinner(Context context, List<Types> types) {
        this.context = context;
        this.types = types;
        this.inflater = (LayoutInflater.from(context));
    }

    @Override

    public int getCount() {
        if(types == null)
            return 0;
        else
        return types.size();
    }

    @Override
    public Object getItem(int position) {
        if(types == null)
            return null;
        else
        return types.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.spinner_item_request, null);
        TextView tv_request = (TextView)convertView.findViewById(R.id.tv_request);
        TextView tv_value = (TextView) convertView.findViewById(R.id.tv_value);
        tv_request.setText(types.get(position).getLabel());
        tv_value.setText(String.valueOf(types.get(position).getValue()));

        return convertView;
    }
}
