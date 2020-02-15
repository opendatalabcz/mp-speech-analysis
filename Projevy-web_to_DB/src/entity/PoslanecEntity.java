package entity;

import service.OsobyEntityService;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "poslanec", schema = "FdCPKNUIYW")
public class PoslanecEntity implements HasID {
    private Integer idPoslanec;
    private Integer idKraj;
    private Integer idKandidatka;
    private Integer idObdobi;
    private String web;
    private String ulice;
    private String obec;
    private String psc;
    private String email;
    private String telefon;
    private String fax;
    private String pspTelefon;
    private String facebook;
    private Integer foto;
    private OsobyEntity osobyByIdOsoba;
    private Collection<ProjevEntity> projevsByIdPoslanec;
    private StatistikyEntity statistikyByIdPoslanec;

    public PoslanecEntity() {
    }
    public PoslanecEntity(Integer idPoslanec) {
        this.idPoslanec = idPoslanec;
    }

    public PoslanecEntity(Integer idPoslanec, Integer idKraj, Integer idKandidatka, Integer idObdobi,
                          String web, String ulice, String obec, String psc, String email, String telefon, String fax,
                          String pspTelefon, String facebook, Integer foto, OsobyEntity osobyByIdOsoba) {
        this.idPoslanec = idPoslanec;
        this.idKraj = idKraj;
        this.idKandidatka = idKandidatka;
        this.idObdobi = idObdobi;
        this.web = web;
        this.ulice = ulice;
        this.obec = obec;
        this.psc = psc;
        this.email = email;
        this.telefon = telefon;
        this.fax = fax;
        this.pspTelefon = pspTelefon;
        this.facebook = facebook;
        this.foto = foto;
        this.osobyByIdOsoba = osobyByIdOsoba;
    }

    public PoslanecEntity(Integer idPoslanec, Integer idKraj, Integer idKandidatka, Integer idObdobi,
                          String web, String ulice, String obec, String psc, String email, String telefon, String fax,
                          String pspTelefon, String facebook, Integer foto) {
        this.idPoslanec = idPoslanec;
        this.idKraj = idKraj;
        this.idKandidatka = idKandidatka;
        this.idObdobi = idObdobi;
        this.web = web;
        this.ulice = ulice;
        this.obec = obec;
        this.psc = psc;
        this.email = email;
        this.telefon = telefon;
        this.fax = fax;
        this.pspTelefon = pspTelefon;
        this.facebook = facebook;
        this.foto = foto;
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
    @Column(name = "id_kraj")
    public Integer getIdKraj() {
        return idKraj;
    }

    public void setIdKraj(Integer idKraj) {
        this.idKraj = idKraj;
    }

    @Basic
    @Column(name = "id_kandidatka")
    public Integer getIdKandidatka() {
        return idKandidatka;
    }

    public void setIdKandidatka(Integer idKandidatka) {
        this.idKandidatka = idKandidatka;
    }

    @Basic
    @Column(name = "id_obdobi")
    public Integer getIdObdobi() {
        return idObdobi;
    }

    public void setIdObdobi(Integer idObdobi) {
        this.idObdobi = idObdobi;
    }

    @Basic
    @Column(name = "web")
    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    @Basic
    @Column(name = "ulice")
    public String getUlice() {
        return ulice;
    }

    public void setUlice(String ulice) {
        this.ulice = ulice;
    }

    @Basic
    @Column(name = "obec")
    public String getObec() {
        return obec;
    }

    public void setObec(String obec) {
        this.obec = obec;
    }

    @Basic
    @Column(name = "psc")
    public String getPsc() {
        return psc;
    }

    public void setPsc(String psc) {
        this.psc = psc;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "telefon")
    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    @Basic
    @Column(name = "fax")
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @Basic
    @Column(name = "psp_telefon")
    public String getPspTelefon() {
        return pspTelefon;
    }

    public void setPspTelefon(String pspTelefon) {
        this.pspTelefon = pspTelefon;
    }

    @Basic
    @Column(name = "facebook")
    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    @Basic
    @Column(name = "foto")
    public Integer getFoto() {
        return foto;
    }

    public void setFoto(Integer foto) {
        this.foto = foto;
    }

    @ManyToOne
    @JoinColumn(name = "id_osoba", referencedColumnName = "id_osoba", nullable = false)
    public OsobyEntity getOsobyByIdOsoba() {
        return osobyByIdOsoba;
    }

    public void setOsobyByIdOsoba(OsobyEntity osobyByIdOsoba) {
        this.osobyByIdOsoba = osobyByIdOsoba;
    }

    @Override
    public Integer takeID() { return getIdPoslanec(); }

    @Override
    public void pushID(Integer id) { setIdPoslanec(id); }

    @OneToMany(mappedBy = "poslanecByIdPoslanec")
    public Collection<ProjevEntity> getProjevsByIdPoslanec() {
        return projevsByIdPoslanec;
    }

    public void setProjevsByIdPoslanec(Collection<ProjevEntity> projevsByIdPoslanec) {
        this.projevsByIdPoslanec = projevsByIdPoslanec;
    }

    @OneToOne(mappedBy = "poslanecByIdPoslanec")
    public StatistikyEntity getStatistikyByIdPoslanec() {
        return statistikyByIdPoslanec;
    }

    public void setStatistikyByIdPoslanec(StatistikyEntity statistikyByIdPoslanec) {
        this.statistikyByIdPoslanec = statistikyByIdPoslanec;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PoslanecEntity that = (PoslanecEntity) o;
        return idPoslanec.equals(that.idPoslanec) &&
                Objects.equals(idKraj, that.idKraj) &&
                Objects.equals(idKandidatka, that.idKandidatka) &&
                Objects.equals(idObdobi, that.idObdobi) &&
                Objects.equals(web, that.web) &&
                Objects.equals(ulice, that.ulice) &&
                Objects.equals(obec, that.obec) &&
                Objects.equals(psc, that.psc) &&
                Objects.equals(email, that.email) &&
                Objects.equals(telefon, that.telefon) &&
                Objects.equals(fax, that.fax) &&
                Objects.equals(pspTelefon, that.pspTelefon) &&
                Objects.equals(facebook, that.facebook) &&
                Objects.equals(foto, that.foto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPoslanec, idKraj, idKandidatka, idObdobi, web, ulice, obec, psc, email, telefon, fax, pspTelefon, facebook, foto);
    }

}
