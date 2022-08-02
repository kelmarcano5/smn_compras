select
	case
	when smn_compras.smn_requisicion_detalle.smn_naturaleza_id='IT' then '${lbl:b_item}'
	when smn_compras.smn_requisicion_detalle.smn_naturaleza_id='SE' then '${lbl:b_services}'
	when smn_compras.smn_requisicion_detalle.smn_naturaleza_id='AF' then '${lbl:b_actfij'
	end as smn_naturaleza_id,
	case
	when smn_compras.smn_requisicion_detalle.rrs_producto_encontrado='SI' then '${lbl:b_yes}'
	when smn_compras.smn_requisicion_detalle.rrs_producto_encontrado='NO' then '${lbl:b_not}'
	end as rrs_producto_encontrado,
	smn_compras.smn_requisicion_detalle.*
from
	smn_compras.smn_requisicion_detalle,
	smn_compras.smn_requisicion_cabecera
where
		smn_compras.smn_requisicion_detalle.smn_requisicion_cabecera_id=smn_compras.smn_requisicion_cabecera.smn_requisicion_cabecera_id and
		smn_compras.smn_requisicion_cabecera.smn_requisicion_cabecera_id=${fld:id}
