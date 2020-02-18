package entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "slovo", schema = "FdCPKNUIYW")
public class SlovoEntity implements HasID {
    private Integer idSlovo;
    private Integer slovo;
    private Integer tag;
    private Integer pocetVyskytu;
    private ProjevEntity projevByIdProjev;

    @Id
    @Column(name = "id_slovo")
    public Integer getIdSlovo() {
        return idSlovo;
    }

    public void setIdSlovo(Integer idSlovo) {
        this.idSlovo = idSlovo;
    }

    @Basic
    @Column(name = "slovo")
    public Integer getSlovo() {
        return slovo;
    }

    public void setSlovo(Integer slovo) {
        this.slovo = slovo;
    }

    @Basic
    @Column(name = "tag")
    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }

    @Basic
    @Column(name = "pocet_vyskytu")
    public Integer getPocetVyskytu() {
        return pocetVyskytu;
    }

    public void setPocetVyskytu(Integer pocetVyskytu) {
        this.pocetVyskytu = pocetVyskytu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SlovoEntity that = (SlovoEntity) o;
        return Objects.equals(idSlovo, that.idSlovo) &&
                Objects.equals(slovo, that.slovo) &&
                Objects.equals(tag, that.tag) &&
                Objects.equals(pocetVyskytu, that.pocetVyskytu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSlovo, slovo, tag, pocetVyskytu);
    }

    @ManyToOne
    @JoinColumn(name = "id_projev", referencedColumnName = "id_projev", nullable = false)
    public ProjevEntity getProjevByIdProjev() {
        return projevByIdProjev;
    }

    public void setProjevByIdProjev(ProjevEntity projevByIdProjev) {
        this.projevByIdProjev = projevByIdProjev;
    }

    @Override
    public Integer takeID() {
        return getIdSlovo();
    }

    @Override
    public void pushID(Integer id) {
        setIdSlovo(id);
    }
}
