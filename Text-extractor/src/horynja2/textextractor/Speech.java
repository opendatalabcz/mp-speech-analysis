package horynja2.textextractor;

import java.util.ArrayList;
import java.util.List;

public class Speech {
    private String speaker;
    private String speakerID;
    private List<SpeechPart> speechParts;

    public Speech(String speaker) {
        this.speaker = speaker;
        this.speechParts = new ArrayList<>();
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public String getSpeakerID() {
        return speakerID;
    }

    public void setSpeakerID(String speakerID) {
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
        ret += speaker + " (" + speakerID + "):";
        for (SpeechPart sp : speechParts) {
            ret+=sp.toString() + "\n";
        }
        return ret;
    }
}
