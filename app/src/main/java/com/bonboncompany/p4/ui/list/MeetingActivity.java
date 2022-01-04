package com.bonboncompany.p4.ui.list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bonboncompany.p4.R;
import com.bonboncompany.p4.data.model.Room;
import com.bonboncompany.p4.ui.ViewModelFactory;
import com.bonboncompany.p4.ui.add.AddMeetingActivity;
import com.bonboncompany.p4.ui.detail.DetailMeetingActivity;
import com.bonboncompany.p4.ui.list.dialogfilter.RoomSpinnerDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalTime;

public class MeetingActivity extends AppCompatActivity implements OnMeetingClickedListener {

    private MeetingViewModel viewModel;

    private RoomSpinnerDialog.RoomSpinnerListener listener;


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
        viewModel.getMeetingListLiveData().observe(this, meetingViewStateItems -> adapter.submitList(
                meetingViewStateItems));
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

                Toast.makeText(this, "Time Filter", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.filter_room:
               buttonOpenDialogClicked();
                viewModel.onRoomChanged(Room.ZELDA);
                return true;

            case R.id.refresh_all:
                viewModel.onRoomChanged(null);
                Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void buttonOpenDialogClicked() {
        RoomSpinnerDialog.RoomSpinnerListener listener = new RoomSpinnerDialog.RoomSpinnerListener(){
            @Override
            public void roomSpinner(Spinner room) {
                Toast.makeText(MeetingActivity.this,room.toString(), Toast.LENGTH_LONG).show();
            }
        };
        final RoomSpinnerDialog dialog = new RoomSpinnerDialog(this, listener);

        dialog.show();

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