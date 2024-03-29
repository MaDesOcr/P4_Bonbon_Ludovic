package com.bonboncompany.p4.ui.list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bonboncompany.p4.R;
import com.bonboncompany.p4.data.model.Room;
import com.bonboncompany.p4.ui.ViewModelFactory;
import com.bonboncompany.p4.ui.add.AddMeetingActivity;
import com.bonboncompany.p4.ui.detail.DetailMeetingActivity;
import com.bonboncompany.p4.ui.list.dialogfilter.CustomTimePickerDialog;
import com.bonboncompany.p4.ui.list.dialogfilter.CustomRoomSpinnerDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class MeetingActivity extends AppCompatActivity implements OnMeetingClickedListener {

    private MeetingViewModel viewModel;


    Calendar mcurrentTime = Calendar.getInstance();
    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
    int minute = mcurrentTime.get(Calendar.MINUTE);
    private Room room;
    CustomTimePickerDialog mTimePicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FloatingActionButton fab = findViewById(R.id.main_fab_add);
        fab.setOnClickListener(v -> startActivity(AddMeetingActivity.navigate(this)));

        MeetingAdapter adapter = new MeetingAdapter(this);
        RecyclerView recyclerView = findViewById(R.id.meeting_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(
                MeetingViewModel.class);
        viewModel.getMeetingListLiveData().observe(this,
                meetingViewStateItems -> adapter.submitList(
                        meetingViewStateItems));
    }

    public void buttonOpenDialogRoomClicked() {
        AlertDialog.Builder dialogRoom = new AlertDialog.Builder(this)
                .setTitle("Choisis une salle");

        final View CustomRoomSpinnerDialogView = getLayoutInflater().inflate(
                R.layout.room_spinner_custom_dialog,null
        );
        dialogRoom.setView(CustomRoomSpinnerDialogView);


                dialogRoom.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Spinner roomSpinnerFilter = (Spinner) findViewById(R.id.spinner_room_filter);
                        roomSpinnerFilter.setAdapter(new ArrayAdapter<Room>(,
                                android.R.layout.simple_spinner_item,
                                Room.values()));

                        if (roomSpinnerFilter == null) {
                            Toast.makeText(MeetingActivity.this, "select a room", Toast.LENGTH_LONG).show();
                        }
                        room = (Room) roomSpinnerFilter.getSelectedItem();
                    }
                });
        AlertDialog dialog = dialogRoom.create();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.filter_hour:
                buttonOpenDialogTimeClicked();

                Toast.makeText(this, "Time Filter", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.filter_room:
                buttonOpenDialogRoomClicked();

                viewModel.onRoomChanged(room);
                //Test du filtre avec la room zelda
                return true;

            case R.id.refresh_all:
                viewModel.onRoomChanged(null);
                Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void buttonOpenDialogTimeClicked() {
        mTimePicker = new CustomTimePickerDialog(MeetingActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfDay) {
                String selectedHour = Integer.toString(hour);
                String selectedMinute = Integer.toString(minute);
                String time = (selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);
        mTimePicker.setTitle("Selectionne l'heure");
        mTimePicker.show();
    }

    @Override
    public void onMeetingClicked(long meetingId) {
        startActivity(DetailMeetingActivity.navigate(this, meetingId));
    }

    @Override
    public void onDeleteMeetingClicked(long meetingId) {
        viewModel.onDeleteMeetingClicked(meetingId);
    }
}