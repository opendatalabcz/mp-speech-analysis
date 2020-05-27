package poslanciDB.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "typ_organu", schema = "main")
public class TypOrganuEntity implements HasID {
    private Integer idTypOrg;
    private Integer typIdTypOrg;
    private String nazevTypOrgCz;
    private String nazevTypOrgEn;
    private Integer typOrgObecny;
    private Integer priorita;
    private Collection<OrganyEntity> organiesByIdTypOrg;

    public TypOrganuEntity() {
    }

    public TypOrganuEntity(Integer idTypOrg, Integer typIdTypOrg, String nazevTypOrgCz, String nazevTypOrgEn,
                           Integer typOrgObecny, Integer priorita) {
        this.idTypOrg = idTypOrg;
        this.typIdTypOrg = typIdTypOrg;
        this.nazevTypOrgCz = nazevTypOrgCz;
        this.nazevTypOrgEn = nazevTypOrgEn;
        this.typOrgObecny = typOrgObecny;
        this.priorita = priorita;
    }

    @Id
    @Column(name = "id_typ_org")
    public Integer getIdTypOrg() {
        return idTypOrg;
    }

    public void setIdTypOrg(Integer idTypOrg) {
        this.idTypOrg = idTypOrg;
    }

    @Basic
    @Column(name = "typ_id_typ_org")
    public Integer getTypIdTypOrg() {
        return typIdTypOrg;
    }

    public void setTypIdTypOrg(Integer typIdTypOrg) {
        this.typIdTypOrg = typIdTypOrg;
    }

    @Basic
    @Column(name = "nazev_typ_org_cz")
    public String getNazevTypOrgCz() {
        return nazevTypOrgCz;
    }

    public void setNazevTypOrgCz(String nazevTypOrgCz) {
        this.nazevTypOrgCz = nazevTypOrgCz;
    }

    @Basic
    @Column(name = "nazev_typ_org_en")
    public String getNazevTypOrgEn() {
        return nazevTypOrgEn;
    }

    public void setNazevTypOrgEn(String nazevTypOrgEn) {
        this.nazevTypOrgEn = nazevTypOrgEn;
    }

    @Basic
    @Column(name = "typ_org_obecny")
    public Integer getTypOrgObecny() {
        return typOrgObecny;
    }

    public void setTypOrgObecny(Integer typOrgObecny) {
        this.typOrgObecny = typOrgObecny;
    }

    @Basic
    @Column(name = "priorita")
    public Integer getPriorita() {
        return priorita;
    }

    public void setPriorita(Integer priorita) {
        this.priorita = priorita;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypOrganuEntity that = (TypOrganuEntity) o;
        return Objects.equals(idTypOrg, that.idTypOrg) &&
                Objects.equals(typIdTypOrg, that.typIdTypOrg) &&
                Objects.equals(nazevTypOrgCz, that.nazevTypOrgCz) &&
                Objects.equals(nazevTypOrgEn, that.nazevTypOrgEn) &&
                Objects.equals(typOrgObecny, that.typOrgObecny) &&
                Objects.equals(priorita, that.priorita);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTypOrg, typIdTypOrg, nazevTypOrgCz, nazevTypOrgEn, typOrgObecny, priorita);
    }

    @OneToMany(mappedBy = "typOrganuByIdTypOrganu")
    public Collection<OrganyEntity> getOrganiesByIdTypOrg() {
        return organiesByIdTypOrg;
    }

    public void setOrganiesByIdTypOrg(Collection<OrganyEntity> organiesByIdTypOrg) {
        this.organiesByIdTypOrg = organiesByIdTypOrg;
    }

    @Override
    public Integer takeID() {
        return getIdTypOrg();
    }

    @Override
    public void pushID(Integer id) {
        setIdTypOrg(id);
    }
}
