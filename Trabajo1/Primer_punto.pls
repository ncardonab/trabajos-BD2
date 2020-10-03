-- Es necesario crear las siguientes dos tablas:
DROP TABLE strs;
CREATE TABLE strs(proveedor NUMBER(8), str VARCHAR2(20));

DROP TABLE venta_aux;
CREATE TABLE venta_aux AS (select distinct codpv, codproducto FROM venta);

DECLARE
    TYPE ventas IS TABLE OF venta_aux%ROWTYPE INDEX BY BINARY_INTEGER;
    array_ventas ventas;

    TYPE prods IS TABLE OF strs.str%TYPE INDEX BY PLS_INTEGER;
    string_prods prods;

    n NUMBER(1);
    i NUMBER(8) := 1;
    j NUMBER(8) := 1;
    prov VARCHAR2(10);
    prov2 VARCHAR2(10);
    resultado VARCHAR2(100);

    aux strs.str%TYPE;
    aux2 strs.proveedor%TYPE;

    a EXCEPTION;
PROCEDURE encontrar_grupos(n IN number) IS
BEGIN
    IF n > 6 OR n < 2 THEN
        RAISE a;
    END IF;
    -- Loop donde se mapea la selección de la tabla de venta_aux en el array_ventas
    FOR venta_aux IN (SELECT * FROM venta_aux ORDER BY codpv, codproducto) LOOP
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
    END LOOP;

    FOR venta_aux IN (SELECT * FROM strs) LOOP
        aux := string_prods(venta_aux.proveedor); --string productos del proveedor actual
        aux2 := string_prods.next(venta_aux.proveedor); --indice siguiente proveedor
        i := 1;
        select nompv INTO prov FROM proveedor WHERE codpv = venta_aux.proveedor;
        resultado := ('[' || venta_aux.proveedor || ' (' || prov || '), ');

        WHILE aux2 IS NOT NULL LOOP
            IF aux = string_prods(aux2) THEN
                i := i + 1;
                select nompv INTO prov2 FROM proveedor WHERE codpv = aux2;
                IF i = n THEN
                    resultado := (resultado || aux2  || ' ('  || prov2 || ')] ' || '--> ' || aux);
                    DBMS_OUTPUT.PUT_LINE(resultado);
                    EXIT;
                END IF;
                resultado := (resultado || aux2  || ' ('  || prov2 || '), ');
            END IF;
            aux2 := string_prods.next(aux2);
        END LOOP;
    END LOOP;
EXCEPTION
    WHEN a THEN
        DBMS_OUTPUT.PUT_LINE('n fuera del rango ');
END;
/