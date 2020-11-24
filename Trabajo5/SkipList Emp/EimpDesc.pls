set SERVEROUTPUT on

CREATE OR REPLACE PROCEDURE ImprimirDescSL IS
c NUMBER;
aux NUMBER;
ptrsnodos VARCHAR2(1000);

TYPE t_array IS TABLE OF NUMBER(3) INDEX BY BINARY_INTEGER;
mi_array t_array;

i NUMBER := 1;
j NUMBER := 1;
k NUMBER := 1;
l NUMBER;
m NUMBER;
BEGIN
    SELECT COUNT(*) INTO i FROM indexempskip;
    i := i-1;

    WHILE i > 0 LOOP
        FOR nodo IN (SELECT * FROM indexempskip WHERE numnodo = i) LOOP
            dbms_output.put_line('Nodo ' || i);
            
            FOR dep IN (SELECT * FROM departamento WHERE codigoD = nodo.codigoD) LOOP
                dbms_output.put_line('Datos dpto: ' || dep.codigoD || ', ' || dep.nombreD || ', ' || dep.DireccionD);
            END LOOP;

            IF i = 1 THEN
                dbms_output.put_line('Datos dpto: No tiene');
            END IF;

            -- Imprimir empleados
            FOR emp IN (SELECT t2.codigoE cod, t2.nombreE nom FROM indexempskip t, TABLE(t.empleados) t2 WHERE t.numnodo = i) LOOP
                dbms_output.put_line(emp.cod || ', ' || emp.nom);
            END LOOP;
            
            SELECT COUNT(t2.numnodo) INTO aux
            FROM indexempskip t, TABLE(t.grupoDePunteros) t2
            WHERE t.numnodo = i;
            dbms_output.put_line('Número de punteros: ' || aux);
            
            ptrsnodos := 'De arriba a abajo los punteros apuntan a los nodos: ';
            FOR ptr IN (SELECT t2.numnodo num, COUNT(t2.numnodo) cant FROM indexempskip t, TABLE(t.grupoDePunteros) t2 WHERE t.numnodo = i GROUP BY t2.numnodo) LOOP
                mi_array(ptr.num) := ptr.cant;
            END LOOP;
            SELECT COUNT(*) total INTO l FROM indexempskip t, TABLE(t.grupoDePunteros) t2 WHERE t.numnodo = i;
            j := mi_array.LAST;
            WHILE l >= 1 LOOP
                m := mi_array(j);
                FOR k IN 1 .. m LOOP
                    ptrsnodos := ptrsnodos || j;
                    IF l = 2 THEN
                        ptrsnodos := ptrsnodos || ' y ';
                    ELSIF l = 1 THEN
                        ptrsnodos := ptrsnodos || '.';
                    ELSE
                        ptrsnodos := ptrsnodos || ', ';
                    END IF;
                    l := l - 1;
                END LOOP;
                mi_array.DELETE(j);
                j := mi_array.LAST;
            END LOOP;
            dbms_output.put_line(ptrsnodos);
            SELECT ptrback INTO i FROM indexempskip WHERE numnodo = i;
        END LOOP;
        dbms_output.put_line(chr(10));
    END LOOP;
END;
/

BEGIN
ImprimirDescSL;
END;
/
