package poslanciDB.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "osoby", schema = "main")
public class OsobyEntity implements HasID {
    private Integer idOsoba;
    private String pred;
    private String prijmeni;
    private String jmeno;
    private String za;
    private Date narozeni;
    private String pohlavi;
    private Date zmena;
    private Date umrti;
    private Collection<PoslanecEntity> poslanecsByIdOsoba;

    public OsobyEntity() {
    }

    public OsobyEntity(Integer idOsoba, String pred, String jmeno, String prijmeni, String za, Date narozeni,
                       String pohlavi, Date zmena, Date umrti, Collection<PoslanecEntity> poslanecsByIdOsoba) {
        setIdOsoba(idOsoba);
        setPred(pred);
        setJmeno(jmeno);
        setPrijmeni(prijmeni);
        setZa(za);
        setNarozeni(narozeni);
        setPohlavi(pohlavi);
        setZmena(zmena);
        setUmrti(umrti);
        setPoslanecsByIdOsoba(poslanecsByIdOsoba);
    }

    public OsobyEntity(Integer idOsoba, String pred, String jmeno, String prijmeni, String za, Date narozeni,
                       String pohlavi, Date zmena, Date umrti) {
        setIdOsoba(idOsoba);
        setPred(pred);
        setJmeno(jmeno);
        setPrijmeni(prijmeni);
        setZa(za);
        setNarozeni(narozeni);
        setPohlavi(pohlavi);
        setZmena(zmena);
        setUmrti(umrti);
        setPoslanecsByIdOsoba(null);
    }

    @Id
    @Column(name = "id_osoba")
    public Integer getIdOsoba() {
        return idOsoba;
    }

    public void setIdOsoba(Integer idOsoba) {
        this.idOsoba = idOsoba;
    }

    @Basic
    @Column(name = "pred")
    public String getPred() {
        return pred;
    }

    public void setPred(String pred) {
        this.pred = pred;
    }

    @Basic
    @Column(name = "prijmeni")
    public String getPrijmeni() {
        return prijmeni;
    }

    public void setPrijmeni(String prijmeni) {
        this.prijmeni = prijmeni;
    }

    @Basic
    @Column(name = "jmeno")
    public String getJmeno() {
        return jmeno;
    }

    public void setJmeno(String jmeno) {
        this.jmeno = jmeno;
    }

    @Basic
    @Column(name = "za")
    public String getZa() {
        return za;
    }

    public void setZa(String za) {
        this.za = za;
    }

    @Basic
    @Column(name = "narozeni")
    public Date getNarozeni() {
        return narozeni;
    }

    public void setNarozeni(Date narozeni) {
        this.narozeni = narozeni;
    }

    @Basic
    @Column(name = "pohlavi")
    public String getPohlavi() {
        return pohlavi;
    }

    public void setPohlavi(String pohlavi) {
        this.pohlavi = pohlavi;
    }

    @Basic
    @Column(name = "zmena")
    public Date getZmena() {
        return zmena;
    }

    public void setZmena(Date zmena) {
        this.zmena = zmena;
    }

    @Basic
    @Column(name = "umrti")
    public Date getUmrti() {
        return umrti;
    }

    public void setUmrti(Date umrti) {
        this.umrti = umrti;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OsobyEntity that = (OsobyEntity) o;
        return Objects.equals(idOsoba, that.idOsoba) &&
                Objects.equals(pred, that.pred) &&
                Objects.equals(prijmeni, that.prijmeni) &&
                Objects.equals(jmeno, that.jmeno) &&
                Objects.equals(za, that.za) &&
                Objects.equals(narozeni, that.narozeni) &&
                Objects.equals(pohlavi, that.pohlavi) &&
                Objects.equals(zmena, that.zmena) &&
                Objects.equals(umrti, that.umrti) &&
                Objects.equals(poslanecsByIdOsoba, that.poslanecsByIdOsoba);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOsoba, pred, prijmeni, jmeno, za, narozeni, pohlavi, zmena, umrti);
    }

    @Override
    public String toString() {
        return pred + ' ' + jmeno + ' ' + prijmeni + ' ' + za;
    }

    @OneToMany(mappedBy = "osobyByIdOsoba", cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.PERSIST})
    public Collection<PoslanecEntity> getPoslanecsByIdOsoba() {
        return poslanecsByIdOsoba;
    }

    public void setPoslanecsByIdOsoba(Collection<PoslanecEntity> poslanecsByIdOsoba) {
        this.poslanecsByIdOsoba = poslanecsByIdOsoba;
    }

    @Override
    public Integer takeID() {
        return getIdOsoba();
    }

    @Override
    public void pushID(Integer id) {
        setIdOsoba(id);
    }
}
