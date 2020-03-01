package entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "projev", schema = "dbo", catalog = "Poslanci")
public class ProjevEntity implements HasID {
    private Integer idProjev;
    private String text;
    private Integer delka;
    private Integer poradi;
    private ProjevStatistikyEntity projevStatistikyByIdProjev;
    private PoslanecEntity poslanecByIdPoslanec;
    private BodEntity bodByIdBod;
    private Collection<SlovoEntity> slovosByIdProjev;

    public ProjevEntity() {
    }

    public ProjevEntity(Integer idProjev, String text, Integer delka, Integer poradi,
                        PoslanecEntity poslanecByIdPoslanec, BodEntity bodByIdBod) {
        this.idProjev = idProjev;
        this.text = text;
        this.delka = delka;
        this.poradi = poradi;
        this.poslanecByIdPoslanec = poslanecByIdPoslanec;
        this.bodByIdBod = bodByIdBod;

    }

    @Id
    @Column(name = "id_projev")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "projev_generator")
    @SequenceGenerator(name="projev_generator", sequenceName = "projev_seq", allocationSize=500, initialValue = 1)
    public Integer getIdProjev() {
        return idProjev;
    }

    public void setIdProjev(Integer idProjev) {
        this.idProjev = idProjev;
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
    public Integer getDelka() {
        return delka;
    }

    public void setDelka(Integer delka) {
        this.delka = delka;
    }

    @Basic
    @Column(name = "poradi")
    public Integer getPoradi() {
        return poradi;
    }

    public void setPoradi(Integer poradi) {
        this.poradi = poradi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjevEntity that = (ProjevEntity) o;
        return Objects.equals(idProjev, that.idProjev) &&
                Objects.equals(text, that.text) &&
                Objects.equals(delka, that.delka) &&
                Objects.equals(poradi, that.poradi);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProjev, text, delka, poradi);
    }

    @OneToOne(mappedBy = "projevByIdProjev", cascade = CascadeType.ALL, orphanRemoval = true)
    public ProjevStatistikyEntity getProjevStatistikyByIdProjev() {
        return projevStatistikyByIdProjev;
    }

    public void setProjevStatistikyByIdProjev(ProjevStatistikyEntity projevStatistikyByIdProjev) {
        this.projevStatistikyByIdProjev = projevStatistikyByIdProjev;
    }

    @ManyToOne
    @JoinColumn(name = "id_poslanec", referencedColumnName = "id_poslanec")
    public PoslanecEntity getPoslanecByIdPoslanec() {
        return poslanecByIdPoslanec;
    }

    public void setPoslanecByIdPoslanec(PoslanecEntity poslanecByIdPoslanec) {
        this.poslanecByIdPoslanec = poslanecByIdPoslanec;
    }

    @ManyToOne
    @JoinColumn(name = "id_bod", referencedColumnName = "id_bod")
    public BodEntity getBodByIdBod() {
        return bodByIdBod;
    }

    public void setBodByIdBod(BodEntity bodByIdBod) {
        this.bodByIdBod = bodByIdBod;
    }

    @OneToMany(mappedBy = "projevByIdProjev", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    public Collection<SlovoEntity> getSlovosByIdProjev() {
        return slovosByIdProjev;
    }

    public void setSlovosByIdProjev(Collection<SlovoEntity> slovosByIdProjev) {
        this.slovosByIdProjev = slovosByIdProjev;
    }

    @Override
    public Integer takeID() {
        return getIdProjev();
    }

    @Override
    public void pushID(Integer id) {
        setIdProjev(id);
    }
}
