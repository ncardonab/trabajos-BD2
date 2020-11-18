set SERVEROUTPUT on
CREATE OR REPLACE PROCEDURE CrearSL(maxPtrs NUMBER) IS
    n pls_integer;
BEGIN

    SELECT dbms_random.value(1,maxPtrs) num
    into n
    FROM dual;

    dbms_output.put_line(n);

END;
/

BEGIN
CrearSL(8);
END;
/

CREATE OR REPLACE PROCEDURE punto1(var_n NUMBER) IS
    CURSOR tabla IS SELECT DISTINCT codpv, codproducto FROM venta ORDER BY codpv,codproducto; 
    original VARCHAR2(200):=''; 
    aux VARCHAR2(200):='';
    aux2 VARCHAR2(200):='';
    numeros VARCHAR2(200):='';
    numeros1 VARCHAR2(200):='';
    ene NUMBER(2); 
BEGIN 
    FOR datos IN (SELECT * FROM proveedor ORDER BY codpv) LOOP 
        aux:= (datos.codpv || ' ('||datos.nompv||')'); 
        original:= (datos.codpv || ' ('||datos.nompv||')');
        ene:=1; 
        FOR datos2 IN tabla LOOP
            IF datos.codpv=datos2.codpv THEN
                numeros:=(numeros||datos2.codproducto||',');
            END IF;
        END LOOP;
        numeros:= ('{'||SUBSTR(numeros,0,LENGTH(numeros)-1)||'}');
        FOR datos3 IN (SELECT * FROM proveedor ORDER BY codpv) LOOP 
            IF ene<var_n THEN
                IF datos3.codpv>datos.codpv THEN 
                    aux2:= (datos3.codpv || ' ('||datos3.nompv||')');
                    FOR datos4 IN tabla LOOP 
                        IF datos3.codpv=datos4.codpv THEN
                        numeros1:=(numeros1||datos4.codproducto||',');
                        END IF;
                    END LOOP;
                    numeros1:= ('{'||SUBSTR(numeros1,0,LENGTH(numeros1)-1)||'}');
                END IF; 
                IF numeros1=numeros THEN
                    aux:=(aux||','||aux2);
                    ene:=ene+1; 
                END IF;
                aux2:='';
                numeros1:=''; 
                IF ene=var_n THEN
                    DBMS_OUTPUT.PUT_LINE('['||aux||']'||' --> '||numeros);
                    ene:=1; 
                    aux:=original;
                END IF;
            END IF;
        END LOOP;
        aux:='';
        numeros:='';
    END LOOP;
END;
/