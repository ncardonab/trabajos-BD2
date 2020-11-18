set SERVEROUTPUT on

CREATE OR REPLACE PROCEDURE CrearSL(maxPtrs NUMBER) IS
    n pls_integer;
    c PLS_INTEGER;
    i NUMBER := 1;
BEGIN

    SELECT dbms_random.value(1,maxPtrs) num
    into n
    FROM dual;

    dbms_output.put_line(n);

    SELECT COUNT(*) INTO c FROM departamento;

    DELETE auxdep;
    DELETE indexdepskip;
    FOR dep IN (SELECT * FROM departamento) LOOP
        IF i <> c THEN
            INSERT INTO indexdepskip VALUES(
                i, dep.codigoD, dep.nombreD, dep.direccionD, nest_puntero(puntero_tip(i)), i
            );
        END IF;
        i := i + 1;
    END LOOP;
    

END;
/

BEGIN
CrearSL(8);
END;
/
