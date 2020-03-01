package poslanciDB.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "projev_statistiky", schema = "main")
public class ProjevStatistikyEntity implements HasID{
    private Integer idProjev;
    private Integer pocetPosSlov;
    private Integer pocetNegSlov;
    private Double sentiment;
    private ProjevEntity projevByIdProjev;

    public ProjevStatistikyEntity() {
    }

    public ProjevStatistikyEntity(Integer idProjev, Integer pocetPosSlov,
                                  Integer pocetNegSlov, Double sentiment, ProjevEntity projevEntity) {
        this.idProjev = idProjev;
        this.pocetPosSlov = pocetPosSlov;
        this.pocetNegSlov = pocetNegSlov;
        this.sentiment = sentiment;
        this.projevByIdProjev = projevEntity;
    }

    @Id
    @Column(name = "id_projev")
    public Integer getIdProjev() {
        return idProjev;
    }

    public void setIdProjev(Integer idProjev) {
        this.idProjev = idProjev;
    }

    @Basic
    @Column(name = "pocet_pos_slov")
    public Integer getPocetPosSlov() {
        return pocetPosSlov;
    }

    public void setPocetPosSlov(Integer pocetPosSlov) {
        this.pocetPosSlov = pocetPosSlov;
    }

    @Basic
    @Column(name = "pocet_neg_slov")
    public Integer getPocetNegSlov() {
        return pocetNegSlov;
    }

    public void setPocetNegSlov(Integer pocetNegSlov) {
        this.pocetNegSlov = pocetNegSlov;
    }

    @Basic
    @Column(name = "sentiment")
    public Double getSentiment() {
        return sentiment;
    }

    public void setSentiment(Double sentiment) {
        this.sentiment = sentiment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjevStatistikyEntity that = (ProjevStatistikyEntity) o;
        return Objects.equals(idProjev, that.idProjev) &&
                Objects.equals(pocetPosSlov, that.pocetPosSlov) &&
                Objects.equals(pocetNegSlov, that.pocetNegSlov) &&
                Objects.equals(sentiment, that.sentiment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProjev, pocetPosSlov, pocetNegSlov, sentiment);
    }

    @OneToOne
    @JoinColumn(name = "id_projev", referencedColumnName = "id_projev", nullable = false)
    public ProjevEntity getProjevByIdProjev() {
        return projevByIdProjev;
    }

    public void setProjevByIdProjev(ProjevEntity projevByIdProjev) {
        this.projevByIdProjev = projevByIdProjev;
    }

    @Override
    public Integer takeID() {
        return getIdProjev();
    }

    @Override
    public void pushID(Integer id) {
        setIdProjev(id);
    }
}
