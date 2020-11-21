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


--Crear tabla indexdepskip
DROP TYPE puntero_tip FORCE;
CREATE OR REPLACE TYPE puntero_tip AS OBJECT(
    numnodo NUMBER
);
/

DROP TYPE nest_puntero FORCE;
CREATE OR REPLACE TYPE nest_puntero AS TABLE OF puntero_tip;
/

DROP TYPE indexdepskip_type FORCE;
CREATE OR REPLACE TYPE indexdepskip_type AS OBJECT(
    numnodo NUMBER,
    codigoD NUMBER(8),
    nombreD VARCHAR2(20),
    direccionD VARCHAR(20),
    grupoDePunteros nest_puntero,
    ptrback NUMBER
);
/

DROP TABLE indexdepskip FORCE;
CREATE TABLE indexdepskip OF indexdepskip_type
(numnodo PRIMARY KEY)
NESTED TABLE grupoDePunteros STORE AS store_punteros
;
-----------------------------------

INSERT INTO indexdepskip VALUES(
    1, 11, 'Antioquia', 'calle 2', nest_puntero(puntero_tip(2)), 0);

SELECT numnodo, codigoD, grupoDePunteros FROM indexdepskip;

INSERT INTO indexdepskip VALUES(
    6, 50, 'hola', 'carrera', NULL, 5
);

DELETE FROM departamento WHERE codigoD=66;

-- Ver contenido tabla anidada
SELECT t2.numnodo num
FROM indexdepskip t, TABLE(t.grupoDePunteros) t2
WHERE t.numnodo = 1;

SELECT t.numnodo, COUNT(t2.numnodo) cantnodos
FROM indexdepskip t, TABLE(t.grupoDePunteros) t2
GROUP BY  t.numnodo
ORDER BY cantnodos DESC, t.numnodo;