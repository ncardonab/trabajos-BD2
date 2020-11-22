set SERVEROUTPUT on

DROP TRIGGER DeleteDep;  
CREATE OR REPLACE TRIGGER DeleteDep

BEFORE DELETE ON departamento
FOR EACH ROW
DECLARE
    c NUMBER;
    nodo indexdepskip%ROWTYPE;
    alt NUMBER;
    altAct NUMBER;
    nnodo NUMBER;

BEGIN
    -- Nodo a eliminar 
    SELECT indexdepskip_type(numnodo, codigoD, nombreD, direccionD, grupoDePunteros, ptrback) INTO nodo FROM indexdepskip WHERE codigoD = :OLD.codigoD;
    dbms_output.put_line('NODO A ELIMINAR: ' || nodo.numnodo);
    -- Eliminar nodo
    DELETE FROM indexdepskip WHERE codigoD = nodo.codigoD;
    -- Actualizar: Restar uno a los nodos siguientes 
    UPDATE indexdepskip SET numnodo = numnodo - 1 WHERE numnodo > nodo.numnodo;
    -- Restar uno a los grupos de punteros de los nodos siguientes
    dbms_output.put_line('ANTES DEL FOR1');
    SELECT COUNT(*) INTO c FROM indexdepskip;
    FOR nodoAct IN (SELECT * FROM indexdepskip WHERE numnodo >= nodo.numnodo AND numnodo < c ORDER BY numnodo ) LOOP
        dbms_output.put_line('NODO A RESTAR: ' || nodoACT.numnodo);
        UPDATE TABLE(SELECT grupoDePunteros
                FROM indexdepskip t
                WHERE t.numnodo = nodoAct.numnodo)
        SET numnodo = numnodo - 1;
        dbms_output.put_line('NODO RESTADO: ' || nodoACT.numnodo);
    END LOOP;   

    -- SE actualiza el grupo de puntero de cada nodo anterior al nodo eliminado 
    dbms_output.put_line('ANTES DEL FOR2');
    FOR nodoAct IN (SELECT * FROM indexdepskip WHERE numnodo < nodo.numnodo ORDER BY numnodo) LOOP

        dbms_output.put_line('NODO ACTUAL: ' || nodoACT.numnodo);
        -- Altura del nodo actual
        SELECT COUNT(t2.numnodo) altura INTO altAct
        FROM indexdepskip t, TABLE(t.grupoDePunteros) t2 
        WHERE t.numnodo = nodoAct.numnodo
        GROUP BY  (t.numnodo) ORDER BY t.numnodo;
        dbms_output.put_line('ALTURA NODO ACTUAL: ' || altAct);

        DELETE TABLE(SELECT grupoDePunteros
        FROM indexdepskip t
        WHERE t.numnodo = nodoAct.numnodo);

        alt := 1;
        dbms_output.put_line('ANTES DEL WHILE');
        WHILE alt <= altAct LOOP
            -- Altura del siguiente nodo
            BEGIN
                SELECT nnodo INTO nodoNext FROM(SELECT nnodo, altura FROM(SELECT t.numnodo nnodo, COUNT(t2.numnodo) altura
                FROM indexdepskip t, TABLE(t.grupoDePunteros) t2 
                GROUP BY  (t.numnodo) ORDER BY t.numnodo)
                WHERE nnodo > nodoAct.numnodo AND altura >= alt)
                WHERE ROWNUM = 1;

                dbms_output.put_line('Nodo NEXT: ' || nodoNext);
                INSERT INTO TABLE(SELECT grupoDePunteros FROM indexdepskip WHERE numnodo = nodoAct.numnodo)
                VALUES(nodoNext);
            EXCEPTION
                WHEN NO_DATA_FOUND THEN
                    INSERT INTO TABLE(SELECT grupoDePunteros FROM indexdepskip WHERE numnodo = nodoAct.numnodo)
                    VALUES(c);
            END;
            alt := alt + 1;
        END LOOP;
    END LOOP;
END;
/