select
select
select
	case
	when smn_compras.smn_impuest_deducc_detalle.rld_cod_descuento='IM' then '${lbl:b_yes}'
	when smn_compras.smn_impuest_deducc_detalle.rld_cod_descuento='DC' then '${lbl:b_not}'
	end as rld_cod_descuento,
	smn_compras.smn_impuest_deducc_detalle.*
from
	smn_compras.smn_impuest_deducc_detalle,
	smn_compras.smn_requisicion_detalle
where
		smn_compras.smn_impuest_deducc_detalle.smn_requisicion_detalle_id=smn_compras.smn_requisicion_detalle.smn_requisicion_detalle_id and
		smn_compras.smn_requisicion_detalle.smn_requisicion_detalle_id=${fld:id}
