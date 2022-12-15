select
		smn_compras.smn_cotizacion.smn_cotizacion_id,
	smn_compras.smn_requisicion_detalle.smn_requisicion_detalle_id,
	smn_compras.smn_requisicion_detalle.smn_naturaleza_id as smn_naturaleza_id_pl0,
select
		smn_compras.smn_cotizacion.smn_cotizacion_id,
select
		smn_compras.smn_cotizacion.smn_cotizacion_id,
select
		smn_compras.smn_cotizacion.smn_cotizacion_id,
	case
	when smn_compras.smn_cotizacion.cot_estatus='SO' then '${lbl:b_solicited}'
	when smn_compras.smn_cotizacion.cot_estatus='AP' then '${lbl:b_aprobated}'
	end as cot_estatus,
	smn_compras.smn_cotizacion.smn_requisicion_detalle_id,
	smn_compras.smn_cotizacion.cot_secuencia,
	smn_compras.smn_cotizacion.smn_documento_id,
	smn_compras.smn_cotizacion.cot_numero_documento,
	smn_compras.smn_cotizacion.smn_proveedor_id,
	smn_compras.smn_cotizacion.cot_fecha_envio,
	smn_compras.smn_cotizacion.cot_fecha_requerido,
	smn_compras.smn_cotizacion.cot_estatus,
	smn_compras.smn_cotizacion.cot_fecha_registro
	
from
	smn_compras.smn_requisicion_detalle,
	smn_compras.smn_cotizacion
where
	smn_compras.smn_requisicion_detalle.smn_requisicion_detalle_id=smn_compras.smn_cotizacion.smn_requisicion_detalle_id
