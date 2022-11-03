package ghydration.com.sqlitedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ListView doseListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        loadFromDBToMemory();
        setDoseAdapter();
        setOnClickListener();
    }

    private void initWidgets()
    {
        doseListView = findViewById(R.id.doseListView);
    }

    private void loadFromDBToMemory()
    {
        SQLite sqLite = SQLite.instanceOfDatabase(this);
        sqLite.populateDoseListArray();
    }

    private void setDoseAdapter()
    {
        DoseAdapter doseAdapter = new DoseAdapter(getApplicationContext(), Dose.nonDeletedDoses());
        doseListView.setAdapter(doseAdapter);
    }

    private void setOnClickListener()
    {
        doseListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                Dose selectedDose = (Dose) doseListView.getItemAtPosition(position);
                Intent editDoseIntent = new Intent(getApplicationContext(), DoseDetailActivity.class);
                editDoseIntent.putExtra(Dose.DOSE_EDIT_EXTRA, selectedDose.getId());
                startActivity(editDoseIntent);
            }
        });
    }

    public void newDose(View view)
    {
        Intent newDoseIntent = new Intent(this, DoseDetailActivity.class);
        startActivity(newDoseIntent);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setDoseAdapter();
    }

}