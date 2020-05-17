package web.info;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import static web.Helper.getValueWithColorfulLabelComponent;

public class InfoView extends VerticalLayout {


    public InfoView(Runnable runner) {
        add(new ThemeChangerComponent(runner),
                getPoslanec(), getOsoba(), getStrana(),
                getSrovnaniOsob(), getSrovnaniStranAObdobi());
    }

    private VerticalLayout getPoslanec()
    {
        return getValueWithColorfulLabelComponent("Karta Poslanec",
                "<p> Tento pohled se soustředí na jednu entitu a tou je Poslanec. První je potřeba vybrat určitého poslance. K tomu jsou určeny 3 rozevírací seznamy. První obsahuje výběr volebního období, druhý zobrazuje seznam stran, za které byli poslanci zvoleni a třetí umožňuje vybrat konkrétního poslance. Poslance popisuje profil, kde jsou zobrazeny údaje jako jméno, kandidátka, na které byl zvolen, email,... Druhou částí pohledu na poslance jsou statistiky, konkrétně: nejpoužívanější slova, zmínky, měsíční grafy a projevy. </p>" +
                        "\n" +
                        "<p> Na zmínky poslanců mezi sebou pohlíží program třemi pohledy. V prvním se zaměřuje na to, které poslance vybraný poslance zmiňoval nejčastěji. Tento pohled je prezentován tabulkou se jmény a počty zmínek daného poslance. Druhý pohled je inverzí k prvnímu. Jedná se o podobnou tabulku, ale nyní zobrazuje, jací poslanci a kolikrát zmiňovali vybraného poslance. Třetí pohled ukazuje, jak vybraný poslanec zmiňoval celé strany. Tato data se sbírají znovu přes zmínky poslanců, ale program provede zobecnění na strany. Tento pohled reprezentují dva grafy. První z grafů ukazuje ve sloupcích počty zmínek za jednotlivé strany. Druhý graf ukazuje, kolikrát vybraný poslanec průměrně zmínil každého poslance z dané strany. Simuluje tedy částečně situaci, kdy by všechny strany měly stejný počet poslanců. Obecně totiž bývá typické, že poslanec častěji zmiňuje poslance té strany, která má ve Sněmovně více poslanců. Této simulace program docílí tím, že dělí absolutní počty zmínek počtem poslanců dané strany. </p>" +
                        "\n" +
                        "<p> K poslanci se vážou dva grafy s údaji po jednotlivých měsících. Jeden se zaměřuje na slova a druhý na sentiment. Zdrojem dat k oběma grafům jsou projevy vybraného poslance. Projevy jsou rozděleny podle měsíců, v kterých byly projednávány body, ke kterým projevy přísluší. Počtem slov za měsíc je poté součtem počtů slov všech projevů daného měsíce. Sentiment za měsíc je vypočítaný ze sentimentu všech slov všech projevů daného měsíce. </p>" +
                        "\n" +
                        "<p> Program zobrazuje i všechny projevy, které poslanec pronesl. Projevy jsou strukturovány po jednotlivých poslaneckých schůzích v rozbalovacích seznamech. U celých schůzí jsou zobrazeny následující informace -- číselné označení, počet slov a sentiment všech projevů vybraného poslance v rámci konkrétní schůze. Každý projev má u sebe informace -- název jednacího bodu, ke kterému se projev váže, počet slov a sentiment celého projevu. </p>"
        );
    }

    private VerticalLayout getOsoba() {
        return getValueWithColorfulLabelComponent("Karta Osoba",
                "<p> Na této kartě si uživatel vybere osobu ze seznamu všech osob, které kdy měly poslanecký mandát. Po výběru ze seznamu se uživateli zobrazí profil osoby, který je stejný jako v případě poslance, přesněji se použijí informace z nejnovějšího poslaneckého mandátu vybrané osoby.</p>" +
                        "\n" +
                        "<p>Další část tvoří dva grafy. Oba jsou prosté sloupcové a u obou představuje jeden sloupec jedno období. První graf vyjadřuje sentiment poslance za období a druhý počet slov za období. U osob s více poslaneckými mandáty lze tedy vidět vývoj v delším časovém intervalu.</p>" +
                        "\n" +
                        "<p>Poslední část je rolovací seznam, v kterém lze vybrat jedno z období, v kterém vybrané osoba byla poslancem. Po výběru se pod seznamem objeví statistiky poslance jako jsou na kartě Poslanec -- nejpoužívanější slova, zmínky, měsíční grafy a projevy. Původní myšlenkou bylo načítat všechny poslanecké mandáty vybrané osoby rovnou. Existují ale dva hlavní důvody, proč se poslanecký mandát u konkrétního člověka vybírá jednotlivě. Za prvé při načtení statistik všech poslaneckých mandátů pod sebe docházelo k dlouhému načítání, což by kazilo uživatelský zážitek. Druhým důvodem je to, že u některých osob s více poslaneckými mandáty by stránka byla moc dlouhá a v tom by se uživatel složitě orientoval.</p>"
        );
    }

