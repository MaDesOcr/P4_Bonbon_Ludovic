package com.bonboncompany.p4.ui.detail;

import com.bonboncompany.p4.data.model.Room;

import java.time.LocalTime;
import java.util.Objects;

public class DetailMeetingViewState {

    private final String meetingTopic;
    LocalTime time;
    private final Room room;
    private final String participantMail;

    public DetailMeetingViewState(String meetingTopic,LocalTime time, Room room, String participantMail) {
        this.meetingTopic = meetingTopic;
        this.time = time;
        this.room = room;
        this.participantMail = participantMail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DetailMeetingViewState that = (DetailMeetingViewState) o;
        return time == that.time && Objects.equals(meetingTopic, that.meetingTopic) && room == that.room && Objects.equals(participantMail, that.participantMail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(meetingTopic, time, room, participantMail);
    }

    @Override
    public String toString() {
        return "MeetingDetailViewState{" +
                "meetingTopic='" + meetingTopic + '\'' +
                ", time=" + time +
                ", room=" + room +
                ", participantMail='" + participantMail + '\'' +
                '}';
    }

    public String getMeetingTopic() {
        return meetingTopic;
    }

    public LocalTime getTime() {
        return time;
    }
    public Room getRoom() {
        return room;
    }

    public String getParticipantMail() {
        return participantMail;
    }
}