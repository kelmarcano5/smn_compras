select
		smn_compras.smn_orden_compra_impuesto.smn_orden_compra_impuesto_id,
	${field}
from
	smn_compras.smn_orden_compra_impuesto
where
		smn_compras.smn_orden_compra_impuesto.smn_orden_compra_impuesto_id is not null
	${filter}
	
	
