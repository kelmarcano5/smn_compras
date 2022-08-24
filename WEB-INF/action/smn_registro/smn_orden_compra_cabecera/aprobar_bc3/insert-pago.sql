INSERT INTO smn_pagos.smn_orden_compra
(
	smn_orden_compra_op_id,
	smn_orden_compra_rf,
	smn_documento_rf,
	smn_numero_documento_rf,
	smn_proveedor_id,
	smn_usuario_rf,
	ocp_monto_ml,
	ocp_impuesto_ml,
	smn_moneda_rf,
	ocp_monto_ma,
	ocp_monto_impuestos_ma,
	ocp_monto_usado_ml,
	ocp_impuesto_usado_ml,
	ocp_monto_usado_ma,
	ocp_monto_impuestos_usado_ma,
	ocp_monto_saldo_ml,
	ocp_impuestos_saldo_ml,
	ocp_monto_saldo_ma,
	ocp_monto_impuestos_saldo_ma,
	ocp_estatus,
	ocp_idioma,
	ocp_usuario,
	ocp_fecha_registro,
	ocp_hora
)
VALUES
(
	nextval('smn_pagos.seq_smn_orden_compra'),--smn_orden_compra_op_id
	${fld:smn_orden_compra_cabecera_id}, --smn_orden_compra_rf
	${fld:smn_documento_id}, --smn_documento_rf
	${fld:occ_orden_compra_numero}, --smn_numero_documento_rf
	${fld:smn_proveedor_id}, --smn_proveedor_id
	(
	 SELECT
		smn_base.smn_usuarios.smn_usuarios_id
	 FROM
	 	smn_base.smn_usuarios
	 INNER JOIN
	 	smn_seguridad.s_user
	 ON
	 	smn_base.smn_usuarios.smn_user_rf = smn_seguridad.s_user.user_id
	 WHERE	
	 	smn_seguridad.s_user.userlogin = '${def:user}'
	), --smn_usuario_rf
	${fld:occ_monto_neto_ml}, --ocp_monto_ml
	${fld:occ_monto_impuesto_ml}, --ocp_impuesto_ml
	${fld:smn_moneda_rf}, --smn_moneda_rf
	${fld:occ_monto_neto_ma}, --ocp_monto_ma
	${fld:occ_monto_impuesto_ma}, --ocp_monto_impuestos_ma
	0.0, --ocp_monto_usado_ml
	0.0, --ocp_impuesto_usado_ml
	null, --ocp_monto_usado_ma
	0.0, --ocp_monto_impuestos_usado_ma
	${fld:occ_monto_neto_ml}, --ocp_monto_saldo_ml
	${fld:occ_monto_impuesto_ml}, --ocp_impuestos_saldo_ml
	${fld:occ_monto_neto_ma}, --ocp_monto_saldo_ma
	${fld:occ_monto_impuesto_ma}, --ocp_monto_impuestos_saldo_ma
	'RE', --ocp_estatus
	'${def:locale}', --ocp_idioma 
	'${def:user}', --ocp_usuario
	{d '${def:date}'}, --ocp_fecha_registro
  	'${def:time}' --ocp_hora
)

RETURNING smn_orden_compra_op_id;