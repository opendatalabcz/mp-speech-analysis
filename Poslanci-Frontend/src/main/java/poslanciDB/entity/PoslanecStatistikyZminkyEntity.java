package poslanciDB.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "poslanec_statistiky_zminky", schema = "main")
public class PoslanecStatistikyZminkyEntity implements HasID {
    private Integer idZminky;
    private Integer pocetVyskytu;
    private PoslanecStatistikyEntity poslanecStatistikyByIdPoslanecRecnik;
    private PoslanecEntity poslanecByIdPoslanecZmineny;

    public PoslanecStatistikyZminkyEntity() {
    }

    public PoslanecStatistikyZminkyEntity(Integer pocetVyskytu,
                                          PoslanecStatistikyEntity poslanecStatistikyEntityRecnik,
                                          PoslanecEntity poslanecEntityZmineny) {
        this.idZminky = null;
        this.pocetVyskytu = pocetVyskytu;
        this.poslanecStatistikyByIdPoslanecRecnik = poslanecStatistikyEntityRecnik;
        this.poslanecByIdPoslanecZmineny = poslanecEntityZmineny;
    }

    @Id
    @Column(name = "id_zminky")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getIdZminky() {
        return idZminky;
    }

    public void setIdZminky(Integer idZminky) {
        this.idZminky = idZminky;
    }

    @Basic
    @Column(name = "pocet_vyskytu")
    public Integer getPocetVyskytu() {
        return pocetVyskytu;
    }

    public void setPocetVyskytu(Integer pocetVyskytu) {
        this.pocetVyskytu = pocetVyskytu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PoslanecStatistikyZminkyEntity that = (PoslanecStatistikyZminkyEntity) o;
        return Objects.equals(idZminky, that.idZminky) &&
                Objects.equals(pocetVyskytu, that.pocetVyskytu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idZminky, pocetVyskytu);
    }

    @ManyToOne
    @JoinColumn(name = "id_poslanec_recnik", referencedColumnName = "id_poslanec", nullable = false)
    public PoslanecStatistikyEntity getPoslanecStatistikyByIdPoslanecRecnik() {
        return poslanecStatistikyByIdPoslanecRecnik;
    }

    public void setPoslanecStatistikyByIdPoslanecRecnik(PoslanecStatistikyEntity poslanecStatistikyByIdPoslanecRecnik) {
        this.poslanecStatistikyByIdPoslanecRecnik = poslanecStatistikyByIdPoslanecRecnik;
    }

    @ManyToOne
    @JoinColumn(name = "id_poslanec_zmineny", referencedColumnName = "id_poslanec", nullable = false)
    public PoslanecEntity getPoslanecByIdPoslanecZmineny() {
        return poslanecByIdPoslanecZmineny;
    }

    public void setPoslanecByIdPoslanecZmineny(PoslanecEntity poslanecByIdPoslanecZmineny) {
        this.poslanecByIdPoslanecZmineny = poslanecByIdPoslanecZmineny;
    }

    @Override
    public Integer takeID() {
        return getIdZminky();
    }

    @Override
    public void pushID(Integer id) {
        setIdZminky(id);
    }
}
