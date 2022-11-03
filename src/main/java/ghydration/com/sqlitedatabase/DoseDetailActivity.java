package ghydration.com.sqlitedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;
import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Date;

public class DoseDetailActivity extends AppCompatActivity {

    private EditText titleEditText, descEditText,  tvDisplayTime;
    private Button deleteButton, buttonChangeTime;
    private Dose selectedDose;
    private TimePicker timePickerl;

    private int hour;
    private int minute;

    static final int TIME_DIALOG_ID = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dose_detail);
        initWidgets();
        checkForEditDose();

        setCurrentTimeOnView();
        addListenerOnButton();

    }

    // display current time
    public void setCurrentTimeOnView() {
        tvDisplayTime = (EditText) findViewById(R.id.tvTime);
        timePickerl = (TimePicker) findViewById(R.id.timePickerl);

        final Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        // set current time into textview
        tvDisplayTime.setText(new
                StringBuilder().append(pad(hour)).append(":").
                append(pad(minute)));

        // set current time into timepicker
        timePickerl.setHour(hour);
        timePickerl.setMinute(minute);
    }

    private void addListenerOnButton() {
        Button buttonChangeTime = (Button) findViewById(R.id.buttonChangeTime);
        buttonChangeTime.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog(TIME_DIALOG_ID);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID:

                // set time picker as current time
                return new TimePickerDialog(this, timePickerListener, hour,
                        minute, false);
        }
        return null;
    }

    private TimePickerDialog.OnTimeSetListener timePickerListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                    hour = selectedHour;
                    minute = selectedMinute;

                    // set current date into text view
                    tvDisplayTime.setText(new StringBuilder().append(pad(hour)).append(pad(minute)));

                    // set current time into time picker
                    timePickerl.setHour(hour);
                    timePickerl.setMinute(minute);
                }
            };

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    private void initWidgets() {
        titleEditText = findViewById(R.id.tvTime);
        descEditText = findViewById(R.id.descriptionEditText);
        deleteButton = findViewById(R.id.deleteDoseButton);
    }

    private void checkForEditDose()
    {
        Intent previousIntent = getIntent();

        int passedDoseID = previousIntent.getIntExtra(Dose.DOSE_EDIT_EXTRA, -1);
        selectedDose = Dose.getDoseForID(passedDoseID);

        if (selectedDose != null)
        {
            titleEditText.setText(selectedDose.getTitle());
            descEditText.setText(selectedDose.getDescription());
        }
        else
        {
            deleteButton.setVisibility(View.INVISIBLE);
        }
    }

    public void saveNote(View view)
    {
        SQLite sqLite = SQLite.instanceOfDatabase(this);
        String title = String.valueOf(titleEditText.getText());
        String desc = String.valueOf(descEditText.getText());

        if (selectedDose == null)
        {
            int id = Dose.doseArrayList.size();
            Dose newDose = new Dose(id, title, desc);
            Dose.doseArrayList.add(newDose);
            sqLite.addDoseToDatabase(newDose);
        }
        else
        {
            selectedDose.setTitle(title);
            selectedDose.setDescription(desc);
            sqLite.updateDoseInDB(selectedDose);
        }


        finish();
    }

    public void deleteDose(View view)
    {
        selectedDose.setDeleted(new Date());
        SQLite sqLite = SQLite.instanceOfDatabase(this);
        sqLite.updateDoseInDB(selectedDose);
        finish();
    }




}