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

CREATE TABLE ConsultaDep(
    nodo NUMBER PRIMARY KEY,
    cant NUMBER
);

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

-- Ver contenido tabla anidada
SELECT t2.numnodo num
FROM indexdepskip t, TABLE(t.grupoDePunteros) t2
WHERE t.numnodo = 1;

SELECT t.numnodo, COUNT(t2.numnodo) cantnodos
FROM indexdepskip t, TABLE(t.grupoDePunteros) t2
GROUP BY  t.numnodo
ORDER BY cantnodos DESC, t.numnodo;


-- Mostrar nodos con alturas
SELECT nnodo, altura FROM(SELECT t.numnodo nnodo, COUNT(t2.numnodo) altura
FROM indexdepskip t, TABLE(t.grupoDePunteros) t2 
GROUP BY  (t.numnodo) ORDER BY t.numnodo)
WHERE nnodo > 2 AND altura >= 1;

INSERT INTO TABLE(SELECT grupoDePunteros
            FROM indexdepskip t
            WHERE t.numnodo = 7);

DELETE DEPARTAMENTO;
INSERT INTO departamento VALUES(11,'dep1','carrera dep 1');
INSERT INTO departamento VALUES(22,'dep22','calle dep 2');
INSERT INTO departamento VALUES(33,'dep333','carrera dep 3');
INSERT INTO departamento VALUES(44,'dep4','calle dep 4');
INSERT INTO departamento VALUES(55,'dep55','carrera dep 5');
INSERT INTO departamento VALUES(66,'dep666','calle dep 6');

DELETE indexdepskip;
INSERT INTO INDEXDEPSKIP VALUES(1, NULL, NULL, NULL, NEST_PUNTERO(PUNTERO_TIP(2), PUNTERO_TIP(2), PUNTERO_TIP(2)), NULL);
INSERT INTO INDEXDEPSKIP VALUES(2, 11, 'dep1' , 'carrera dep 1', NEST_PUNTERO(PUNTERO_TIP(3), PUNTERO_TIP(4), PUNTERO_TIP(8)),1);
INSERT INTO INDEXDEPSKIP VALUES(3, 22, 'dep22', 'calle dep 2', NEST_PUNTERO(PUNTERO_TIP(4)),2);
INSERT INTO INDEXDEPSKIP VALUES(4, 33, 'dep333', 'calle dep 3', NEST_PUNTERO(PUNTERO_TIP(5), PUNTERO_TIP(5)),3);
INSERT INTO INDEXDEPSKIP VALUES(5, 44, 'dep4', 'calle dep 4', NEST_PUNTERO(PUNTERO_TIP(6), PUNTERO_TIP(7)),4);
INSERT INTO INDEXDEPSKIP VALUES(6, 55, 'dep55', 'calle dep 5', NEST_PUNTERO(PUNTERO_TIP(7)),5);
INSERT INTO INDEXDEPSKIP VALUES(7, 66, 'dep6', 'calle dep 6', NEST_PUNTERO(PUNTERO_TIP(8), PUNTERO_TIP(8)),6);
INSERT INTO INDEXDEPSKIP VALUES(8, NULL, NULL, NULL, NULL,7);

DELETE FROM departamento WHERE codigoD = 44;

SELECT numnodo, codigoD, grupoDePunteros FROM indexdepskip;