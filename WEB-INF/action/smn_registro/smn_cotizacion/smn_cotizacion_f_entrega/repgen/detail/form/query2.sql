select
		smn_compras.smn_cotizacion_f_entrega.smn_cotizacion_id,
	smn_compras.smn_cotizacion_f_entrega.cfe_consecutivo,
	smn_compras.smn_cotizacion_f_entrega.cfe_cantidad,
	smn_compras.smn_cotizacion_f_entrega.cfe_fecha_entrega,
	smn_compras.smn_cotizacion_f_entrega.cfe_status
from
	smn_compras.smn_cotizacion_f_entrega 
where
	smn_compras.smn_cotizacion_f_entrega.smn_cotizacion_f_entrega_id = ${fld:id}
