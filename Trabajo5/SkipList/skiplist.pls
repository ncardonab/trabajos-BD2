set SERVEROUTPUT on

CREATE OR REPLACE PROCEDURE CrearSL(maxPtrs NUMBER) IS
    n pls_integer;
    c PLS_INTEGER;
    ran PLS_INTEGER;
    i NUMBER := 2;
    j NUMBER := 1;
    iter NUMBER := 1;

    TYPE t_puntero IS TABLE OF NUMBER(3) INDEX BY BINARY_INTEGER;
    mis_punteros t_puntero;
BEGIN

    SELECT COUNT(*) INTO c FROM departamento;

    DELETE auxdep;
    DELETE indexdepskip;
    mis_punteros(1) := 1;
    INSERT INTO indexdepskip VALUES(
        1, NULL, NULL, NULL, nest_puntero(puntero_tip(2)), NULL
    );
    FOR dep IN (SELECT * FROM departamento ORDER BY codigoD) LOOP
        INSERT INTO indexdepskip VALUES(
            i, dep.codigoD, dep.nombreD, dep.direccionD, nest_puntero(puntero_tip(i+1)), i-1
        );        
        mis_punteros(i) := i;
        i := i + 1;
    END LOOP;
    INSERT INTO indexdepskip VALUES(
        i, NULL, NULL, NULL, NULL, i-1
    );
    
    -- Guardar num nodo final
    n := mis_punteros.COUNT + 1;

    WHILE mis_punteros.COUNT > 2 AND iter <= maxPtrs LOOP
        j := mis_punteros.FIRST;
        WHILE j IS NOT NULL LOOP
            IF j = mis_punteros.LAST THEN
                INSERT INTO TABLE(SELECT grupoDePunteros FROM indexdepskip WHERE numnodo = j)
                VALUES(n);
                EXIT;
            END IF;
            SELECT dbms_random.value(0,1) num into ran FROM dual;
            dbms_output.put_line('J ES: ' || j);
            dbms_output.put_line('r: ' ||ran);
            i := mis_punteros.NEXT(j);
            IF ran = 0 THEN
                IF i IS NOT NULL THEN
                    INSERT INTO TABLE(SELECT grupoDePunteros FROM indexdepskip WHERE numnodo = j)
                    VALUES(mis_punteros(i));
                END IF;
                j := mis_punteros.NEXT(j);
            ELSE
                mis_punteros.DELETE(i);
            END IF;
        END LOOP;
        iter := iter+1;
    END LOOP;
END;
/

BEGIN
CrearSL(8);
END;
/