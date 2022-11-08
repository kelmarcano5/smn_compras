SELECT
	smn_base.smn_codigos_impuestos.smn_codigos_impuestos_id,
	smn_base.smn_codigos_impuestos.imp_porcentaje_base,
	smn_base.smn_codigos_impuestos.imp_porcentaje_calculo,
	smn_base.smn_codigos_impuestos.imp_ui_sustraendo,
	smn_base.smn_codigos_impuestos.imp_tipo_codigo
FROM
	smn_base.smn_codigos_impuestos
	INNER JOIN
	smn_base.smn_rel_item_cod_impuesto ON smn_base.smn_codigos_impuestos.smn_codigos_impuestos_id = smn_base.smn_rel_item_cod_impuesto.smn_codigos_impuestos_id
WHERE
	smn_base.smn_rel_item_cod_impuesto.smn_item_id = ${fld:smn_item_compras_id}
	