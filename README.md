# Nástroj pro analýzu poslaneckých projevů

Tento repozitář obsahuje zdrojové kódy k projektu bakalářské práce "Nástroj pro analýzu poslaneckých projevů".

Autorem bakalářské práce je Jan Horyna - horynja2@fit.cvut.cz

# Obsah

## Databases
Tento adresář obsahuje databáze, které se používají v později uvedených programech.
Kvůli velikosti celých databází zde nejsou celé databáze, ale jen SQL scripty.
SQL script lze převést na SQLite databázi, která se v tomto projektu používá, pomocí následující utility:

https://sqlite.org/download.html

1. Po otevření utility se nová databáze vytvoří příkazem ".open" -- příklad: ".open Database.db"
2. SQl script se na databázi aplikuje příkazem ".read" -- příklad: ".read SQLScript.sql"

Lepší popis a případné problémy jsou řešené v tomto vláknu -- https://stackoverflow.com/a/2049137/13325565

Po těchto dvou příkazech je v "Database.db" databáze popsaná v SQL scriptu a lze tuto databázi používat jako argument v následujících programech.


## Poslanci-Downloader
Tento projekt stahuje soubory potřebné pro další projekty. 

Program je spustitelný přes soubor Poslanci-Downloader.jar. Tento soubor je artifact v terminologii IntelliJ IDEA (v kterém probíhal vývoj) a je dostupný v out\artifacts\Poslanci_Downloader_jar.
V popsaném umístení je zkompilována aktuální verze.

V případě vlastní kompilace je důležité uvádět jako hlavní třídu: main.App

Program se spouští s minimálně dvěma parametry
- první označuje cestu, kam se mají data ukládat
- druhým parametrem je seznam parametrů, který může být libovolně dlouhý a označuje volební období, která program stáhne, příklad formátu období: 1993ps 2002ps 2017ps

Výstupem programu je jsou soubory uložené v cestě, kterou uživatel zadal jako první argument, složka s těmito soubory je využita v dalším programu.

## Poslanci-Backend2
Tento projekt analyzuje data z předchozího projektu a ukládá je do SQLite databáze.

Program je spustitelný přes soubor Poslanci-Backend2-1.0-SNAPSHOT-jar-with-dependencies.jar, tento soubor je ve složce target a je vygenerován přes Maven příkazy compile a package.
Tento .jar soubor musí mít ve stejném umístění ještě morphodita_java.dll a složku resources se soubory czech-morfflex-pdt-161115.tagger, stop_slova.unl a sublex_1_0.csv.
Předchozí soubor a složka jsou přímo ve složce target nebo je lze najít ve složce Resources, která je nulté úrovni celého repozitáře.

Program se spouští se čtyřmi parametry
- první označuje cestu k databázi
- druhý označuje cestu k souborům (soubory z Poslanci-Downloader)
- třetí oznčuje období ve formátu - 1993ps 2002ps 2013ps
- čtvrtý označuje období ve formátu - PSP1, PSP4, PSP7

Pomocné soubory pro běh programu jsou napevno ve složce resources. Tuto cestu nelze měnit a program bez nich nefunguje. 
Potřebná pro běh programu je i knihovna morphodita_java.dll.

Výstupem programu je naplněná databáze.

## Poslanci-Frontend
Tento projekt vytváří webovou aplikaci, která vizualizuje data z databáze.

Program je spustitelný přes soubor Poslanci-Frontend-1.0.war, tento soubor je ve složce target a je vygenerován přes Maven příkazy compile a package.
Potřebné soubory pro běh programu jsou napevno ve složce Poslanci-Frontend-1.0, tato složka se vygeneruje spolu s .war souborem.

Program se spouští s jedním parametrem
- první parametr označuje cestu k SQLite databázi

Jedná se o databázi PoslanciSQLite12345678.db, která obsahuje období PSP1-8, kde PSP8 není úplné, protože v době vyhotovení této práce ještě nebylo ukončeno.

Webová aplikace je spuštění dostupná na url http://localhost:8080/

## Resources
Poskytuje zálohu případných souborů potřebných pro spuštění některého z projektů.







