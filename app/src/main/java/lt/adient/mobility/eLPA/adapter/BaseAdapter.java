package lt.adient.mobility.eLPA.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import lt.adient.mobility.eLPA.R;

public abstract class BaseAdapter<T> extends ArrayAdapter<T> {

    private final Context context;

    public BaseAdapter(Context context, int resource, List<T> items) {
        super(context, resource, items);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryText));
        label.setText(getText(position));
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setText(getText(position));
        return label;
    }

    public abstract String getText(int position);
}
