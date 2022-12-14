INSERT INTO smn_compras.smn_requisicion_detalle
(
	smn_requisicion_detalle_id,
	smn_requisicion_cabecera_id,
	smn_cabecera_version_id,
	smn_linea_id,
	smn_naturaleza_id,
	smn_servicio_id,
	smn_item_id,
	smn_afijo_id,
	rrs_producto_encontrado,
	smn_contrato_id,
	rrs_motivo_variacion,
	rrs_porcentaje,
	rss_cantidad,
	rrs_precio,
	rrs_monto,
	smn_moneda_id,
	rrs_precio_moneda_alterna,
	rrs_monto_moneda_alterna,
	smn_proveedor_id,
	rrs_especificaciones_del_requerimiento,
	rrs_fecha_de_requerido,
	rrs_observaciones,
	rrs_fecha_registro
)
VALUES
(
	${seq:nextval@smn_compras.seq_smn_requisicion_detalle},
	?,
	?,
	?,
	?,
	?,
	?,
	?,
	?,
	?,
	?,
	?,
	?,
	?,
	?,
	?,
	?,
	?,
	?,
	?,
	?,
	?,
	{d '${def:date}'}
)
