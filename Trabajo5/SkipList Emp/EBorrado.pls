set SERVEROUTPUT on

DROP TRIGGER DeleteEmp;  
CREATE OR REPLACE TRIGGER DeleteEmp

BEFORE DELETE ON empleado
FOR EACH ROW
DECLARE
    c NUMBER;
    nodo indexempskip%ROWTYPE;
    nodoNext NUMBER;
    alt NUMBER;
    altAct NUMBER;
    nnodo NUMBER;
    cantEmp NUMBER;

BEGIN
    -- Nodo a eliminar 
    SELECT indexempskip_type(numnodo, codigoD, nombreD, direccionD, grupoDePunteros, empleados, ptrback) INTO nodo FROM indexempskip WHERE codigoD = :OLD.depE;
    -- empleado a eliminar
    DELETE FROM TABLE(SELECT empleados
                FROM indexempskip
                WHERE codigoD = :OLD.depE)
    WHERE codigoE = :OLD.codigoE;
    
    -- Verificar cantidad de empleados en el depto
    SELECT COUNT(t2.codigoE) cantEmp INTO cantEmp
    FROM indexempskip t, TABLE(t.empleados) t2
    WHERE t.numnodo = nodo.numnodo;

    IF cantEmp = 0 THEN

        -- Eliminar nodo
        DELETE FROM indexempskip WHERE codigoD = nodo.codigoD;
        -- Actualizar: Restar uno a los nodos siguientes 
        UPDATE indexempskip SET numnodo = numnodo - 1 WHERE numnodo > nodo.numnodo;
        -- Restar uno a los grupos de punteros de los nodos siguientes
        SELECT COUNT(*) INTO c FROM indexempskip;
        FOR nodoAct IN (SELECT * FROM indexempskip WHERE numnodo >= nodo.numnodo AND numnodo < c ORDER BY numnodo ) LOOP
            dbms_output.put_line('NODO A RESTAR: ' || nodoACT.numnodo);
            UPDATE indexempskip
            SET ptrback = ptrback - 1
            WHERE numnodo = nodoAct.numnodo;
            UPDATE TABLE(SELECT grupoDePunteros
                    FROM indexempskip t
                    WHERE t.numnodo = nodoAct.numnodo)
            SET numnodo = numnodo - 1;
            
            dbms_output.put_line('NODO RESTADO: ' || nodoACT.numnodo);
        END LOOP;   

        UPDATE indexempskip
        SET ptrback = ptrback - 1
        WHERE numnodo = c;

        -- SE actualiza el grupo de puntero de cada nodo anterior al nodo eliminado
        FOR nodoAct IN (SELECT * FROM indexempskip WHERE numnodo < nodo.numnodo ORDER BY numnodo) LOOP

            dbms_output.put_line('NODO ACTUAL: ' || nodoACT.numnodo);
            -- Altura del nodo actual
            SELECT COUNT(t2.numnodo) altura INTO altAct
            FROM indexempskip t, TABLE(t.grupoDePunteros) t2 
            WHERE t.numnodo = nodoAct.numnodo
            GROUP BY  (t.numnodo) ORDER BY t.numnodo;
            dbms_output.put_line('ALTURA NODO ACTUAL: ' || altAct);

            DELETE TABLE(SELECT grupoDePunteros
            FROM indexempskip t
            WHERE t.numnodo = nodoAct.numnodo);

            alt := 1;
            dbms_output.put_line('ANTES DEL WHILE');
            WHILE alt <= altAct LOOP
                -- Altura del siguiente nodo
                BEGIN
                    SELECT nnodo INTO nodoNext FROM(SELECT nnodo, altura FROM(SELECT t.numnodo nnodo, COUNT(t2.numnodo) altura
                    FROM indexempskip t, TABLE(t.grupoDePunteros) t2 
                    GROUP BY  (t.numnodo) ORDER BY t.numnodo)
                    WHERE nnodo > nodoAct.numnodo AND altura >= alt)
                    WHERE ROWNUM = 1;

                    dbms_output.put_line('Nodo NEXT: ' || nodoNext);
                    INSERT INTO TABLE(SELECT grupoDePunteros FROM indexempskip WHERE numnodo = nodoAct.numnodo)
                    VALUES(nodoNext);
                EXCEPTION
                    WHEN NO_DATA_FOUND THEN
                        INSERT INTO TABLE(SELECT grupoDePunteros FROM indexempskip WHERE numnodo = nodoAct.numnodo)
                        VALUES(c);
                END;
                alt := alt + 1;
            END LOOP;
        END LOOP;
    END IF;
END;
/