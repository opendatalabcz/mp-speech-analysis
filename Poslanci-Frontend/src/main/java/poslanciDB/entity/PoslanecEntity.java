package poslanciDB.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "poslanec", schema = "main", catalog = "")
public class PoslanecEntity implements HasID{
    private Integer idPoslanec;
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
    private OrganyEntity organyByIdKraj;
    private OrganyEntity organyByIdKandidatka;
    private OrganyEntity organyByIdObdobi;
    private Collection<ProjevEntity> projevsByIdPoslanec;
    private PoslanecStatistikyEntity poslanecStatistikyByIdPoslanec;

    public PoslanecEntity() {
    }

    public PoslanecEntity(Integer idPoslanec) {
        this.idPoslanec = idPoslanec;
    }

    public PoslanecEntity(Integer idPoslanec, OrganyEntity organyByIdKraj, OrganyEntity organyByIdKandidatka, OrganyEntity organyByIdObdobi,
                          String web, String ulice, String obec, String psc, String email, String telefon, String fax,
                          String pspTelefon, String facebook, Integer foto, OsobyEntity osobyByIdOsoba) {
        this.idPoslanec = idPoslanec;
        this.organyByIdKraj = organyByIdKraj;
        this.organyByIdKandidatka = organyByIdKandidatka;
        this.organyByIdObdobi = organyByIdObdobi;
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

    public PoslanecEntity(Integer idPoslanec, OrganyEntity idKraj, OrganyEntity idKandidatka, OrganyEntity idObdobi,
                          String web, String ulice, String obec, String psc, String email, String telefon, String fax,
                          String pspTelefon, String facebook, Integer foto) {
        this.idPoslanec = idPoslanec;
        this.organyByIdKraj = idKraj;
        this.organyByIdKandidatka = idKandidatka;
        this.organyByIdObdobi = idObdobi;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PoslanecEntity that = (PoslanecEntity) o;
        return Objects.equals(idPoslanec, that.idPoslanec) &&
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
        return Objects.hash(idPoslanec, web, ulice, obec, psc, email, telefon, fax, pspTelefon, facebook, foto);
    }

    @ManyToOne
    @JoinColumn(name = "id_osoba", referencedColumnName = "id_osoba")
    public OsobyEntity getOsobyByIdOsoba() {
        return osobyByIdOsoba;
    }

    public void setOsobyByIdOsoba(OsobyEntity osobyByIdOsoba) {
        this.osobyByIdOsoba = osobyByIdOsoba;
    }

    @ManyToOne
    @JoinColumn(name = "id_kraj", referencedColumnName = "id_organ")
    public OrganyEntity getOrganyByIdKraj() {
        return organyByIdKraj;
    }

    public void setOrganyByIdKraj(OrganyEntity organyByIdKraj) {
        this.organyByIdKraj = organyByIdKraj;
    }

    @ManyToOne
    @JoinColumn(name = "id_kandidatka", referencedColumnName = "id_organ")
    public OrganyEntity getOrganyByIdKandidatka() {
        return organyByIdKandidatka;
    }

    public void setOrganyByIdKandidatka(OrganyEntity organyByIdKandidatka) {
        this.organyByIdKandidatka = organyByIdKandidatka;
    }

    @ManyToOne
    @JoinColumn(name = "id_obdobi", referencedColumnName = "id_organ")
    public OrganyEntity getOrganyByIdObdobi() {
        return organyByIdObdobi;
    }

    public void setOrganyByIdObdobi(OrganyEntity organyByIdObdobi) {
        this.organyByIdObdobi = organyByIdObdobi;
    }

    @OneToMany(mappedBy = "poslanecByIdPoslanec")
    public Collection<ProjevEntity> getProjevsByIdPoslanec() {
        return projevsByIdPoslanec;
    }

    public void setProjevsByIdPoslanec(Collection<ProjevEntity> projevsByIdPoslanec) {
        this.projevsByIdPoslanec = projevsByIdPoslanec;
    }

    @Override
    public Integer takeID() {
        return getIdPoslanec();
    }

    @Override
    public void pushID(Integer id) {
        setIdPoslanec(id);
    }

    @OneToOne(mappedBy = "poslanecByIdPoslanec")
    public PoslanecStatistikyEntity getPoslanecStatistikyByIdPoslanec() {
        return poslanecStatistikyByIdPoslanec;
    }

    public void setPoslanecStatistikyByIdPoslanec(PoslanecStatistikyEntity poslanecStatistikyByIdPoslanec) {
        this.poslanecStatistikyByIdPoslanec = poslanecStatistikyByIdPoslanec;
    }
}
