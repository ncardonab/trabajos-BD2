set SERVEROUTPUT on

DROP TRIGGER UpdateDep;  
CREATE OR REPLACE TRIGGER UpdateDep
AFTER UPDATE ON departamento
FOR EACH ROW
DECLARE
    nodo indexdepskip%ROWTYPE;
    firstDep NUMBER;
    lastDep NUMBER;
    auxNodoB NUMBER;
    nodoB indexdepskip%ROWTYPE;
    nodoAux indexdepskip%ROWTYPE;
    c NUMBER;
    i NUMBER := 1;
BEGIN
    SELECT indexdepskip_type(numnodo, codigoD, nombreD, direccionD, grupoDePunteros, ptrback) INTO nodo FROM indexdepskip WHERE codigoD = :OLD.codigoD;
     -- c: Cantidad de nodos
    SELECT COUNT(*) INTO c FROM indexdepskip;
    -- Penúltimo nodo
    c := c - 1;
    dbms_output.put_line('IF GRANDE');
    IF :OLD.codigoD <> :NEW.codigoD THEN
        --Primer codigoD
        SELECT numnodo INTO firstDep FROM indexdepskip WHERE numnodo = 2;
        SELECT numnodo INTO lastDep FROM indexdepskip WHERE numnodo = c;
        IF :NEW.codigoD < firstDep THEN
            SELECT indexdepskip_type(numnodo, codigoD, nombreD, direccionD, grupoDePunteros, ptrback) INTO nodoB FROM indexdepskip WHERE numnodo = 2;
        ELSIF :NEW.codigoD < :OLD.codigoD THEN
            SELECT numnodo INTO auxNodoB FROM(SELECT numnodo FROM indexdepskip WHERE codigoD > :NEW.codigoD ORDER BY numnodo) WHERE ROWNUM = 1;
            SELECT indexdepskip_type(numnodo, codigoD, nombreD, direccionD, grupoDePunteros, ptrback) INTO nodoB FROM indexdepskip WHERE numnodo = auxNodoB;
        ELSE
            SELECT numnodo INTO auxNodoB FROM(SELECT numnodo FROM indexdepskip WHERE codigoD < :NEW.codigoD ORDER BY numnodo DESC) WHERE ROWNUM = 1;
            SELECT indexdepskip_type(numnodo, codigoD, nombreD, direccionD, grupoDePunteros, ptrback) INTO nodoB FROM indexdepskip WHERE numnodo = auxNodoB;
        END IF;

        IF nodoB.numnodo > nodo.numnodo THEN
            dbms_output.put_line('Nodo a cambiar: ' || nodoB.numnodo);
            FOR nodoAct IN (SELECT * FROM indexdepskip WHERE numnodo >= nodo.numnodo AND numnodo < nodoB.numnodo ORDER BY numnodo) LOOP
                i := nodoAct.numnodo + 1;
                SELECT indexdepskip_type(numnodo, codigoD, nombreD, direccionD, grupoDePunteros, ptrback) INTO nodoAux FROM indexdepskip WHERE numnodo = i;
                
                UPDATE indexdepskip
                SET codigoD = nodoAux.codigoD, nombreD = nodoAux.nombreD, direccionD = nodoAux.direccionD
                WHERE numnodo = nodoAct.numnodo;

                dbms_output.put_line('UP nodo: ' || nodoAct.numnodo || ' codigo: '|| nodoAux.codigoD);
            END LOOP;
        ELSE
            dbms_output.put_line('ENTRO AL CASO');
            FOR nodoAct IN (SELECT * FROM indexdepskip WHERE numnodo <= nodo.numnodo AND numnodo > nodoB.numnodo ORDER BY numnodo DESC) LOOP
                i := nodoAct.numnodo - 1;
                SELECT indexdepskip_type(numnodo, codigoD, nombreD, direccionD, grupoDePunteros, ptrback) INTO nodoAux FROM indexdepskip WHERE numnodo = i;
                
                UPDATE indexdepskip
                SET codigoD = nodoAux.codigoD, nombreD = nodoAux.nombreD, direccionD = nodoAux.direccionD
                WHERE numnodo = nodoAct.numnodo;

                dbms_output.put_line('UP nodo: ' || nodoAct.numnodo || ' codigo: '|| nodoAux.codigoD);
            END LOOP;
            dbms_output.put_line('SALIO DEL LOOP');
        END IF;
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