CREATE TABLE empleado(
    codigoE NUMBER(10) PRIMARY KEY,
    nombreE VARCHAR2(20) NOT NULL,
    depE NUMBER(8) NOT NULL
);



CREATE TABLE departamento(
    codigoD NUMBER(8) PRIMARY KEY,
    nombreD VARCHAR2(20) NOT NULL,
    direccionD VARCHAR2(20) NOT NULL
);

INSERT INTO departamento VALUES(11,'dep1','carrera dep 1');
INSERT INTO departamento VALUES(22,'dep22','calle dep 2');
INSERT INTO departamento VALUES(33,'dep333','carrera dep 3');
INSERT INTO departamento VALUES(44,'dep4','calle dep 4');
INSERT INTO departamento VALUES(55,'dep55','carrera dep 5');
INSERT INTO departamento VALUES(66,'dep666','calle dep 6');


CREATE OR REPLACE TYPE puntero_tip AS OBJECT(
    numnodo NUMBER
);
/

CREATE TABLE puntero OF puntero_tip
(numnodo PRIMARY KEY);

INSERT INTO puntero VALUES(22);
INSERT INTO puntero VALUES(33);

CREATE OR REPLACE TYPE ptrs_varray AS
VARRAY(10) OF REF puntero_tip;
/

DROP TYPE indexdepskip_tip FORCE;
CREATE TYPE indexdepskip_tip AS object(
    numnodo NUMBER,
    codigoD NUMBER(8),
    nombreD VARCHAR2(20),
    direccionD VARCHAR(20),
    grupoDePunteros ptrs_varray,
    ptrback puntero_tip
);
/

DROP TABLE  indexdepskip FORCE;
CREATE TABLE indexdepskip OF indexdepskip_tip(
    numnodo PRIMARY KEY
);