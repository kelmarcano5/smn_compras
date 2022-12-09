select
		smn_compras.smn_req_detalle_impuesto.smn_req_detalle_impuesto_id,
	${field}
from
	smn_compras.smn_req_detalle_impuesto
where
		smn_compras.smn_req_detalle_impuesto.smn_req_detalle_impuesto_id is not null
	${filter}
	
	
