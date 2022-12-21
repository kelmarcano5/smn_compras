INSERT INTO smn_compras.smn_orden_compra_detalle
(
	smn_orden_compra_detalle_id,
	smn_orden_compra_cabecera_id,
	smn_linea_id,
	smn_servicios_id,
	smn_item_rf,
	smn_afijo_rf,
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
	ocd_hora,
	smn_oferta_id,
	smn_requisicion_cabecera_id
)
VALUES
(
	nextval('smn_compras.seq_smn_orden_compra_detalle'), --smn_orden_compra_detalle_id
	${fld:smn_orden_compra_cabecera_id}, --smn_orden_compra_cabecera_id
	${fld:smn_linea_id}, --smn_linea_id
	${fld:smn_servicios_compras_id}, --smn_servicios_id
	${fld:smn_item_compras_id}, --smn_item_rf
	${fld:smn_activo_fijo_compras_id}, --smn_afijo_rf,
	${fld:ocd_descripcion}, --ocd_descripcion
	${fld:ocd_cantidad_pedida}, --ocd_cantidad_pedida
	${fld:ofe_cantidad}, --ocd_cantidad_recibida
	${fld:smn_unidad_medida_rf}, --smn_unidad_medida_rf
	${fld:ofe_precio_ml}, --ocd_costo_ml
	${fld:ofe_monto_ml}, --ocd_monto_bruto_ml
	0.0, --ocd_monto_impuesto_ml
	0.0, --ocd_monto_desc_reten_ml
	0.0, --ocd_monto_neto_ml
	${fld:ofe_moneda_id}, --smn_moneda_rf
	${fld:ofe_tasa}, --smn_tasa_rf
	${fld:ofe_precio_ma}, --ocd_costo_ma
	${fld:ofe_monto_ma}, --ocd_monto_bruto_ma
	0.0, --ocd_monto_impuesto_ma
	0.0, --ocd_monto_desc_reten_ma
	0.0, --ocd_monto_neto_ma
	'RE',
	'${def:locale}',
    '${def:user}',
    {d '${def:date}'},
    '${def:time}',
    ${fld:smn_oferta_id}, --smn_oferta_id
    ${fld:smn_requisicion_cabecera_id} --smn_requisicion_cabecera_id
)
RETURNING smn_orden_compra_detalle_id;