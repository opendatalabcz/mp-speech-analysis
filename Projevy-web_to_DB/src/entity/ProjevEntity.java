package entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "projev", schema = "FdCPKNUIYW")
public class ProjevEntity {
    private int idProjev;
    private int idPoslanec;
    private int idBod;
    private String text;
    private int delka;
    private PoslanecEntity poslanecByIdPoslanec;
    private BodEntity bodByIdBod;

    @Id
    @Column(name = "id_projev")
    public int getIdProjev() {
        return idProjev;
    }

    public void setIdProjev(int idProjev) {
        this.idProjev = idProjev;
    }

    @Basic
    @Column(name = "id_poslanec")
    public int getIdPoslanec() {
        return idPoslanec;
    }

    public void setIdPoslanec(int idPoslanec) {
        this.idPoslanec = idPoslanec;
    }

    @Basic
    @Column(name = "id_bod")
    public int getIdBod() {
        return idBod;
    }

    public void setIdBod(int idBod) {
        this.idBod = idBod;
    }

    @Basic
    @Column(name = "text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Basic
    @Column(name = "delka")
    public int getDelka() {
        return delka;
    }

    public void setDelka(int delka) {
        this.delka = delka;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjevEntity that = (ProjevEntity) o;
        return idProjev == that.idProjev &&
                idPoslanec == that.idPoslanec &&
                idBod == that.idBod &&
                delka == that.delka &&
                Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProjev, idPoslanec, idBod, text, delka);
    }

    @ManyToOne
    @JoinColumn(name = "id_poslanec", referencedColumnName = "id_poslanec", nullable = false)
    public PoslanecEntity getPoslanecByIdPoslanec() {
        return poslanecByIdPoslanec;
    }

    public void setPoslanecByIdPoslanec(PoslanecEntity poslanecByIdPoslanec) {
        this.poslanecByIdPoslanec = poslanecByIdPoslanec;
    }

    @ManyToOne
    @JoinColumn(name = "id_bod", referencedColumnName = "id_bod", nullable = false)
    public BodEntity getBodByIdBod() {
        return bodByIdBod;
    }

    public void setBodByIdBod(BodEntity bodByIdBod) {
        this.bodByIdBod = bodByIdBod;
    }
}
