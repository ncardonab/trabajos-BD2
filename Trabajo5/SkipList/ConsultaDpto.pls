set SERVEROUTPUT on

CREATE OR REPLACE PROCEDURE ConsultaDpto(codigoD NUMBER) IS

sgtNodo VARCHAR2(5);
nodoAnt NUMBER;
nodoAux NUMBER := 0;
BEGIN

dbms_output.put_line('Exploración de nodos');
dbms_output.put_line('Numnodo  CodigoD');

FOR nodo IN (SELECT t.numnodo nnodo, t.codigoD depto, t.nombreD nombre, t.direccionD direccion, COUNT(t2.numnodo) cantnodos 
            FROM indexdepskip t, TABLE(t.grupoDePunteros) t2 
            GROUP BY  (t.numnodo, t.codigoD, t.nombreD, t.direccionD) ORDER BY cantnodos DESC, t.numnodo) LOOP

    IF sgtNodo = 'Mayor' AND nodo.nnodo < nodoAnt THEN
        CONTINUE;
    ELSIF sgtNodo = 'Menor' AND nodo.nnodo > nodoAnt THEN
        CONTINUE;
    END IF;

    IF nodo.depto = codigoD THEN
        dbms_output.put_line('Se encontró del codigoD en el nodo: ' || nodo.nnodo);
        dbms_output.put_line('Nombre: ' || nodo.nombre);
        dbms_output.put_line('Dirección: ' || nodo.direccion);
        nodoAux := 1;
        EXIT;
    ELSIF codigoD > nodo.depto THEN
        sgtNodo := 'Mayor';
        dbms_output.put_line(nodo.nnodo || '         ' || nodo.depto);
    ELSIF codigoD < nodo.depto THEN
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
ConsultaDpto(55);
END;
/