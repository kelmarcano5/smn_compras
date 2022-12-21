select
		smn_compras.smn_cotizacion_f_entrega.smn_cotizacion_f_entrega_id,
	${field}
from
	smn_compras.smn_cotizacion_f_entrega
where
		smn_compras.smn_cotizacion_f_entrega.smn_cotizacion_f_entrega_id is not null
	${filter}
	
	
