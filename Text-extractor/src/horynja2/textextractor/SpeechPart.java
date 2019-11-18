package horynja2.textextractor;

public class SpeechPart {
    private String content;

    public SpeechPart(String content) {
        if(content.startsWith(": ")) content = content.substring(2);
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return content;
    }
}
