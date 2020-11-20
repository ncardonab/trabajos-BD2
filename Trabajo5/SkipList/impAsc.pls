set SERVEROUTPUT on

CREATE OR REPLACE PROCEDURE ImprimirAscSL IS
c NUMBER;
aux NUMBER;
ptrsnodos VARCHAR2(1000);

TYPE t_array IS TABLE OF NUMBER(3) INDEX BY BINARY_INTEGER;
mi_array t_array;

i NUMBER := 1;
j NUMBER := 1;
BEGIN
    SELECT COUNT(*) INTO c FROM indexdepskip;

    WHILE i < c LOOP
        FOR nodo IN (SELECT * FROM indexdepskip WHERE numnodo = i) LOOP
            dbms_output.put_line('Nodo ' || i);
            
            FOR dep IN (SELECT * FROM departamento WHERE codigoD = nodo.codigoD) LOOP
                dbms_output.put_line('Datos dpto: ' || dep.codigoD || ', ' || dep.nombreD || ', ' || dep.DireccionD);
            END LOOP;
            
            SELECT COUNT(t2.numnodo) INTO aux
            FROM indexdepskip t, TABLE(t.grupoDePunteros) t2
            WHERE t.numnodo = i;
            dbms_output.put_line('Número de punteros: ' || aux);
            
            ptrsnodos := 'De arriba a abajo los punteros apuntan a los nodos: ';
            j := 1;
            FOR ptr IN (SELECT t2.numnodo num FROM indexdepskip t, TABLE(t.grupoDePunteros) t2 WHERE t.numnodo = i) LOOP
                mi_array(j) := ptr.num;
                j := j+1;
            END LOOP;
            j := mi_array.LAST;
            WHILE j IS NOT NULL LOOP
                ptrsnodos := ptrsnodos || mi_array(j);
                IF j = 2 THEN
                    ptrsnodos := ptrsnodos || ' y ';
                ELSIF j = 1 THEN
                    ptrsnodos := ptrsnodos || '.';
                ELSE
                    ptrsnodos := ptrsnodos || ',';
                END IF;
                mi_array.DELETE(j);
                j := mi_array.LAST;
            END LOOP;
            dbms_output.put_line(ptrsnodos);
            SELECT numnodo INTO i FROM indexdepskip WHERE ptrback = i;
        END LOOP;
        dbms_output.put_line('.');
    END LOOP;
END;
/

BEGIN
ImprimirAscSL;
END;
/