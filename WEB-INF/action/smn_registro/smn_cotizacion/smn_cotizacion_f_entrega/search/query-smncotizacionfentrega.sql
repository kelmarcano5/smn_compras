select
		smn_compras.smn_cotizacion_f_entrega.smn_cotizacion_f_entrega_id,
	case
	when smn_compras.smn_cotizacion_f_entrega.cfe_status='RE' then '${lbl:b_register}'
	when smn_compras.smn_cotizacion_f_entrega.cfe_status='GE' then '${lbl:b_generated}'
	end as cfe_status,
	smn_compras.smn_cotizacion.cot_numero_documento as smn_cotizacion_id,
	smn_compras.smn_cotizacion_f_entrega.cfe_consecutivo,
	smn_compras.smn_cotizacion_f_entrega.cfe_cantidad,
	smn_compras.smn_cotizacion_f_entrega.cfe_fecha_entrega
from
	smn_compras.smn_cotizacion_f_entrega
    inner join smn_compras.smn_cotizacion on smn_compras.smn_cotizacion.smn_cotizacion_id = smn_compras.smn_cotizacion_f_entrega.smn_cotizacion_id
 where 
		smn_compras.smn_cotizacion.smn_cotizacion_id=${fld:id4}