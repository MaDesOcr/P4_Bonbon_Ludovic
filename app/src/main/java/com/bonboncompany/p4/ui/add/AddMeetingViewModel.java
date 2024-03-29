package com.bonboncompany.p4.ui.add;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bonboncompany.p4.data.MeetingRepository;
import com.bonboncompany.p4.data.model.Room;
import com.bonboncompany.p4.util.SingleLiveEvent;

import java.time.LocalTime;

public class AddMeetingViewModel extends ViewModel {

    private final MeetingRepository meetingRepository ;
    private final MutableLiveData<Boolean> isSaveButtonEnabledMutableLiveData = new MutableLiveData<>(false);
    private final SingleLiveEvent<Void> closeActivitySingleLiveEvent = new SingleLiveEvent<>();
    private Room selectedRoom;


    public AddMeetingViewModel(@NonNull MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    // activate add button !
    public MutableLiveData<Boolean> getIsSaveButtonEnabledLiveData() {
        return isSaveButtonEnabledMutableLiveData;
    }

    public void onTopicChanged(String topic) {
        isSaveButtonEnabledMutableLiveData.setValue(!topic.isEmpty());
    }


    // single event close activity
    public SingleLiveEvent<Void> getCloseActivitySingleLiveEvent() {
        return closeActivitySingleLiveEvent;
    }

    //method creation
    public void onAddButtonClicked(
            @NonNull String topic,
            @Nullable LocalTime time,
            @Nullable Room room,
            @NonNull String participantMail
    ) {

        meetingRepository.addMeeting(topic, time, room, participantMail);
        closeActivitySingleLiveEvent.call();
    }

    public void onRoomSelected (Room selectedItem){
        selectedRoom = selectedItem;
    }

}
