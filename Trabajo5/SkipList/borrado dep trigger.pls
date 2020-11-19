set SERVEROUTPUT on

CREATE OR REPLACE TRIGGER DeleteDep

BEFORE DELETE ON departamento
FOR EACH ROW
DECLARE
i NUMBER;
BEGIN
dbms_output.put_line(:OLD.codigoD);
FOR nodo IN (SELECT * FROM departamento) LOOP
    IF nodo.codigoD = :OLD.codigoD THEN
        nnodo = nodo.numnodo
        nodoback = nodo.ptrback
        EXIT;
    END IF;
END LOOP;


END;
/