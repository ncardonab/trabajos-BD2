set SERVEROUTPUT on


CREATE OR REPLACE PROCEDURE JoinDep AS
    deptoEmpleado NUMBER := 0;

BEGIN
    FOR linea IN (SELECT * FROM empleado) loop
        deptoEmpleado := linea.depE;
        
        FOR sk IN (SELECT * FROM indexdepskip) loop
            BEGIN
                IF deptoEmpleado = sk.codigoD THEN
                    dbms_output.put_line('('||linea.codigoE || ', '|| linea.nombreE || ', '|| deptoEmpleado || ', '|| sk.codigoD || ', '|| sk.nombreD || ', '||sk.direccionD||')');
                END IF;
            END;
        
        end loop;

    end loop;

END;
/ 

  

BEGIN
JoinDep;
END;
/