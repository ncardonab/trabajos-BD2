set SERVEROUTPUT on

CREATE OR REPLACE TRIGGER DeleteDep

BEFORE DELETE ON departamento
FOR EACH ROW
DECLARE
    i NUMBER;
    nodo NUMBER;
    ptrback NUMBER;

    nodoBack NUMBER;
    ptrNodoBack NUMBER;

    nodoNext NUMBER;
    ptrNodoNext NUMBER;    
BEGIN
    dbms_output.put_line(:OLD.codigoD);
    -- Nodo a eliminar (actual)
    FOR indexdep IN (SELECT * FROM indexdepskip WHERE codigoD = :OLD.codigoD) LOOP
        nodo := indexdep.numnodo;
        ptrback := indexdep.ptrback;
    END LOOP;

    -- -- Nodo anterior
    -- FOR nodo IN (SELECT * FROM indexdepskip WHERE numnodo = ptrback) LOOP
    --     nodoback = nodo.numnodo;
    -- END LOOP;

    -- -- Nodo siguiente 
    -- FOR nodo IN (SELECT * FROM indexdepskip WHERE ptrback = nodo) LOOP
    --     nodonext = nodo.numnodo;
    -- END LOOP;
END;
/