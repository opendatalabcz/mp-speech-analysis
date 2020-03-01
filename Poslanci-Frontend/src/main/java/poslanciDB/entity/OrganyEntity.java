package poslanciDB.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "organy", schema = "main")
public class OrganyEntity implements HasID {
    private Integer idOrgan;
    private Integer organIdOrgan;
    private String zkratka;
    private String nazevOrganuCz;
    private String nazevOrganuEn;
    private Date odOrgan;
    private Date doOrgan;
    private Integer priorita;
    private Integer clOrganBase;
    private TypOrganuEntity typOrganuByIdTypOrganu;
    private Collection<PoslanecEntity> poslanecsKrajByIdOrgan;
    private Collection<PoslanecEntity> poslanecsKandidatkaByIdOrgan;
    private Collection<PoslanecEntity> poslanecsObdobiByIdOrgan;

    public OrganyEntity() {
    }

    public OrganyEntity(Integer idOrgan, Integer organIdOrgan, String zkratka, String nazevOrganuCz, String nazevOrganuEn,
                        Date odOrgan, Date doOrgan, Integer priorita, Integer clOrganBase, TypOrganuEntity typOrganuEntity) {
        this.idOrgan = idOrgan;
        this.organIdOrgan = organIdOrgan;
        this.zkratka = zkratka;
        this.nazevOrganuCz = nazevOrganuCz;
        this.nazevOrganuEn = nazevOrganuEn;
        this.odOrgan = odOrgan;
        this.doOrgan = doOrgan;
        this.priorita = priorita;
        this.clOrganBase = clOrganBase;
        this.typOrganuByIdTypOrganu = typOrganuEntity;
    }

    @Id
    @Column(name = "id_organ")
    public Integer getIdOrgan() {
        return idOrgan;
    }

    public void setIdOrgan(Integer idOrgan) {
        this.idOrgan = idOrgan;
    }

    @Basic
    @Column(name = "organ_id_organ")
    public Integer getOrganIdOrgan() {
        return organIdOrgan;
    }

    public void setOrganIdOrgan(Integer organIdOrgan) {
        this.organIdOrgan = organIdOrgan;
    }

    @Basic
    @Column(name = "zkratka")
    public String getZkratka() {
        return zkratka;
    }

    public void setZkratka(String zkratka) {
        this.zkratka = zkratka;
    }

    @Basic
    @Column(name = "nazev_organu_cz")
    public String getNazevOrganuCz() {
        return nazevOrganuCz;
    }

    public void setNazevOrganuCz(String nazevOrganuCz) {
        this.nazevOrganuCz = nazevOrganuCz;
    }

    @Basic
    @Column(name = "nazev_organu_en")
    public String getNazevOrganuEn() {
        return nazevOrganuEn;
    }

    public void setNazevOrganuEn(String nazevOrganuEn) {
        this.nazevOrganuEn = nazevOrganuEn;
    }

    @Basic
    @Column(name = "od_organ")
    public Date getOdOrgan() {
        return odOrgan;
    }

    public void setOdOrgan(Date odOrgan) {
        this.odOrgan = odOrgan;
    }

    @Basic
    @Column(name = "do_organ")
    public Date getDoOrgan() {
        return doOrgan;
    }

    public void setDoOrgan(Date doOrgan) {
        this.doOrgan = doOrgan;
    }

    @Basic
    @Column(name = "priorita")
    public Integer getPriorita() {
        return priorita;
    }

    public void setPriorita(Integer priorita) {
        this.priorita = priorita;
    }

    @Basic
    @Column(name = "cl_organ_base")
    public Integer getClOrganBase() {
        return clOrganBase;
    }

    public void setClOrganBase(Integer clOrganBase) {
        this.clOrganBase = clOrganBase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganyEntity that = (OrganyEntity) o;
        return Objects.equals(idOrgan, that.idOrgan) &&
                Objects.equals(organIdOrgan, that.organIdOrgan) &&
                Objects.equals(zkratka, that.zkratka) &&
                Objects.equals(nazevOrganuCz, that.nazevOrganuCz) &&
                Objects.equals(nazevOrganuEn, that.nazevOrganuEn) &&
                Objects.equals(odOrgan, that.odOrgan) &&
                Objects.equals(doOrgan, that.doOrgan) &&
                Objects.equals(priorita, that.priorita) &&
                Objects.equals(clOrganBase, that.clOrganBase);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOrgan, organIdOrgan, zkratka, nazevOrganuCz, nazevOrganuEn, odOrgan, doOrgan, priorita, clOrganBase);
    }

    @ManyToOne
    @JoinColumn(name = "id_typ_organu", referencedColumnName = "id_typ_org")
    public TypOrganuEntity getTypOrganuByIdTypOrganu() {
        return typOrganuByIdTypOrganu;
    }

    public void setTypOrganuByIdTypOrganu(TypOrganuEntity typOrganuByIdTypOrganu) {
        this.typOrganuByIdTypOrganu = typOrganuByIdTypOrganu;
    }

    @OneToMany(mappedBy = "organyByIdKraj")
    public Collection<PoslanecEntity> getPoslanecsKrajByIdOrgan() {
        return poslanecsKrajByIdOrgan;
    }

    public void setPoslanecsKrajByIdOrgan(Collection<PoslanecEntity> poslanecsKrajByIdOrgan) {
        this.poslanecsKrajByIdOrgan = poslanecsKrajByIdOrgan;
    }

    @OneToMany(mappedBy = "organyByIdKandidatka")
    public Collection<PoslanecEntity> getPoslanecsKandidatkaByIdOrgan() {
        return poslanecsKandidatkaByIdOrgan;
    }

    public void setPoslanecsKandidatkaByIdOrgan(Collection<PoslanecEntity> poslanecsKandidatkaByIdOrgan) {
        this.poslanecsKandidatkaByIdOrgan = poslanecsKandidatkaByIdOrgan;
    }

    @OneToMany(mappedBy = "organyByIdObdobi")
    public Collection<PoslanecEntity> getPoslanecsObdobiByIdOrgan() {
        return poslanecsObdobiByIdOrgan;
    }

    public void setPoslanecsObdobiByIdOrgan(Collection<PoslanecEntity> poslanecsObdobiByIdOrgan) {
        this.poslanecsObdobiByIdOrgan = poslanecsObdobiByIdOrgan;
    }

    @Override
    public Integer takeID() {
        return getIdOrgan();
    }

    @Override
    public void pushID(Integer id) {
        setIdOrgan(id);
    }
}
