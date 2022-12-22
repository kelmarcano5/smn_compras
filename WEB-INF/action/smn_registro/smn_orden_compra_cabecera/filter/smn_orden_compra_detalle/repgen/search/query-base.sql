select
		smn_compras.smn_orden_compra_detalle.smn_orden_compra_detalle_id,
	${field}
from
	smn_compras.smn_orden_compra_detalle
where
		smn_compras.smn_orden_compra_detalle.smn_orden_compra_detalle_id is not null
	${filter}
	
	
