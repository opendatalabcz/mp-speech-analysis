package entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "projev", schema = "FdCPKNUIYW", catalog = "")
public class ProjevEntity implements HasID {
    private Integer idProjev;
    //private Integer idPoslanec;
    //private Integer idBod;
    private String text;
    private Integer delka;
    private Integer poradi;
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
    @GeneratedValue(strategy=GenerationType.IDENTITY)
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
    public Integer getPoradi() { return poradi; }

    public void setPoradi(Integer poradi) { this.poradi = poradi; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjevEntity that = (ProjevEntity) o;
        return idProjev == that.idProjev &&
                /*idPoslanec == that.idPoslanec &&
                idBod == that.idBod &&*/
                delka == that.delka &&
                Objects.equals(text, that.text) &&
                Objects.equals(poslanecByIdPoslanec, that.poslanecByIdPoslanec) &&
                Objects.equals(bodByIdBod, that.bodByIdBod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProjev, text, delka, poslanecByIdPoslanec, bodByIdBod);
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

    @Override
    public Integer takeID() {
        return getIdProjev();
    }

    @Override
    public void pushID(Integer id) {
        setIdProjev(id);
    }

    @OneToMany(mappedBy = "projevByIdProjev")
    public Collection<SlovoEntity> getSlovosByIdProjev() {
        return slovosByIdProjev;
    }

    public void setSlovosByIdProjev(Collection<SlovoEntity> slovosByIdProjev) {
        this.slovosByIdProjev = slovosByIdProjev;
    }
}
