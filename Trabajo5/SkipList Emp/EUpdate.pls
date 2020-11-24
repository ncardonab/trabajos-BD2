set SERVEROUTPUT on

DROP TRIGGER UpdateEmp;  
CREATE OR REPLACE TRIGGER UpdateEmp
AFTER UPDATE ON empleado
FOR EACH ROW
DECLARE
    nodo indexempskip%ROWTYPE;
    firstDep NUMBER;
    lastDep NUMBER;
    auxNodoB NUMBER;
    nodoB indexempskip%ROWTYPE;
    nodoAux indexempskip%ROWTYPE;
    c NUMBER;
    i NUMBER := 1;
BEGIN
    SELECT indexempskip_type(numnodo, codigoD, nombreD, direccionD, grupoDePunteros, empleados, ptrback) INTO nodo FROM indexempskip WHERE codigoD = :OLD.depE;
     -- c: Cantidad de nodos
    SELECT COUNT(*) INTO c FROM indexempskip;
    -- Penúltimo nodo
    c := c - 1;
    dbms_output.put_line('IF GRANDE');
    IF :OLD.depE <> :NEW.depE THEN
        --Primer codigoD
        SELECT numnodo INTO firstDep FROM indexempskip WHERE numnodo = 2;
        SELECT numnodo INTO lastDep FROM indexempskip WHERE numnodo = c;
        IF :NEW.codigoD < firstDep THEN
            SELECT indexempskip_type(numnodo, codigoD, nombreD, direccionD, grupoDePunteros, empleados, ptrback) INTO nodoB FROM indexempskip WHERE numnodo = 2;
        ELSIF :NEW.codigoD < :OLD.codigoD THEN
            SELECT numnodo INTO auxNodoB FROM(SELECT numnodo FROM indexempskip WHERE codigoD > :NEW.depE ORDER BY numnodo) WHERE ROWNUM = 1;
            SELECT indexempskip_type(numnodo, codigoD, nombreD, direccionD, grupoDePunteros, empleados, ptrback) INTO nodoB FROM indexempskip WHERE numnodo = auxNodoB;
        ELSE
            SELECT numnodo INTO auxNodoB FROM(SELECT numnodo FROM indexempskip WHERE codigoD < :NEW.depE ORDER BY numnodo DESC) WHERE ROWNUM = 1;
            SELECT indexempskip_type(numnodo, codigoD, nombreD, direccionD, grupoDePunteros, empleados, ptrback) INTO nodoB FROM indexempskip WHERE numnodo = auxNodoB;
        END IF;

        IF nodoB.numnodo > nodo.numnodo THEN
            dbms_output.put_line('Nodo a cambiar: ' || nodoB.numnodo);
            FOR nodoAct IN (SELECT * FROM indexempskip WHERE numnodo >= nodo.numnodo AND numnodo < nodoB.numnodo ORDER BY numnodo) LOOP
                i := nodoAct.numnodo + 1;
                SELECT indexempskip_type(numnodo, codigoD, nombreD, direccionD, grupoDePunteros, empleados, ptrback) INTO nodoAux FROM indexempskip WHERE numnodo = i;
                
                UPDATE indexempskip
                SET codigoD = nodoAux.codigoD, nombreD = nodoAux.nombreD, direccionD = nodoAux.direccionD
                WHERE numnodo = nodoAct.numnodo;

                dbms_output.put_line('UP nodo: ' || nodoAct.numnodo || ' codigo: '|| nodoAux.codigoD);
            END LOOP;
        ELSE
            dbms_output.put_line('ENTRO AL CASO');
            FOR nodoAct IN (SELECT * FROM indexempskip WHERE numnodo <= nodo.numnodo AND numnodo > nodoB.numnodo ORDER BY numnodo DESC) LOOP
                i := nodoAct.numnodo - 1;
                SELECT indexempskip_type(numnodo, codigoD, nombreD, direccionD, grupoDePunteros, empleados, ptrback) INTO nodoAux FROM indexempskip WHERE numnodo = i;
                
                UPDATE indexempskip
                SET codigoD = nodoAux.codigoD, nombreD = nodoAux.nombreD, direccionD = nodoAux.direccionD
                WHERE numnodo = nodoAct.numnodo;

                dbms_output.put_line('UP nodo: ' || nodoAct.numnodo || ' codigo: '|| nodoAux.codigoD);
            END LOOP;
            dbms_output.put_line('SALIO DEL LOOP');
        END IF;
        -- Actualizar nodoB
        UPDATE indexempskip
        SET codigoD = :NEW.depE, nombreE = :NEW.nombreE, codigoE = :NEW.codigoE
        WHERE numnodo = nodoB.numnodo;
    ELSE
        UPDATE TABLE(SELECT empleados
                    FROM indexempskip
                    WHERE codigoD = :NEW.depE)
        SET codigoE = :NEW.codigoE, nombreE = :NEW.nombreE
        WHERE numnodo = nodo.numnodo;        
    END IF;
END;
/