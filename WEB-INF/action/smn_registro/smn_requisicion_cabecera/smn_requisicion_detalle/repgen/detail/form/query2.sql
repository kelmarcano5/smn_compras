select
		smn_compras.smn_requisicion_detalle.smn_requisicion_cabecera_id,
	smn_compras.smn_requisicion_detalle.smn_linea_id,
	smn_compras.smn_requisicion_detalle.smn_naturaleza_id,
	smn_compras.smn_requisicion_detalle.smn_servicio_id,
	smn_compras.smn_requisicion_detalle.smn_item_id,
	smn_compras.smn_requisicion_detalle.smn_afijo_id,
	smn_compras.smn_requisicion_detalle.rrs_producto_encontrado,
	smn_compras.smn_requisicion_detalle.smn_contrato_id,
	smn_compras.smn_requisicion_detalle.rrs_motivo_variacion,
	smn_compras.smn_requisicion_detalle.rrs_porcentaje,
	smn_compras.smn_requisicion_detalle.rss_cantidad,
	smn_compras.smn_requisicion_detalle.rrs_precio,
	smn_compras.smn_requisicion_detalle.rrs_monto,
	smn_compras.smn_requisicion_detalle.smn_moneda_id,
	smn_compras.smn_requisicion_detalle.rrs_precio_moneda_alterna,
	smn_compras.smn_requisicion_detalle.rrs_monto_moneda_alterna,
	smn_compras.smn_requisicion_detalle.smn_proveedor_id,
	smn_compras.smn_requisicion_detalle.rrs_especificaciones_del_requerimiento,
	smn_compras.smn_requisicion_detalle.rrs_fecha_de_requerido,
	smn_compras.smn_requisicion_detalle.rrs_observaciones,
	smn_compras.smn_requisicion_detalle.rrs_fecha_registro
from
	smn_compras.smn_requisicion_detalle 
where
	smn_compras.smn_requisicion_detalle.smn_requisicion_detalle_id = ${fld:id}
