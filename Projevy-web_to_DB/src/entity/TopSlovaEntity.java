package entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "top_slova", schema = "dbo", catalog = "Poslanci")
public class TopSlovaEntity implements HasID {
    private Integer idTopSlova;
    private String slovo;
    private Integer pocetVyskytu;
    private Integer poradi;
    private StatistikyEntity statistikyByIdPoslanec;

    public TopSlovaEntity() {
    }

    public TopSlovaEntity(String slovo, Integer pocetVyskytu, Integer poradi, StatistikyEntity statistikyEntity) {
        this.idTopSlova = null;
        this.slovo = slovo;
        this.pocetVyskytu = pocetVyskytu;
        this.poradi = poradi;
        this.statistikyByIdPoslanec = statistikyEntity;
    }

    @Id
    @Column(name = "id_top_slova")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Integer getIdTopSlova() {
        return idTopSlova;
    }

    public void setIdTopSlova(Integer idTopSlova) {
        this.idTopSlova = idTopSlova;
    }

    @Basic
    @Column(name = "slovo")
    public String getSlovo() {
        return slovo;
    }

    public void setSlovo(String slovo) {
        this.slovo = slovo;
    }

    @Basic
    @Column(name = "pocet_vyskytu")
    public Integer getPocetVyskytu() {
        return pocetVyskytu;
    }

    public void setPocetVyskytu(Integer pocetVyskytu) {
        this.pocetVyskytu = pocetVyskytu;
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
        TopSlovaEntity that = (TopSlovaEntity) o;
        return Objects.equals(idTopSlova, that.idTopSlova) &&
                Objects.equals(slovo, that.slovo) &&
                Objects.equals(pocetVyskytu, that.pocetVyskytu) &&
                Objects.equals(poradi, that.poradi);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTopSlova, slovo, pocetVyskytu, poradi);
    }

    @Override
    public Integer takeID() {
        return getIdTopSlova();
    }

    @Override
    public void pushID(Integer id) {
        setIdTopSlova(id);
    }

    @ManyToOne
    @JoinColumn(name = "id_poslanec", referencedColumnName = "id_poslanec")
    public StatistikyEntity getStatistikyByIdPoslanec() {
        return statistikyByIdPoslanec;
    }

    public void setStatistikyByIdPoslanec(StatistikyEntity statistikyByIdPoslanec) {
        this.statistikyByIdPoslanec = statistikyByIdPoslanec;
    }
}
