select distinct codpv, count(codpv) as nocodpv, nompv
from (select distinct venta.codpv, proveedor.nompv, venta.codproducto
      from venta
      inner join proveedor ON proveedor.codpv = venta.codpv
      ) 
group by codpv, nompv
order by codpv;