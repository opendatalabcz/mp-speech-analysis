package poslanciDB.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "projev", schema = "main")
public class ProjevEntity implements HasID{
    private Integer idProjev;
    private String text;
    private Integer pocetSlov;
    private Integer poradi;
    private PoslanecEntity poslanecByIdPoslanec;
    private BodEntity bodByIdBod;
    private ProjevStatistikyEntity projevStatistikyByIdProjev;
    private Collection<SlovoEntity> slovosByIdProjev;

    public ProjevEntity() {
    }

    public ProjevEntity(Integer idProjev, String text, Integer pocetSlov, Integer poradi,
                        PoslanecEntity poslanecByIdPoslanec, BodEntity bodByIdBod) {
        this.idProjev = idProjev;
        this.text = text;
        this.pocetSlov = pocetSlov;
        this.poradi = poradi;
        this.poslanecByIdPoslanec = poslanecByIdPoslanec;
        this.bodByIdBod = bodByIdBod;

    }

    @Id
    @Column(name = "id_projev")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(name = "pocet_slov")
    public Integer getPocetSlov() {
        return pocetSlov;
    }

    public void setPocetSlov(Integer pocetSlov) {
        this.pocetSlov = pocetSlov;
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
                Objects.equals(pocetSlov, that.pocetSlov) &&
                Objects.equals(poradi, that.poradi);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProjev, text, pocetSlov, poradi);
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

    @OneToOne(mappedBy = "projevByIdProjev")
    public ProjevStatistikyEntity getProjevStatistikyByIdProjev() {
        return projevStatistikyByIdProjev;
    }

    public void setProjevStatistikyByIdProjev(ProjevStatistikyEntity projevStatistikyByIdProjev) {
        this.projevStatistikyByIdProjev = projevStatistikyByIdProjev;
    }

    @OneToMany(mappedBy = "projevByIdProjev")
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
