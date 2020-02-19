package entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "slovo", schema = "FdCPKNUIYW")
public class SlovoEntity implements HasID {
    private Integer idSlovo;
    private String slovo;
    private String tag;
    private Integer pocetVyskytu;
    private Integer sentiment;
    private ProjevEntity projevByIdProjev;

    public SlovoEntity() {
    }

    public SlovoEntity(String slovo, String tag, Integer pocetVyskytu,
                       Integer sentiment, ProjevEntity projevEntity) {
        this.idSlovo = null;
        this.slovo = slovo;
        this.tag = tag;
        this.pocetVyskytu = pocetVyskytu;
        this.sentiment = sentiment;
        this.projevByIdProjev = projevEntity;
    }

    public SlovoEntity(Integer idSlovo, String slovo, String tag, Integer pocetVyskytu,
                       Integer sentiment, ProjevEntity projevEntity) {
        this.idSlovo = idSlovo;
        this.slovo = slovo;
        this.tag = tag;
        this.pocetVyskytu = pocetVyskytu;
        this.sentiment = sentiment;
        this.projevByIdProjev = projevEntity;
    }

    @Id
    @Column(name = "id_slovo")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Integer getIdSlovo() {
        return idSlovo;
    }

    public void setIdSlovo(Integer idSlovo) {
        this.idSlovo = idSlovo;
    }

    @Basic
    @Column(name = "slovo")
    public String getSlovo() {
        return slovo;
    }

    public void setSlovo(String slovo) {
        this.slovo = slovo;
    }

    @Basic
    @Column(name = "tag")
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Basic
    @Column(name = "pocet_vyskytu")
    public Integer getPocetVyskytu() {
        return pocetVyskytu;
    }

    public void setPocetVyskytu(Integer pocetVyskytu) {
        this.pocetVyskytu = pocetVyskytu;
    }

    @Basic
    @Column(name = "sentiment")
    public Integer getSentiment() {
        return sentiment;
    }

    public void setSentiment(Integer sentiment) {
        this.sentiment = sentiment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SlovoEntity that = (SlovoEntity) o;
        return Objects.equals(idSlovo, that.idSlovo) &&
                Objects.equals(slovo, that.slovo) &&
                Objects.equals(tag, that.tag) &&
                Objects.equals(pocetVyskytu, that.pocetVyskytu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSlovo, slovo, tag, pocetVyskytu);
    }

    @ManyToOne
    @JoinColumn(name = "id_projev", referencedColumnName = "id_projev", nullable = false)
    public ProjevEntity getProjevByIdProjev() {
        return projevByIdProjev;
    }

    public void setProjevByIdProjev(ProjevEntity projevByIdProjev) {
        this.projevByIdProjev = projevByIdProjev;
    }

    @Override
    public Integer takeID() {
        return getIdSlovo();
    }

    @Override
    public void pushID(Integer id) {
        setIdSlovo(id);
    }
}
