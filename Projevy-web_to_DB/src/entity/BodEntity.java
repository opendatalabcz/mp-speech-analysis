package entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "bod", schema = "dbo", catalog = "Poslanci")
public class BodEntity implements HasID {
    private Integer idBod;
    private String text;
    private Integer cisloSchuze;
    private Date datum;
    private Collection<ProjevEntity> projevsByIdBod;

    public BodEntity() {
    }

    public BodEntity(Integer idBod, String text, Integer cisloSchuze, Date datum) {
        this.idBod = idBod;
        this.text = text;
        this.cisloSchuze = cisloSchuze;
        this.datum = datum;
    }

    @Id
    @Column(name = "id_bod")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Integer getIdBod() {
        return idBod;
    }

    public void setIdBod(Integer idBod) {
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
    @Column(name = "cislo_schuze")
    public Integer getCisloSchuze() {
        return cisloSchuze;
    }

    public void setCisloSchuze(Integer cisloSchuze) {
        this.cisloSchuze = cisloSchuze;
    }

    @Basic
    @Column(name = "datum")
    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BodEntity bodEntity = (BodEntity) o;
        return Objects.equals(text.toString(), bodEntity.text.toString()) &&
                Objects.equals(cisloSchuze, bodEntity.cisloSchuze) &&
                Objects.equals(datum, bodEntity.datum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, cisloSchuze, datum);
    }

    @OneToMany(mappedBy = "bodByIdBod", cascade = CascadeType.ALL, orphanRemoval = true)
    public Collection<ProjevEntity> getProjevsByIdBod() {
        return projevsByIdBod;
    }

    public void setProjevsByIdBod(Collection<ProjevEntity> projevsByIdBod) {
        this.projevsByIdBod = projevsByIdBod;
    }

    @Override
    public Integer takeID() {
        return getIdBod();
    }

    @Override
    public void pushID(Integer id) {
        setIdBod(id);
    }
}
