package creator;

import entity.SlovoEntity;

import java.util.Objects;

public class LemmaWithTag {
    private String lemma;
    private String tag;

    public LemmaWithTag(String lemma, String tag) {
        this.lemma = lemma;
        this.tag = tag;
    }

    public String getLemma() {
        return lemma;
    }

    public void setLemma(String lemma) {
        this.lemma = lemma;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        LemmaWithTag that = (LemmaWithTag) obj;
        return that.lemma.equals(this.lemma) && that.tag.equals(this.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lemma, tag);
    }
}
