INSERT INTO smn_compras.smn_orden_compra_cabecera
(
	smn_orden_compra_cabecera_id,
	smn_requisicion_cabecera_id,
	smn_entidad_rf,
	smn_sucursal_rf,
	smn_documento_id,
	occ_orden_compra_numero,
	occ_descripcion,
	smn_proveedor_id,
	smn_auxiliar_rf,
	occ_fecha_elaboracion,
	occ_fecha_orde_compra,
	smn_forma_pago_rf,
	smn_condicion_pago_rf,
	occ_observacion,
	occ_monto_ml,
	occ_monto_impuesto_ml,
	occ_monto_desc_rete_ml,
	occ_monto_neto_ml,
	smn_moneda_rf,
	smn_tasa_rf,
	occ_monto_ma,
	occ_monto_impuesto_ma,
	occ_estatus,
	occ_idioma,
	occ_usuario,
	occ_fecha_registro,
	occ_hora
)
VALUES
(
	${seq:currval@smn_compras.seq_smn_orden_compra_cabecera},
	${fld:smn_requisicion_cabecera_id},
	${fld:smn_entidad_rf},
	${fld:smn_sucursal_rf},
	${fld:smn_documento_id},
	${fld:occ_orden_compra_numero},
	${fld:occ_descripcion},
	${fld:smn_proveedor_id},
	${fld:smn_auxiliar_rf},
	${fld:occ_fecha_elaboracion},
	${fld:occ_fecha_orde_compra},
	${fld:smn_forma_pago_rf},
	${fld:smn_condicion_pago_rf},
	${fld:occ_observacion},
	${fld:occ_monto_ml},
	${fld:occ_monto_impuesto_ml},
	${fld:occ_monto_desc_rete_ml},
	5000,
	${fld:smn_moneda_rf},
	${fld:smn_tasa_rf},
	${fld:occ_monto_ma},
	${fld:occ_monto_impuesto_ma},
	'RE',
	'${def:locale}',
	'${def:user}',
	{d '${def:date}'},
	'${def:time}'
)