package edu.lcark.todo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private String[] days;

    private String today;

    private int dayIndex;

   // private TextView day;

    private Button yesterday;

    private Button tomorrow;

    private Button save;

    private EditText reminder;

    private Spinner spinner;

    public void setDay(){
        today = days[dayIndex];
        //day.setText(today);
        yesterday.setText(days[(dayIndex + 6) % 7] );
        tomorrow.setText(days[(dayIndex + 8) % 7]);
        reminder.setText(getPreferences(dayIndex).getString(today, ""));
        reminder.setHint(getResources().getString(R.string.hint) + " " + days[dayIndex]);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //day = (TextView) findViewById(R.id.activity_main_day);
        spinner = (Spinner) findViewById(R.id.activity_main_day);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                dayIndex = position;
                setDay();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        days = getResources().getStringArray(R.array.days_of_the_week);
        yesterday = (Button) findViewById(R.id.activity_main_yesterday);
        tomorrow = (Button) findViewById(R.id.activity_main_tomorrow);
        reminder = (EditText) findViewById(R.id.activity_main_list);
        save = (Button) findViewById(R.id.activity_main_save);
        dayIndex = 1;
        yesterday.setOnClickListener(this);
        tomorrow.setOnClickListener(this);
        save.setOnClickListener(this);
        setDay();

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.activity_main_yesterday){
            dayIndex = (dayIndex + 6) % 7;
            setDay();
        }
        if(v.getId() == R.id.activity_main_tomorrow){
            dayIndex = (dayIndex + 8) % 7;
            setDay();
        }
        if(v.getId() == R.id.activity_main_save){
            SharedPreferences.Editor editor = getPreferences(dayIndex).edit();
            editor.putString(today, reminder.getText().toString());
            editor.commit();
            Toast.makeText(getApplicationContext(), R.string.save_successful, Toast.LENGTH_SHORT).show();
        }
        if (v.getId() == R.id.activity_main_day){
            dayIndex = spinner.getId();
            setDay();
        }
    }
}
