package poslanciDB.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "bod", schema = "main")
public class BodEntity implements HasID {
    private Integer idBod;
    private String text;
    private Integer cisloSchuze;
    private Date datum;
    private Collection<ProjevEntity> projevsByIdBod;
    private OrganyEntity organyByIdOrganObdobi;

    public BodEntity() {
    }

    public BodEntity(Integer idBod, String text, Integer cisloSchuze, Date datum, OrganyEntity organyEntity) {
        this.idBod = idBod;
        this.text = text;
        this.cisloSchuze = cisloSchuze;
        this.datum = datum;
        this.organyByIdOrganObdobi = organyEntity;
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
        return Objects.equals(idBod, bodEntity.idBod) &&
                Objects.equals(text, bodEntity.text) &&
                Objects.equals(cisloSchuze, bodEntity.cisloSchuze) &&
                Objects.equals(datum, bodEntity.datum) &&
                Objects.equals(organyByIdOrganObdobi, bodEntity.organyByIdOrganObdobi);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idBod, text, cisloSchuze, datum, organyByIdOrganObdobi);
    }

    @OneToMany(mappedBy = "bodByIdBod", cascade = {CascadeType.REMOVE, CascadeType.DETACH, CascadeType.REFRESH})
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

    @ManyToOne
    @JoinColumn(name = "id_organ_obdobi", referencedColumnName = "id_organ", nullable = false)
    public OrganyEntity getOrganyByIdOrganObdobi() {
        return organyByIdOrganObdobi;
    }

    public void setOrganyByIdOrganObdobi(OrganyEntity organyByIdOrganObdobi) {
        this.organyByIdOrganObdobi = organyByIdOrganObdobi;
    }
}
