--
-- File generated with SQLiteStudio v3.2.1 on pá kvì 29 13:55:15 2020
--
-- Text encoding used: System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: bod
DROP TABLE IF EXISTS bod;
CREATE TABLE bod (id_bod INTEGER PRIMARY KEY NOT NULL, text TEXT, cislo_schuze INTEGER, datum DATE, id_organ_obdobi INTEGER REFERENCES organy (id_organ) ON DELETE CASCADE ON UPDATE CASCADE NOT NULL);

-- Table: hibernate_sequence
DROP TABLE IF EXISTS hibernate_sequence;
CREATE TABLE hibernate_sequence (next_val bigint);
INSERT INTO hibernate_sequence (next_val) VALUES (3);

-- Table: organy
DROP TABLE IF EXISTS organy;
CREATE TABLE organy (id_organ INTEGER PRIMARY KEY NOT NULL, organ_id_organ INTEGER, id_typ_organu INTEGER REFERENCES typ_organu (id_typ_org) ON DELETE CASCADE ON UPDATE CASCADE, zkratka VARCHAR (256), nazev_organu_cz TEXT, nazev_organu_en TEXT, od_organ DATE, do_organ DATE, priorita INTEGER, cl_organ_base INTEGER);

-- Table: osoby
DROP TABLE IF EXISTS osoby;
CREATE TABLE osoby (id_osoba INT PRIMARY KEY, pred VARCHAR (128), prijmeni VARCHAR (128), jmeno VARCHAR (128), za VARCHAR (128), narozeni DATE, pohlavi VARCHAR (128), zmena DATE, umrti DATE);

-- Table: poslanec
DROP TABLE IF EXISTS poslanec;
CREATE TABLE poslanec (id_poslanec INTEGER PRIMARY KEY NOT NULL, id_osoba INTEGER REFERENCES osoby (id_osoba) ON DELETE CASCADE ON UPDATE CASCADE, id_kraj INTEGER REFERENCES organy (id_organ), id_kandidatka INTEGER REFERENCES organy (id_organ), id_obdobi INTEGER REFERENCES organy (id_organ) ON DELETE CASCADE ON UPDATE CASCADE, web VARCHAR (256), ulice VARCHAR (128), obec VARCHAR (256), psc VARCHAR (16), email VARCHAR (128), telefon VARCHAR (32), fax VARCHAR (32), psp_telefon VARCHAR (32), facebook VARCHAR (256), foto INTEGER);

-- Table: poslanec_statistiky
DROP TABLE IF EXISTS poslanec_statistiky;
CREATE TABLE poslanec_statistiky (id_poslanec INTEGER PRIMARY KEY REFERENCES poslanec (id_poslanec) ON DELETE CASCADE ON UPDATE CASCADE NOT NULL, pocet_slov INTEGER, sentiment DOUBLE);

-- Table: poslanec_statistiky_mesic
DROP TABLE IF EXISTS poslanec_statistiky_mesic;
CREATE TABLE poslanec_statistiky_mesic (id_mesic INTEGER PRIMARY KEY NOT NULL, id_poslanec INTEGER REFERENCES poslanec_statistiky (id_poslanec) ON DELETE CASCADE ON UPDATE CASCADE NOT NULL, mesic DATE, pocet_slov INTEGER, pocet_pos_slov INTEGER, pocet_neg_slov INTEGER, sentiment DOUBLE);

-- Table: poslanec_statistiky_zminky
DROP TABLE IF EXISTS poslanec_statistiky_zminky;
CREATE TABLE poslanec_statistiky_zminky (id_zminky INTEGER PRIMARY KEY NOT NULL, id_poslanec_recnik INTEGER REFERENCES poslanec_statistiky (id_poslanec) ON DELETE CASCADE ON UPDATE CASCADE NOT NULL, id_poslanec_zmineny INTEGER REFERENCES poslanec (id_poslanec) NOT NULL, pocet_vyskytu INTEGER);

-- Table: projev
DROP TABLE IF EXISTS projev;
CREATE TABLE projev (id_projev INTEGER PRIMARY KEY NOT NULL, id_poslanec INTEGER REFERENCES poslanec (id_poslanec) ON DELETE CASCADE ON UPDATE CASCADE, id_bod INTEGER REFERENCES bod (id_bod) ON DELETE CASCADE ON UPDATE CASCADE, text TEXT, pocet_slov INTEGER, poradi INTEGER, pocet_pos_slov INTEGER, pocet_neg_slov INTEGER, sentiment DOUBLE);

-- Table: projev_seq
DROP TABLE IF EXISTS projev_seq;
CREATE TABLE projev_seq (next_val bigint);
INSERT INTO projev_seq (next_val) VALUES (41001);

-- Table: slovo
DROP TABLE IF EXISTS slovo;
CREATE TABLE slovo (id_slovo INTEGER PRIMARY KEY NOT NULL, id_projev INTEGER REFERENCES projev (id_projev) ON DELETE CASCADE ON UPDATE CASCADE, slovo VARCHAR (256), tag VARCHAR (32), pocet_vyskytu INTEGER, sentiment INTEGER);

-- Table: slovo_seq
DROP TABLE IF EXISTS slovo_seq;
CREATE TABLE slovo_seq (next_val bigint);
INSERT INTO slovo_seq (next_val) VALUES (1);

-- Table: top_slova
DROP TABLE IF EXISTS top_slova;
CREATE TABLE top_slova (id_top_slova INTEGER PRIMARY KEY NOT NULL, id_poslanec INTEGER REFERENCES poslanec_statistiky (id_poslanec) ON DELETE CASCADE ON UPDATE CASCADE, slovo VARCHAR (256), pocet_vyskytu INTEGER, poradi INTEGER);

-- Table: typ_organu
DROP TABLE IF EXISTS typ_organu;
CREATE TABLE typ_organu (id_typ_org INTEGER PRIMARY KEY NOT NULL, typ_id_typ_org INTEGER, nazev_typ_org_cz TEXT, nazev_typ_org_en TEXT, typ_org_obecny INTEGER, priorita INTEGER);

-- Table: zminka
DROP TABLE IF EXISTS zminka;
CREATE TABLE zminka (id_zminka INTEGER PRIMARY KEY NOT NULL, id_projev INTEGER REFERENCES projev (id_projev) ON DELETE CASCADE ON UPDATE CASCADE NOT NULL, id_poslanec INTEGER REFERENCES poslanec (id_poslanec) ON DELETE CASCADE ON UPDATE CASCADE NOT NULL, pocet INTEGER);

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
