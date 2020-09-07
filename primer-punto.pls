set serveroutput on

declare
    -- declaración de cursores
    cursor tabla_prov_x_venta is 
    select distinct venta.codpv, proveedor.nompv, venta.codproducto
    from venta 
    inner join proveedor on proveedor.codpv = venta.codpv 
    order by venta.codpv;

    cursor tabla_count_codpv is 
    select distinct codpv, count(codpv) as nocodpv, nompv
    from (select distinct venta.codpv, proveedor.nompv, venta.codproducto
        from venta
        inner join proveedor ON proveedor.codpv = venta.codpv) 
    group by codpv, nompv
    order by codpv;

    -- Declaración de arrays
    type t_prov_x_venta is table of tabla_prov_x_venta%rowtype index by binary_integer;
    array_prov_x_venta t_prov_x_venta;

    type t_count_codpv is table of tabla_count_codpv%rowtype
    index by binary_integer;
    array_count_codpv t_count_codpv;

    type t_codproducto is table of venta.codproducto%type index by binary_integer;
    array_codproducto_mismo_codpv t_codproducto; -- Array de codproducto que coinciden en el mismo codpv
    
    -- declaración de variables
    no_filas_tabla_p_x_v number(8);
    no_filas_tabla_count_codpv number(8);
    i number;
    k number(8) := 1;
    l number(8) := 1; -- El 1 es por que aquí los ciclos (for) inician desde 1 a menos que se indique lo contrario

    t1 integer;
    t2 integer;  
begin
    t1 := DBMS_UTILITY.GET_TIME;
    -- Número de filas de la tabla resultante | otra forma es iterar el cursor hasta obtener el %rowcount del último
    select count(*) into no_filas_tabla_p_x_v from (select distinct venta.codpv, proveedor.nompv, venta.codproducto
    from venta 
    inner join proveedor on proveedor.codpv = venta.codpv 
    order by venta.codpv);

    for row in tabla_prov_x_venta loop
        array_prov_x_venta(k) := row;
        k := k + 1;
    end loop;

    for row in tabla_count_codpv loop
        array_count_codpv(l) := row;
        l := l + 1;
    end loop;

    dbms_output.put_line('-----------------------------------');

    for i in 1..array_count_codpv.count loop
        dbms_output.put_line('i: ' || i);
        for j in 1..array_prov_x_venta.count loop
            if array_prov_x_venta(j).codpv = array_count_codpv(i).codpv then
                dbms_output.put_line('.    j: ' || j || ', Proveedor X Venta: ' || array_prov_x_venta(j).codpv || ', ' || array_prov_x_venta(j).codproducto);
                
            end if;
        end loop;
    end loop;
    t2 := DBMS_UTILITY.GET_TIME;
    DBMS_OUTPUT.PUT_LINE('tiempo ' || (t2 - t1)/100);
end;
/