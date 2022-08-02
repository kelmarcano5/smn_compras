INSERT INTO smn_compras.smn_orden_compra_detalle
(
	smn_orden_compra_detalle_id,
	smn_orden_compra_cabecera_id,
	smn_linea_id,
	smn_servicios_id,
	smn_item_rf,
	smn_afijo_rf,
	smn_contrato_modulo_id,
	ocd_descripcion,
	ocd_cantidad_pedida,
	ocd_cantidad_recibida,
	smn_unidad_medida_rf,
	ocd_costo_ml,
	ocd_monto_bruto_ml,
	ocd_monto_impuesto_ml,
	ocd_monto_desc_reten_ml,
	ocd_monto_neto_ml,
	smn_moneda_rf,
	smn_tasa_rf,
	ocd_costo_ma,
	ocd_monto_bruto_ma,
	ocd_monto_impuesto_ma,
	ocd_monto_desc_reten_ma,
	ocd_monto_neto_ma,
	ocd_estatus,
	ocd_idioma,
	ocd_usuario,
	ocd_fecha_registro,
	ocd_hora
)
VALUES
(
	${seq:nextval@smn_compras.seq_smn_orden_compra_detalle},
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
	?,
	?,
	'${def:locale
}',
	'${def:user
}',
	'${def:date
}',
	'${def:time
}'
)
