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
	ocd_estatus,
	ocd_idioma,
	ocd_usuario,
	ocd_fecha_registro,
	ocd_hora 
)
VALUES
(
	nextval('smn_compras.seq_smn_orden_compra_detalle'),
	${fld:smn_orden_compra_cabecera_id},
	${fld:smn_linea_id},
	${fld:smn_servicio_id},
	${fld:smn_item_id},
	${fld:smn_afijo_id},
	${fld:smn_contrato_id},
	${fld:ocd_descripcion},
	${fld:rss_cantidad},
	null, /* cantidad recibida */
	(SELECT
		smn_unidad_medida_compra_rf
	 FROM
	 	smn_inventario.smn_caracteristica_item
	 WHERE
	 	smn_item_rf = ${fld:smn_item_id}
	),
	${fld:rrs_precio},
	${fld:rrs_monto},
	${fld:rim_monto_impuesto},
	${fld:drc_monto_descuento},
	'0.0',
	${fld:smn_moneda_id},
	(SELECT 
		smn_tasa_rf 
	 FROM 
	 	smn_compras.smn_orden_compra_cabecera 
	 WHERE 
	 	smn_orden_compra_cabecera_id = ${fld:smn_requisicion_cabecera_id}
	),
	${fld:rrs_precio_moneda_alterna},
	${fld:rrs_monto_moneda_alterna},
	(SELECT 
		rld_monto_imp_moneda_alterna
	 FROM
	 	smn_compras.smn_impuest_deducc_detalle
	 WHERE
	 	smn_requisicion_detalle_id = ${fld:smn_requisicion_detalle_id}
	),
	(SELECT
		drc_monto_descuento_ma
	 FROM
	 	smn_compras.smn_req_detalle_dcto_retenc
	 WHERE
	 	smn_requisicion_detalle_id = ${fld:smn_requisicion_detalle_id}
	),
	'RE',
	'${def:locale}',
    '${def:user}',
    {d '${def:date}'},
    '${def:time}'
)
RETURNING smn_orden_compra_detalle_id;