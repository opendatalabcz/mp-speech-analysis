package poslanciDB.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "zminka", schema = "main")
public class ZminkaEntity implements HasID {
    private Integer idZminka;
    private Integer pocet;
    private ProjevEntity projevByIdProjev;
    private PoslanecEntity poslanecByIdPoslanec;

    public ZminkaEntity() {
    }

    public ZminkaEntity(Integer idZminka, Integer pocet, ProjevEntity projevEntity, PoslanecEntity poslanecEntity) {
        this.idZminka = idZminka;
        this.pocet = pocet;
        this.projevByIdProjev = projevEntity;
        this.poslanecByIdPoslanec = poslanecEntity;
    }

    @Id
    @Column(name = "id_zminka")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getIdZminka() {
        return idZminka;
    }

    public void setIdZminka(Integer idZminka) {
        this.idZminka = idZminka;
    }

    @Basic
    @Column(name = "pocet")
    public Integer getPocet() {
        return pocet;
    }

    public void setPocet(Integer pocet) {
        this.pocet = pocet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZminkaEntity that = (ZminkaEntity) o;
        return Objects.equals(idZminka, that.idZminka) &&
                Objects.equals(pocet, that.pocet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idZminka, pocet);
    }

    @ManyToOne
    @JoinColumn(name = "id_projev", referencedColumnName = "id_projev", nullable = false)
    public ProjevEntity getProjevByIdProjev() {
        return projevByIdProjev;
    }

    public void setProjevByIdProjev(ProjevEntity projevByIdProjev) {
        this.projevByIdProjev = projevByIdProjev;
    }

    @ManyToOne
    @JoinColumn(name = "id_poslanec", referencedColumnName = "id_poslanec", nullable = false)
    public PoslanecEntity getPoslanecByIdPoslanec() {
        return poslanecByIdPoslanec;
    }

    public void setPoslanecByIdPoslanec(PoslanecEntity poslanecByIdPoslanec) {
        this.poslanecByIdPoslanec = poslanecByIdPoslanec;
    }

    @Override
    public Integer takeID() {
        return getIdZminka();
    }

    @Override
    public void pushID(Integer id) {
        setIdZminka(id);
    }
}
