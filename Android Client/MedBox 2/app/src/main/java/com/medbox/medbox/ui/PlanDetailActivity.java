package com.medbox.medbox.ui;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.medbox.medbox.MainActivity;
import com.medbox.medbox.R;
import com.medbox.medbox.model.MedicineRecord;

public class PlanDetailActivity extends AppCompatActivity {

    private MedicineRecord record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_detail);

        // get the record data from the intent
        if(getIntent()!= null && getIntent().hasExtra("record")){
            this.record = (MedicineRecord) getIntent().getSerializableExtra("record");
            //showTheData(record);
        }else{
            System.out.println("No record got from parent activity!");
        }
        ((TextView)findViewById(R.id.detail_attention)).setMovementMethod(new ScrollingMovementMethod());

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if(id == android.R.id.home) {//back key
            onBackPressed();
            return true;
        }
        else
            return super.onOptionsItemSelected(item);
    }

    private void showTheData(MedicineRecord record){
        // update the medicine name
        ((TextView)findViewById(R.id.detail_medicine_name)).setText(record.medicine.medicineName);
        //update the patient name
        ((TextView)findViewById(R.id.detail_username)).setText(record.patient.patientName);
        //update doctor name
        ((TextView)findViewById(R.id.detail_doctorname)).setText(record.doctor.doctorName);
        //update plan interval
        ((TextView)findViewById(R.id.detail_interval)).setText(record.mrInterval);
        //update plan period
        ((TextView)findViewById(R.id.detail_period)).setText(record.firstTakeTime+" ~ "+record.lastTakeTime);
        //update plan attention
        ((TextView)findViewById(R.id.detail_attention)).setText(record.mrAttention);
        //update last take time
        ((TextView)findViewById(R.id.detail_last_take_time)).setText(record.lastTakeTime.toString());
    }
}
