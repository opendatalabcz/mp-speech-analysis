package poslanciDB.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "poslanec_statistiky", schema = "main")
public class PoslanecStatistikyEntity implements HasID {
    private Integer idPoslanec;
    private Integer pocetSlov;
    private Double sentiment;
    private PoslanecEntity poslanecByIdPoslanec;
    private Collection<TopSlovaEntity> topSlovaByIdPoslanec;
    private Collection<PoslanecStatistikyMesicEntity> poslanecStatistikyMesicsByIdPoslanec;
    private Collection<PoslanecStatistikyZminkyEntity> poslanecStatistikyZminkiesByIdPoslanec;

    public PoslanecStatistikyEntity() {
    }

    public PoslanecStatistikyEntity(Integer idPoslanec, Integer pocetSlov, Double sentiment) {
        this.idPoslanec = idPoslanec;
        this.pocetSlov = pocetSlov;
        this.sentiment = sentiment;
    }

    @Id
    @Column(name = "id_poslanec")
    public Integer getIdPoslanec() {
        return idPoslanec;
    }

    public void setIdPoslanec(Integer idPoslanec) {
        this.idPoslanec = idPoslanec;
    }

    @Basic
    @Column(name = "pocet_slov")
    public Integer getPocetSlov() {
        return pocetSlov;
    }

    public void setPocetSlov(Integer pocetSlov) {
        this.pocetSlov = pocetSlov;
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
        PoslanecStatistikyEntity that = (PoslanecStatistikyEntity) o;
        return Objects.equals(idPoslanec, that.idPoslanec) &&
                Objects.equals(pocetSlov, that.pocetSlov) &&
                Objects.equals(sentiment, that.sentiment) &&
                Objects.equals(topSlovaByIdPoslanec, that.topSlovaByIdPoslanec) &&
                Objects.equals(poslanecStatistikyMesicsByIdPoslanec, that.poslanecStatistikyMesicsByIdPoslanec) &&
                Objects.equals(poslanecStatistikyZminkiesByIdPoslanec, that.poslanecStatistikyZminkiesByIdPoslanec);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPoslanec, pocetSlov, sentiment);
    }

    @OneToOne
    @JoinColumn(name = "id_poslanec", referencedColumnName = "id_poslanec", nullable = false)
    public PoslanecEntity getPoslanecByIdPoslanec() {
        return poslanecByIdPoslanec;
    }

    public void setPoslanecByIdPoslanec(PoslanecEntity poslanecByIdPoslanec) {
        this.poslanecByIdPoslanec = poslanecByIdPoslanec;
    }

    @OneToMany(mappedBy = "poslanecStatistikyByIdPoslanec", cascade = CascadeType.ALL)
    public Collection<TopSlovaEntity> getTopSlovaByIdPoslanec() {
        return topSlovaByIdPoslanec;
    }

    public void setTopSlovaByIdPoslanec(Collection<TopSlovaEntity> topSlovaByIdPoslanec) {
        this.topSlovaByIdPoslanec = topSlovaByIdPoslanec;
    }

    @OneToMany(mappedBy = "poslanecStatistikyByIdPoslanec", cascade = CascadeType.ALL)
    public Collection<PoslanecStatistikyMesicEntity> getPoslanecStatistikyMesicsByIdPoslanec() {
        return poslanecStatistikyMesicsByIdPoslanec;
    }

    public void setPoslanecStatistikyMesicsByIdPoslanec(Collection<PoslanecStatistikyMesicEntity> poslanecStatistikyMesicsByIdPoslanec) {
        this.poslanecStatistikyMesicsByIdPoslanec = poslanecStatistikyMesicsByIdPoslanec;
    }

    @Override
    public Integer takeID() {
        return getIdPoslanec();
    }

    @Override
    public void pushID(Integer id) {
        setIdPoslanec(id);
    }

    @OneToMany(mappedBy = "poslanecStatistikyByIdPoslanecRecnik", cascade = CascadeType.ALL)
    public Collection<PoslanecStatistikyZminkyEntity> getPoslanecStatistikyZminkiesByIdPoslanec() {
        return poslanecStatistikyZminkiesByIdPoslanec;
    }

    public void setPoslanecStatistikyZminkiesByIdPoslanec(Collection<PoslanecStatistikyZminkyEntity> poslanecStatistikyZminkiesByIdPoslanec) {
        this.poslanecStatistikyZminkiesByIdPoslanec = poslanecStatistikyZminkiesByIdPoslanec;
    }

    @Override
    public String toString() {
        return poslanecByIdPoslanec.toString();
    }
}
