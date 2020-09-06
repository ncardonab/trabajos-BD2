set serveroutput on

declare
    -- Guardando la unión entre las ventas y los proveedores, organizados por el cod del provedoor | el lead trae el valor codpv pero de la proxima fila
    cursor rows is 
    select distinct venta.codpv, proveedor.nompv, venta.codproducto,
    lead (venta.codpv, 1) over (order by venta.codpv) as proxcodpv
    from venta 
    inner join proveedor on proveedor.codpv = venta.codpv 
    order by venta.codpv;

    -- Creando el tipo de array que es el tipo del join anterior 
    type t_prov_x_venta is table of rows%rowtype index by binary_integer;
    prov_x_venta t_prov_x_venta;

    type t_codproducto is table of venta.codproducto%type index by binary_integer;
    array_codproducto_mismo_codpv t_codproducto; -- Array de codproducto que coinciden en el mismo codpv

    -- declaración de variables
    i number;
    k number(8) := 1; -- En 1 por que aquí los ciclos (for) inician desde 1 a menos que se indique lo contrario
begin

    for row in rows loop
        prov_x_venta(k) := row;
        k := k + 1;
    end loop;
    i := prov_x_venta.first;
    while i is not null loop
        -- if prov_x_venta.codpv = prov_x_venta.next(i).codpv then
        dbms_output.put_line(prov_x_venta(i).codpv || ', ' || prov_x_venta(i).nompv || ', ' || prov_x_venta(i).codproducto);
        dbms_output.put_line(i);
        -- end if;
        i := prov_x_venta.next(i);
    end loop;
end;
/

-- dbms_output.put_line('[' || rows.codpv || '(' || rows.nompv || '), ' || rows.next(i).codpv || '(' || rows.next(i).nompv || ')] => {' || row.codproducto || ', ' || row.codproducto || ', ' || row.codproducto || '}');