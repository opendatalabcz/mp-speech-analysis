package poslanciDB.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "poslanec_statistiky_mesic", schema = "main")
public class PoslanecStatistikyMesicEntity implements HasID {
    private Integer idMesic;
    private Date mesic;
    private Integer pocetSlov;
    private Integer pocetPosSlov;
    private Integer pocetNegSlov;
    private Double sentiment;
    private PoslanecStatistikyEntity poslanecStatistikyByIdPoslanec;

    public PoslanecStatistikyMesicEntity() {
    }

    public PoslanecStatistikyMesicEntity(Date mesic, Integer pocetSlov, Integer pocetPosSlov, Integer pocetNegSlov,
                                         Double sentiment, PoslanecStatistikyEntity poslanecStatistikyByIdPoslanec) {
        this.mesic = mesic;
        this.pocetSlov = pocetSlov;
        this.pocetPosSlov = pocetPosSlov;
        this.pocetNegSlov = pocetNegSlov;
        this.sentiment = sentiment;
        this.poslanecStatistikyByIdPoslanec = poslanecStatistikyByIdPoslanec;
    }

    @Id
    @Column(name = "id_mesic")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getIdMesic() {
        return idMesic;
    }

    public void setIdMesic(Integer idMesic) {
        this.idMesic = idMesic;
    }

    @Basic
    @Column(name = "mesic")
    public Date getMesic() {
        return mesic;
    }

    public void setMesic(Date mesic) {
        this.mesic = mesic;
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
        PoslanecStatistikyMesicEntity that = (PoslanecStatistikyMesicEntity) o;
        return Objects.equals(idMesic, that.idMesic) &&
                Objects.equals(mesic, that.mesic) &&
                Objects.equals(pocetSlov, that.pocetSlov) &&
                Objects.equals(sentiment, that.sentiment) &&
                Objects.equals(pocetPosSlov, that.pocetPosSlov) &&
                Objects.equals(pocetNegSlov, that.pocetNegSlov);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMesic, mesic, pocetSlov, pocetPosSlov, pocetNegSlov, sentiment);
    }

    @ManyToOne
    @JoinColumn(name = "id_poslanec", referencedColumnName = "id_poslanec", nullable = false)
    public PoslanecStatistikyEntity getPoslanecStatistikyByIdPoslanec() {
        return poslanecStatistikyByIdPoslanec;
    }

    public void setPoslanecStatistikyByIdPoslanec(PoslanecStatistikyEntity poslanecStatistikyByIdPoslanec) {
        this.poslanecStatistikyByIdPoslanec = poslanecStatistikyByIdPoslanec;
    }

    @Override
    public Integer takeID() {
        return getIdMesic();
    }

    @Override
    public void pushID(Integer id) {
        setIdMesic(id);
    }
}
