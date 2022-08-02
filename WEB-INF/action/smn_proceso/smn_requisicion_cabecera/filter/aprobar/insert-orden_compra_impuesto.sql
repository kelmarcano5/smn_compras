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
	oci_idioma,
	oci_usuario,
	oci_fecha_registro,
	oci_hora
)
VALUES
(
	nextval('smn_compras.seq_smn_orden_compra_impuesto'),
	${fld:smn_orden_compra_detalle_id},
	${fld:smn_cod_impuesto_deduc_rf},
	${fld:rim_monto_base},
	${fld:smn_porcentaje_impuesto},
	${fld:smn_sustraendo},
	${fld:imp_tipo_codigo},
	${fld:rim_monto_impuesto},
	'${def:locale}',
    '${def:user}',
    {d '${def:date}'},
    '${def:time}'
)
RETURNING smn_impuesto_oc_id;