select
select
	case
	when smn_compras.smn_cotizacion_f_entrega.cfe_status='RE' then '${lbl:b_register}'
	when smn_compras.smn_cotizacion_f_entrega.cfe_status='GE' then '${lbl:b_generated}'
	end as cfe_status,
	smn_compras.smn_cotizacion_f_entrega.*
from
	smn_compras.smn_cotizacion_f_entrega
where
	smn_cotizacion_f_entrega_id = ${fld:id}