    private VerticalLayout getStrana() {
        return getValueWithColorfulLabelComponent("Karta Strana",
                "<p>Tato karta popisuje poslance zvolené za jednu stranu v jednom období. Na začátku uživatel vybere pomocí dvou rozevíracích seznamů volební období a stranu. Po vybrání strany se zobrazí její statistiky. Nahoře je krátký profil -- jméno a zkratka. Pod profilem jsou souhrnné statistiky pro celou stranu -- průměry a mediány počtu slov a sentimentu. </p>" +
                        "\n" +
                        "<p>Následuje tabulka, kde jeden řádek představuje jednoho poslance. Tabulka má dále čtyři sloupce se statistikami -- sentiment, počet slov, počet negativních a počet pozitivních slov. Tabulku lze řadit podle kteréhokoliv sloupce a je možno jednoduše zjišťovat například, který poslanec pronesl nejméně slov, nebo kdo má nejvyšší sentiment.</p>" +
                        "\n" +
                        "<p>Pod tabulkou jsou ještě dva grafy. V obou případech jde o prosté sloupcové grafy, kde jeden sloupec představuje jednu stranu. Grafy vyjadřují, jak poslanci vybrané strany zmiňují poslance jiné strany. První zobrazuje absolutní čísla. Druhý hodnotu u každé strany vydělí jejím počtem členů ve sněmovně. Sloupec pak tedy říká informaci, kolikrát průměrně byl zmíněn každý poslanec dané strany.</p>"
                );
    }

    private VerticalLayout getSrovnaniOsob() {
        return getValueWithColorfulLabelComponent("Karta Srovnání osob",
                "<p>Na této kartě lze srovnávat jednotlivé zástupce entity Osoba mezi sebou. Uživatel si na začátku vybere množinu osob, které chce porovnávat. Už po přidání první osoby se na stránce objeví množství grafů a dalších statistik. </p>" +
                        "\n" +
                        "<p>Na začátku stránky popisuje srovnání osob šest grafů. U pěti z nich jde o prosté sloupcové grafy, kde jeden sloupech vždy představuje jedno volební období. Grafy se zaměřují na sentiment, počet slov a také na počty negativních a pozitivních slov. Šestý graf se zaměřuje na počet slov. Jedná se o graf skládaný sloupcový, kde jeden sloupec představuje jednu osobu. Každý sloupec se skládá ze tří částí. Jde o počty pozitivních, negativních a neutrálních slov. Velikost celého složeného sloupce tedy vypovídá o celkovém počtu slov dané osoby. V tomto grafu lze vidět i to, že slova bez sentimentu mají většinové zastoupení v projevech asi všech osob (odpozorováno). To je dáno za prvé tím, že studovaná doména (Poslanecká sněmovna) nabízí projevy s málo výrazným pozitivním nebo negativním sentimentem. Dalším důvodem může být i velikost slovníku s pozitivními a negativními slovy. </p>" +
                        "\n" +
                        "<p>Pod šesticí grafů je ještě oblast, která se zaměřuje na jednotlivá období. Zobrazují se jen ta období, v kterých byla alespoň jedna z vybraných osob poslancem. Pro každé období jsou dostupné dva grafy. Oba jsou sloupcové, kde jeden sloupec odpovídá jednomu měsíci. Jeden graf se zaměřuje na počet slov, druhý na sentiment. V grafech se zobrazí vždy všichni poslanci daného období, kteří mají vazbu na některou z vybraných osob. Pomocí grafů lze tedy osoby porovnávat podrobněji. Toto srovnání využívá stejné grafy, které byly popsány už v popisu karty Poslanec. </p>");
    }

    private VerticalLayout getSrovnaniStranAObdobi() {
        return getValueWithColorfulLabelComponent("Karty Srovnání stran a Srovnání období",
                "<p>Tyto dvě karty mají stejnou strukturu obsahu a tou je šest grafů. U karet se ale liší pohled na doménu. Srovnání stran se zabývá stranami -- je zde tedy potřeba na začátku vybrat období, respektive množinu stran. Srovnání období srovnává všechna volební období Poslanecké sněmovny. Jeden sloupec grafu vždy vyjadřuje období nebo stranu. První trojice grafů se zaměřuje na slova. První graf ukazuje celkové počty slov, druhý průměry počtu slov na jednoho poslance a třetí medián počtu slov na jednoho poslance. Druhá trojice grafů vizualizuje informace o sentimentu. První graf ukazuje celkový sentiment -- sentiment spočítaný ze všech projevů. Druhý graf představuje průměr sentimentu na jednoho poslance a třetí medián sentimentu.</p>");
    }



}
