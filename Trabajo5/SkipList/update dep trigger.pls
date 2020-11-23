set SERVEROUTPUT on

DROP TRIGGER DeleteDep;  
CREATE OR REPLACE TRIGGER DeleteDep
AFTER UPDATE ON departamento
FOR EACH ROW
DECLARE
    nodo indexdepskip%ROWTYPE;
    firstDep NUMBER;
    lastDep NUMBER;
    auxNodoB NUMBER;
    nodoB indexdepskip%ROWTYPE;
    c NUMBER;
BEGIN
    SELECT indexdepskip_type(numnodo, codigoD, nombreD, direccionD, grupoDePunteros, ptrback) INTO nodo FROM indexdepskip WHERE codigoD = :OLD.codigoD;
     -- c: Cantidad de nodos
    SELECT COUNT(*) INTO c FROM indexdepskip;
    -- Penúltimo nodo
    c := c - 1;
    IF :OLD.codigoD <> :NEW.codigoD THEN
        --Primer codigoD
        SELECT numnodo INTO firstDep FROM indexdepskip WHERE numnodo = 2;
        SELECT numnodo INTO lastDep FROM indexdepskip WHERE numnodo = c;
        IF :NEW.codigoD < firstDep THEN
            SELECT indexdepskip_type(numnodo, codigoD, nombreD, direccionD, grupoDePunteros, ptrback) INTO nodoB FROM indexdepskip WHERE numnodo = 2;
        ELSE
            SELECT numnodo INTO auxNodoB FROM(SELECT numnodo FROM indexdepskip WHERE codigoD > :NEW.codigoD ORDER BY numnodo) WHERE ROWNUM = 1;
            SELECT indexdepskip_type(numnodo, codigoD, nombreD, direccionD, grupoDePunteros, ptrback) INTO nodoB FROM indexdepskip WHERE numnodo = auxNodoB;
        END IF;

        -- Actualizo nodoA
        UPDATE indexdepskip
        SET codigoD = nodoB.codigoD, direccionD = nodoB.direccionD, nombreD = nodoB.nombreD
        WHERE numnodo = nodo.numnodo;
        -- Actualizar nodoB
        UPDATE indexdepskip
        SET codigoD = :NEW.codigoD, direccionD = :NEW.direccionD, nombreD = :NEW.nombreD
        WHERE numnodo = nodoB.numnodo;
    ELSE
        UPDATE indexdepskip
        SET codigoD = :NEW.codigoD, direccionD = :NEW.direccionD, nombreD = :NEW.nombreD
        WHERE numnodo = nodo.numnodo;        
    END IF;
END;
/