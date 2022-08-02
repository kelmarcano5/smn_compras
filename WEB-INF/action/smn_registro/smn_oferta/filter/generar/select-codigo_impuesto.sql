SELECT
	imp_porcentaje_base,
	imp_ui_sustraendo,
	imp_tipo_codigo
FROM
	smn_base.smn_codigos_impuestos
WHERE
	smn_codigos_impuestos_id = ${fld:smn_cod_impuesto_deduc_rf}