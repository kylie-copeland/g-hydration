package ghydration.com.sqlitedatabase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class DoseAdapter extends ArrayAdapter<Dose>
{
    public DoseAdapter(Context context, List<Dose> doses)
    {
        super(context, 0, doses);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        Dose dose = getItem(position);
        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.dose_cell, parent, false);

        TextView title = convertView.findViewById(R.id.cellTitle);
        TextView desc = convertView.findViewById(R.id.cellDesc);

        title.setText(dose.getTitle());
        desc.setText(dose.getDescription());

        return convertView;
    }
}
