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
	occ_monto_ml,
	occ_monto_impuesto_ml,
	occ_monto_desc_rete_ml,
	occ_monto_neto_ml,
	occ_monto_ma,
	occ_monto_impuesto_ma,
	occ_monto_neto_ma,
	occ_monto_usado_ml,
	occ_estatus,
	occ_idioma,
	occ_usuario,
	occ_fecha_registro,
	occ_hora
    --occ_aplica_anticipo,
    --occ_porcentaje_anticipo,
    --occ_monto_anticipo
)
VALUES
(
	nextval('smn_compras.seq_smn_orden_compra_cabecera'), --smn_orden_compra_cabecera_id
	${fld:smn_requisicion_cabecera_id}, --smn_requisicion_cabecera_id
	${fld:smn_entidad_id}, --smn_entidad_rf
	${fld:smn_sucursal_id}, --smn_sucursal_rf
	${fld:smn_documento_id}, --smn_documento_id
	(SELECT --occ_orden_compra_numero
		COUNT(occ_orden_compra_numero)+1 
	 FROM 
		smn_compras.smn_orden_compra_cabecera
	),
	${fld:occ_descripcion}, --occ_descripcion
	${fld:smn_proveedor_id}, --smn_proveedor_id
	${fld:smn_auxiliar_rf}, --smn_auxiliar_rf
	{d '${def:date}'}, --occ_fecha_elaboracion
	{d '${def:date}'}, --occ_fecha_orde_compra
	${fld:smn_forma_pago_rf}, --smn_forma_pago_rf
	${fld:smn_condicion_financiera_rf}, --smn_condicion_pago_rf
	0, --occ_monto_ml
	0, --occ_monto_impuesto_ml
	0, --occ_monto_desc_rete_ml
	0, --occ_monto_neto_ml
	0, --occ_monto_ma
	0, --occ_monto_impuesto_ma
	0, --occ_monto_neto_ma
	0, --occ_monto_usado_ml
	'RE',
	'${def:locale}',
    '${def:user}',
    {d '${def:date}'},
    '${def:time}'
    --${fld:ofe_aplica_anticipo}, --occ_aplica_anticipo
    --${fld:ofe_porcentaje_anticipo}, --occ_porcentaje_anticipo
    --${fld:ofe_monto_anticipo} --occ_monto_anticipo
)

RETURNING smn_orden_compra_cabecera_id