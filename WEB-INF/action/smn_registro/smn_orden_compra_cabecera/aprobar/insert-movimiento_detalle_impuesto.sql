INSERT INTO smn_inventario.smn_movimiento_detalle_impuesto
(
	smn_mov_det_impuesto_id,
	smn_movimiento_detalle_id,
	smn_cod_impuesto_deduc_rf,
	mdi_monto_base,
	smn_porcentaje_impuesto_rf,
	mdi_sustraendo_rf,
	mdi_tipo_movimiento,
	mdi_monto_impuesto_ml,
	smn_moneda,
	smn_tasa,
	mdi_monto_impuesto_ma,
	mdi_idioma,
	mdi_usuario,
	mdi_fecha_registro,
	mdi_hora
)
VALUES
(
	nextval('smn_inventario.seq_smn_movimiento_detalle_impuesto'), --smn_movimiento_detalle_impuesto_id
	${fld:smn_movimiento_detalle_id}, --smn_movimiento_detalle_id
	${fld:smn_cod_impuesto_deduc_rf}, --smn_cod_impuesto_deduc_rf
	${fld:oci_monto_base_ml}, --mdi_monto_base_ml
	${fld:smn_porcentaje_impuesto_rf}, --smn_porcentaje_impuesto_rf
	${fld:oci_sustraendo_ml}, --mdi_sustraendo_ml
	${fld:oci_tipo_impuesto_rf}, --mdi_tipo_movimiento
	${fld:oci_monto_impuesto_ml}, --mdi_monto_impuesto_ml
	${fld:smn_moneda}, --smn_moneda
	${fld:smn_tasa}, --smn_tasa
	${fld:oci_monto_impuesto_ma}, --mdi_monto_impuesto_ma
	'${def:locale}', --mdi_idioma
    '${def:user}', --mdi_usuario
    {d '${def:date}'}, --mdi_fecha_registro
    '${def:time}' --mdi_hora
)

RETURNING smn_mov_det_impuesto_id;