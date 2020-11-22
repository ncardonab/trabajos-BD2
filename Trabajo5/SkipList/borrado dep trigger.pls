set SERVEROUTPUT on

CREATE OR REPLACE TRIGGER DeleteDep

BEFORE DELETE ON departamento
FOR EACH ROW
DECLARE
    i NUMBER;
    nodo indexdepskip%ROWTYPE;
    ptrback NUMBER;

    nodoBack indexdepskip%ROWTYPE;
    ptrNodoBack NUMBER;

    nodoNext indexdepskip%ROWTYPE;
    ptrNodoNext NUMBER;    
BEGIN
    dbms_output.put_line(:OLD.codigoD);
    -- Nodo a eliminar 
    SELECT indexdepskip_type(numnodo, codigoD, nombreD, direccionD, grupoDePunteros, ptrback) INTO nodo FROM indexdepskip WHERE codigoD = :OLD.codigoD;
    dbms_output.put_line(nodo.numnodo);
END;
/