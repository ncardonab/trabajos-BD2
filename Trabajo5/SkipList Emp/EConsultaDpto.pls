set SERVEROUTPUT on

CREATE OR REPLACE PROCEDURE ConsultaDpto(depE NUMBER) IS

sgtNodo VARCHAR2(5);
nodoAnt NUMBER;
nodoAux NUMBER := 0;
BEGIN

dbms_output.put_line('Exploración de nodos');
dbms_output.put_line('Numnodo  codigoE');

FOR nodo IN (SELECT t.numnodo nnodo, t.codigoD depto, t.nombreD nombre, t.direccionD direccion, COUNT(t2.numnodo) cantnodos 
            FROM indexempskip t, TABLE(t.grupoDePunteros) t2 
            GROUP BY  (t.numnodo, t.codigoD, t.nombreD, t.direccionD) ORDER BY cantnodos DESC, t.numnodo) LOOP

    IF sgtNodo = 'Mayor' AND nodo.nnodo < nodoAnt THEN
        CONTINUE;
    ELSIF sgtNodo = 'Menor' AND nodo.nnodo > nodoAnt THEN
        CONTINUE;
    END IF;

    IF nodo.depto = depE THEN
        dbms_output.put_line('Se encontró del depE en el nodo: ' || nodo.nnodo);
        dbms_output.put_line('Nombre: ' || nodo.nombre);
        dbms_output.put_line('Dirección: ' || nodo.direccion);
        dbms_output.put_line('Empleados: ');
        -- Imprimir empleados
        FOR emp IN (SELECT t2.codigoE cod, t2.nombreE nom FROM indexempskip t, TABLE(t.empleados) t2 WHERE t.numnodo = nodo.nnodo) LOOP
            dbms_output.put_line(emp.cod || ', ' || emp.nom);
        END LOOP;
        nodoAux := 1;
        EXIT;
    ELSIF depE > nodo.depto THEN
        sgtNodo := 'Mayor';
        dbms_output.put_line(nodo.nnodo || '         ' || nodo.depto);
    ELSIF depE < nodo.depto THEN
        sgtNodo := 'Menor';
        dbms_output.put_line(nodo.nnodo || '         ' || nodo.depto);
    END IF;
    nodoAnt := nodo.nnodo;
END LOOP;
IF nodoAux = 0 THEN
    dbms_output.put_line('Dpto. no existe');
END IF;
END;
/

BEGIN
ConsultaDpto(22);
END;
/