package entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "statistiky", schema = "dbo", catalog = "Poslanci")
public class StatistikyEntity implements HasID {
    private Integer idPoslanec;
    private Integer pocetSlov;
    private Integer sentiment;
    private PoslanecEntity poslanecByIdPoslanec;
    private Collection<TopSlovaEntity> topSlovaByIdPoslanec;

    public StatistikyEntity() {
    }

    public StatistikyEntity(PoslanecEntity poslanecEntity, Integer pocetSlov, Integer sentiment) {
        this.poslanecByIdPoslanec = poslanecEntity;
        this.pocetSlov = pocetSlov;
        this.sentiment = sentiment;
    }

    public StatistikyEntity(Integer idPoslanec, Integer pocetSlov, Integer sentiment) {
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
        StatistikyEntity that = (StatistikyEntity) o;
        return Objects.equals(idPoslanec, that.idPoslanec) &&
                Objects.equals(pocetSlov, that.pocetSlov) &&
                Objects.equals(sentiment, that.sentiment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPoslanec, pocetSlov, sentiment);
    }

    @Override
    public Integer takeID() {
        return getIdPoslanec();
    }

    @Override
    public void pushID(Integer id) {
        setIdPoslanec(id);
    }

    @OneToOne
    @JoinColumn(name = "id_poslanec", referencedColumnName = "id_poslanec", nullable = false)
    public PoslanecEntity getPoslanecByIdPoslanec() {
        return poslanecByIdPoslanec;
    }

    public void setPoslanecByIdPoslanec(PoslanecEntity poslanecByIdPoslanec) {
        this.poslanecByIdPoslanec = poslanecByIdPoslanec;
    }

    @OneToMany(mappedBy = "statistikyByIdPoslanec", cascade = CascadeType.ALL, orphanRemoval = true)
    public Collection<TopSlovaEntity> getTopSlovaByIdPoslanec() {
        return topSlovaByIdPoslanec;
    }

    public void setTopSlovaByIdPoslanec(Collection<TopSlovaEntity> topSlovaByIdPoslanec) {
        this.topSlovaByIdPoslanec = topSlovaByIdPoslanec;
    }
}
