CREATE TABLE empleado(
    codigoE NUMBER(10) PRIMARY KEY,
    nombreE VARCHAR2(20) NOT NULL,
    depE NUMBER(8) NOT NULL
);

--Crear tabla indexempskip
DROP TYPE puntero_tip FORCE;
CREATE OR REPLACE TYPE puntero_tip AS OBJECT(
    numnodo NUMBER
);
/

DROP TYPE nest_puntero FORCE;
CREATE OR REPLACE TYPE nest_puntero AS TABLE OF puntero_tip;
/

DROP TYPE empleados_tip FORCE;
CREATE OR REPLACE TYPE empleados_tip AS OBJECT(
    codigoE NUMBER(10),
    nombreE VARCHAR(20)
);
/

DROP TYPE nest_empleados FORCE;
CREATE OR REPLACE TYPE nest_empleados AS TABLE OF empleados_tip;
/

DROP TYPE indexempskip_type FORCE;
CREATE OR REPLACE TYPE indexempskip_type AS OBJECT(
    numnodo NUMBER,
    codigoD NUMBER(8),
    nombreD VARCHAR2(20),
    direccionD VARCHAR(20),
    grupoDePunteros nest_puntero,
    empleados nest_empleados,
    ptrback NUMBER
);
/

DROP TABLE indexempskip FORCE;
CREATE TABLE indexempskip OF indexempskip_type
(numnodo PRIMARY KEY)
NESTED TABLE empleados STORE AS store_empleados
NESTED TABLE grupoDePunteros STORE AS store_punterosE
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

DELETE empleado;
INSERT INTO empleado VALUES(10,'Kim',11);
INSERT INTO empleado VALUES(20,'Kourtney',22);
INSERT INTO empleado VALUES(30,'Rob',11);
INSERT INTO empleado VALUES(40,'Khloe',22);
INSERT INTO empleado VALUES(50,'Lamar',55);
INSERT INTO empleado VALUES(60,'Jessica',66);

DELETE DEPARTAMENTO;
INSERT INTO departamento VALUES(11,'dep1','carrera dep 1');
INSERT INTO departamento VALUES(22,'dep22','calle dep 2');
INSERT INTO departamento VALUES(33,'dep333','carrera dep 3');
INSERT INTO departamento VALUES(44,'dep4','calle dep 4');
INSERT INTO departamento VALUES(55,'dep55','carrera dep 5');
INSERT INTO departamento VALUES(66,'dep666','calle dep 6');

DELETE indexempskip;
INSERT INTO INDEXEMPSKIP VALUES(1, NULL, NULL, NULL, NEST_PUNTERO(PUNTERO_TIP(2), PUNTERO_TIP(4), PUNTERO_TIP(4), PUNTERO_TIP(4)), NULL, NULL);
INSERT INTO INDEXEMPSKIP VALUES(2, 11, 'dep1' , 'carrera dep 1', NEST_PUNTERO(PUNTERO_TIP(3)), NEST_EMPLEADOS(empleados_TIP(10, 'Kim'),empleados_TIP(30,'Rob')), 1);
INSERT INTO INDEXEMPSKIP VALUES(3, 22, 'dep22', 'calle dep 2', NEST_PUNTERO(PUNTERO_TIP(4)),NEST_EMPLEADOS(empleados_TIP(20,'Kourtney'),empleados_TIP(40,'Khloe')),2);
INSERT INTO INDEXEMPSKIP VALUES(4, 33, 'dep333', 'calle dep 3', NEST_PUNTERO(PUNTERO_TIP(5), PUNTERO_TIP(5)),NEST_EMPLEADOS(),3);
INSERT INTO INDEXEMPSKIP VALUES(5, 44, 'dep4', 'calle dep 4', NEST_PUNTERO(PUNTERO_TIP(6), PUNTERO_TIP(7)),NEST_EMPLEADOS(),4);
INSERT INTO INDEXEMPSKIP VALUES(6, 55, 'dep55', 'calle dep 5', NEST_PUNTERO(PUNTERO_TIP(7)),NEST_EMPLEADOS(empleados_TIP(50,'Lamar')),5);
INSERT INTO INDEXEMPSKIP VALUES(7, 66, 'dep6', 'calle dep 6', NEST_PUNTERO(PUNTERO_TIP(8), PUNTERO_TIP(8)),NEST_EMPLEADOS(empleados_TIP(60,'Jessica')),6);
INSERT INTO INDEXEMPSKIP VALUES(8, NULL, NULL, NULL, NULL,NULL,7);

UPDATE departamento
SET codigoD = 50
WHERE codigoD = 22;

DELETE FROM departamento WHERE codigoD = 44;

SELECT numnodo, codigoD, grupoDePunteros FROM indexdepskip;

SELECT * FROM indexdepskip;

SELECT * FROM departamento where codigoD = 33;


SELECT t2.nombreE, t2.codigoE FROM indexempskip t, TABLE(t.empleados) t2 WHERE t.numnodo = 2;

DELETE FROM TABLE(SELECT empleados
                FROM indexempskip
                WHERE codigoD = 11)
WHERE codigoE = 30;

DELETE FROM empleado WHERE codigoE = 10;


