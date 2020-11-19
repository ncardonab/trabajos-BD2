set SERVEROUTPUT on

CREATE OR REPLACE PROCEDURE CrearSL(maxPtrs NUMBER) IS
    n pls_integer;
    c PLS_INTEGER;
    ran PLS_INTEGER;
    i NUMBER := 1;
    j NUMBER := 1;
    iter NUMBER := 1;

    TYPE t_puntero IS TABLE OF NUMBER(3) INDEX BY BINARY_INTEGER;
    mis_punteros t_puntero;
BEGIN
    SELECT dbms_random.value(1,maxPtrs) num
    into n
    FROM dual;

    dbms_output.put_line(n);

    SELECT COUNT(*) INTO c FROM departamento;

    DELETE auxdep;
    DELETE indexdepskip;
    mis_punteros(0) := 0;
    FOR dep IN (SELECT * FROM departamento) LOOP
        IF i = c THEN
            INSERT INTO indexdepskip VALUES(
                i, dep.codigoD, dep.nombreD, dep.direccionD, NULL, i-1
            );
        ELSE
            INSERT INTO indexdepskip VALUES(
                i, dep.codigoD, dep.nombreD, dep.direccionD, nest_puntero(puntero_tip(i+1)), i-1
            );
        END IF;
        mis_punteros(i) := i;
        i := i + 1;
    END LOOP;
    
    WHILE mis_punteros.COUNT > 1 AND iter <= maxPtrs LOOP
        j := mis_punteros.FIRST;
        WHILE j IS NOT NULL LOOP
            IF j = mis_punteros.LAST THEN
                EXIT;
            END IF;
            SELECT dbms_random.value(0,1) num into ran FROM dual;
            dbms_output.put_line('J ES: ' || j);
            dbms_output.put_line(ran);
            i := mis_punteros.NEXT(j);
            IF j = mis_punteros.LAST+1 THEN
                EXIT;
            END IF;
            IF ran = 0 THEN
                BEGIN
                    IF i IS NOT NULL AND j <> 0 THEN
                        INSERT INTO TABLE(SELECT grupoDePunteros FROM indexdepskip WHERE numnodo = j)
                        VALUES(mis_punteros(i));
                    END IF;
                    j := mis_punteros.NEXT(j);
                    EXCEPTION
                        WHEN DUP_VAL_ON_INDEX THEN
                            dbms_output.put_line('YA EXISTE');
                            j := mis_punteros.NEXT(j);
                END;
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