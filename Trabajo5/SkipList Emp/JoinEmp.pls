set SERVEROUTPUT on


CREATE OR REPLACE PROCEDURE JoinEmp IS
tam1 NUMBER;
tam2 NUMBER;
i NUMBER :=0;
j NUMBER :=0;

codigoDep NUMBER(8);
emple NUMBER (8);

TYPE dep_type IS TABLE OF NUMBER INDEX BY BINARY_INTEGER;
dep_a dep_type;
TYPE emp_type IS TABLE OF NUMBER INDEX BY BINARY_INTEGER;
emp_a emp_type;
   
BEGIN
    SELECT COUNT(*) AS tam1 FROM indexdepskip;
    SELECT COUNT(*) AS tam2 FROM indexempskip;
    FOR lin IN (SELECT * FROM indexdepskip) loop
        IF lin.numnodo > 1  AND lin.numnodo < tam1 THEN
            dep_a(i) := lin.codigoD ; 
            i := i+1;
        END IF;
      
    end loop;

    FOR linea IN (SELECT * FROM indexempskip) loop
        IF linea.numnodo > 1  AND linea.numnodo < tam2 THEN
            emp_a(j) := linea.codigoD ; 
            j := j+1;
        END IF;
      
    end loop;

    codigoDep := dep_a.FIRST;
    emple := emp_a.FIRST;

    WHILE codigoDep IS NOT NULL  AND emple IS NOT NULL LOOP
        WHILE codigoDep IS NOT NULL  AND dep_a(codigoDep) <emp_a(emple) loop
            dbms_output.put_line(dep_a(codigoDep));
            codigoDep := dep_a.NEXT(codigoDep);          
        end loop;
        
        WHILE emple IS NOT NULL  AND dep_a(codigoDep) >emp_a(emple) loop
            dbms_output.put_line(emp_a(emple));
            emple := emp_a.NEXT(emple);          
        end loop;
        
        --dbms_output.put_line('EL nodo numero ' ||codigoDep || ' departamento ' || dep_a(codigoDep));
    END LOOP;
END;
/ 

 

BEGIN
JoinEmp;
END;
/