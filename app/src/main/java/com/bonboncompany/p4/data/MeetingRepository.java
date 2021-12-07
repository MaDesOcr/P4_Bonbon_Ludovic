package com.bonboncompany.p4.data;

import androidx.lifecycle.MutableLiveData;

import com.bonboncompany.p4.data.model.Meeting;
import com.bonboncompany.p4.data.model.Room;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MeetingRepository {

    private final MutableLiveData<List<Meeting>> meetingsLiveData = new MutableLiveData<>(new ArrayList<>());

    private long id = 0;
    List<Meeting> meetings;
    private List<Meeting> DUMMY_MEETINGliveData;

    public MeetingRepository() {
    }

    public MutableLiveData<List<Meeting>> getMeetingsLiveData() {

        meetingsLiveData.setValue(DUMMY_MEETINGliveData);

        return meetingsLiveData;
    }

    public void addMeeting(
            String meetingTopic,
            LocalTime time,
            Room room,
            String participantMail
    ) {
        List<Meeting> meetings = meetingsLiveData.getValue();
        meetings.add(
                new Meeting(
                        id++,
                        meetingTopic,
                        time,
                        room,
                        participantMail
                )
        );

        meetingsLiveData.setValue(meetings);
    }


    public void deleteMeeting(long meetingId) {
        List<Meeting> meetings = meetingsLiveData.getValue();

        if (meetings == null) return;

        for (Iterator<Meeting> iterator = meetings.iterator(); iterator.hasNext(); ) {
            Meeting meeting = iterator.next();

            if (meeting.getId() == meetingId) {
                iterator.remove();
                break;
            }
        }
        meetingsLiveData.setValue(meetings);
    }

    private void DUMMY_MEETINGliveData() {

        addMeeting("dérapage",LocalTime.of(13, 10),Room.MARIO,"george");

    }
}
