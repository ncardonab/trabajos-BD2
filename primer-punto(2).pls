/*INSERT INTO proveedor VALUES(10, 'Lisv');
INSERT INTO proveedor VALUES(20, 'Kirsty');
INSERT INTO proveedor VALUES(30, 'Bjorky');
INSERT INTO proveedor VALUES(40, 'Wimpy');
INSERT INTO proveedor VALUES(50, 'Chucky');
INSERT INTO proveedor VALUES(60, 'Chubby');

INSERT INTO venta VALUES(3, 10, 1);
INSERT INTO venta VALUES(5, 10, 2);
INSERT INTO venta VALUES(7, 10, 5);
INSERT INTO venta VALUES(17, 10, 2);
INSERT INTO venta VALUES(8, 20, 2);
INSERT INTO venta VALUES(1, 20, 1);
INSERT INTO venta VALUES(9, 20, 5);
INSERT INTO venta VALUES(31, 20, 1);
INSERT INTO venta VALUES(33, 20, 1);
INSERT INTO venta VALUES(10, 30, 1);
INSERT INTO venta VALUES(11, 30, 2);
INSERT INTO venta VALUES(12, 40, 2);
INSERT INTO venta VALUES(2, 40, 1);
INSERT INTO venta VALUES(22, 40, 1);
INSERT INTO venta VALUES(28, 50, 2);
INSERT INTO venta VALUES(21, 50, 1);
INSERT INTO venta VALUES(29, 50, 5);
INSERT INTO venta VALUES(99, 60, 2);
*/
--EJECUTAR DESDE AQUÍ

DROP TABLE strs;
CREATE TABLE strs(proveedor NUMBER(8), str VARCHAR2(20));

DROP TABLE venta_aux(str VARCHAR2(20));
CREATE TABLE venta_aux AS (select distinct codpv, codproducto FROM venta);

DECLARE
    TYPE ventas IS TABLE OF venta_aux%ROWTYPE INDEX BY BINARY_INTEGER;
    array_ventas ventas;

    TYPE prods IS TABLE OF strs.str%TYPE INDEX BY PLS_INTEGER;
    string_prods prods;

    n NUMBER(1) := 2;
    i NUMBER(8) := 1;
    j NUMBER(8) := 1;
    prov VARCHAR2(10);
    prov2 VARCHAR2(10);

    aux strs.str%TYPE;
    aux2 strs.proveedor%TYPE;
BEGIN
FOR venta_aux IN (SELECT * FROM venta_aux ORDER BY codpv, codproducto) LOOP
    DBMS_OUTPUT.PUT_LINE(venta_aux.codpv || ' '  || venta_aux.codproducto);
    array_ventas(i) := venta_aux;
    i := i+1;
END LOOP;

FOR venta_aux IN (SELECT codpv, COUNT(codproducto) AS cantidad FROM venta_aux GROUP BY codpv ORDER BY codpv) LOOP
    string_prods(venta_aux.codpv) := '{';

    FOR i IN 1.. venta_aux.cantidad LOOP
        string_prods(venta_aux.codpv) := string_prods(venta_aux.codpv) || array_ventas(j).codproducto;
        IF i <> venta_aux.cantidad THEN
            string_prods(venta_aux.codpv) := string_prods(venta_aux.codpv) || ',';
        ELSE
            string_prods(venta_aux.codpv) := string_prods(venta_aux.codpv) || '}';
        END IF;
        j := array_ventas.NEXT(j);
    END LOOP;

    INSERT INTO strs VALUES (venta_aux.codpv, string_prods(venta_aux.codpv));
    DBMS_OUTPUT.PUT_LINE(string_prods(venta_aux.codpv));
END LOOP;

FOR venta_aux IN (SELECT * FROM strs) LOOP
    aux := string_prods(venta_aux.proveedor);
    aux2 := string_prods.next(venta_aux.proveedor);
    WHILE aux2 IS NOT NULL LOOP
        
        IF aux = string_prods(aux2) THEN
            select nompv INTO prov FROM proveedor WHERE codpv = venta_aux.proveedor;
            select nompv INTO prov2 FROM proveedor WHERE codpv = aux2;
            DBMS_OUTPUT.PUT_LINE('[' || venta_aux.proveedor || ' (' || prov  || '), '|| aux2  || ' ('  || prov2 || ')]' || ' --> ' || aux);
        END IF;
        aux2 := string_prods.next(aux2);

    END LOOP;
END LOOP;
END;
/

-- SELECT str, COUNT(proveedor) FROM strs GROUP BY str 