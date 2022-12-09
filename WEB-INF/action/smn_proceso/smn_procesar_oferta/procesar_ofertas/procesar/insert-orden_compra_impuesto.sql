INSERT INTO smn_compras.smn_orden_compra_impuesto
(
	smn_impuesto_oc_id,
	smn_orden_compra_detalle_id,
	smn_cod_impuesto_deduc_rf,
	oci_monto_base_ml,
	oci_porcentaje_impuesto,
	oci_sustraendo_ml,
	smn_tipo_impuesto_rf,
	oci_monto_impuesto_ml,
	smn_moneda,
	smn_tasa,
	oci_monto_impuesto_ma,
	oci_idioma,
	oci_usuario,
	oci_fecha_registro,
	oci_hora
)
VALUES
(
	nextval('smn_compras.seq_smn_orden_compra_impuesto'), --smn_impuesto_oc_id
	${fld:smn_orden_compra_detalle_id}, --smn_orden_compra_detalle_id
	${fld:smn_cod_impuesto_deduc_rf}, --smn_cod_impuesto_deduc_rf
	${fld:oci_monto_base_ml}, --oci_monto_base_ml
	${fld:imp_porcentaje_base}, --oci_porcentaje_impuesto
	${fld:imp_ui_sustraendo}, --oci_sustraendo_ml
	${fld:imp_tipo_codigo}, --smn_tipo_impuesto_rf
	${fld:oci_monto_impuesto_ml}, --oci_monto_impuesto_ml
	${fld:smn_moneda}, --smn_moneda
	${fld:smn_tasa}, --smn_tasa
	${fld:oci_monto_impuesto_ma}, --oci_monto_impuesto_ma
	'${def:locale}', --oci_idioma
	'${def:user}', --oci_usuario
	{d '${def:date}'}, --oci_fecha_registro
	'${def:time}' --oci_hora
)
RETURNING smn_impuesto_oc_id;