select
	smn_compras.smn_requisicion_detalle.smn_requisicion_detalle_id,
	smn_compras.smn_requisicion_detalle.smn_naturaleza_id as smn_naturaleza_id_pl0,
select
select
select
select
	case
	when smn_compras.smn_cotizacion.cot_estatus='SO' then '${lbl:b_solicited}'
	when smn_compras.smn_cotizacion.cot_estatus='AP' then '${lbl:b_aprobated}'
	end as cot_estatus,
	smn_compras.smn_cotizacion.*
from
	smn_compras.smn_requisicion_detalle,
	smn_compras.smn_cotizacion
where
	smn_compras.smn_requisicion_detalle.smn_requisicion_detalle_id=smn_compras.smn_cotizacion.smn_requisicion_detalle_id
	and
	smn_cotizacion_id = ${fld:id}
