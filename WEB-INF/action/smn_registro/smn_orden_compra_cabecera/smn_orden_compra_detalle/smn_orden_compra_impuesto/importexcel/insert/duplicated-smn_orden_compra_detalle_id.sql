select
	smn_orden_compra_detalle_id	
from 
	smn_compras.smn_orden_compra_detalle
where
	smn_orden_compra_detalle_id = ${fld:smn_orden_compra_detalle_id}
	