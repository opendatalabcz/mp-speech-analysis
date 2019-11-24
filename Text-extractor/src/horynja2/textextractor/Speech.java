package horynja2.textextractor;

import java.util.ArrayList;
import java.util.List;

public class Speech {
    private String speaker;
    private Integer speakerID;
    private Integer meetingNum;
    private Integer speechNumInMeeting;
    private List<SpeechPart> speechParts;

    public Speech(String speaker) {
        this.speaker = speaker;
        this.speechParts = new ArrayList<>();
    }

    public Speech(String speaker, Integer speakerID) {
        this.speaker = speaker;
        this.speakerID = speakerID;
        this.speechParts = new ArrayList<>();
    }

    public Speech(String speaker, Integer speakerID, Integer meetingNum, Integer speechNumInMeeting) {
        this.speaker = speaker;
        this.speakerID = speakerID;
        this.meetingNum = meetingNum;
        this.speechNumInMeeting = speechNumInMeeting;
        this.speechParts = new ArrayList<>();
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public Integer getSpeakerID() {
        return speakerID;
    }

    public void setSpeakerID(Integer speakerID) {
        this.speakerID = speakerID;
    }

    public List<SpeechPart> getSpeechParts() {
        return speechParts;
    }

    public void setSpeechParts(List<SpeechPart> speechParts) {
        this.speechParts = speechParts;
    }

    public void addSpeechPart(SpeechPart sp){
        this.speechParts.add(sp);
    }

    @Override
    public String toString() {
        String ret = "";
        ret += speaker + " (SpeakerID:" + speakerID + ", MeetingNumber:" + meetingNum + ", SpeechNumberInMeeting:" + speechNumInMeeting + " ):";
        for (SpeechPart sp : speechParts) {
            ret+=sp.toString() + "\n";
        }
        return ret;
    }
}
