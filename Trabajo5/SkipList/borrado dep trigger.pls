set SERVEROUTPUT on

CREATE OR REPLACE TRIGGER DeleteDep

BEFORE DELETE ON departamento
FOR EACH ROW
DECLARE
    i NUMBER;
    nnodo NUMBER;
    nodoback NUMBER;
BEGIN
    dbms_output.put_line(:OLD.codigoD);
    FOR nodo IN (SELECT * FROM indexdepskip WHERE codigoD = :OLD.codigoD) LOOP
        nodo = nodo.numnodo;
        ptrback = nodo.ptrback;
    END LOOP;

    FOR nodo IN (SELECT * FROM indexdepskip WHERE ptrback = ) LOOP
        nodoback = nodo.numnodo;
    END LOOP;

END;
/