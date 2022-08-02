SELECT
	smn_base.smn_codigos_impuestos.smn_codigos_impuestos_id,
	smn_base.smn_codigos_impuestos.imp_porcentaje_calculo,
	smn_base.smn_codigos_impuestos.imp_ui_sustraendo
FROM
	smn_base.smn_rel_item_cod_impuesto
INNER JOIN
	smn_base.smn_codigos_impuestos
ON
	smn_base.smn_rel_item_cod_impuesto.smn_codigos_impuestos_id = smn_base.smn_codigos_impuestos.smn_codigos_impuestos_id
WHERE
	smn_item_id = ${fld:smn_item_rf}
UNION
SELECT
	smn_base.smn_codigos_impuestos.smn_codigos_impuestos_id,
	smn_base.smn_codigos_impuestos.imp_porcentaje_calculo,
	smn_base.smn_codigos_impuestos.imp_ui_sustraendo
FROM
	smn_base.smn_rel_serv_cod_impuesto
INNER JOIN
	smn_base.smn_codigos_impuestos
ON
	smn_base.smn_rel_serv_cod_impuesto.smn_codigos_impuestos_id = smn_base.smn_codigos_impuestos.smn_codigos_impuestos_id
WHERE
	smn_servicios_id = ${fld:smn_servicio_id}
UNION
SELECT
	smn_base.smn_codigos_impuestos.smn_codigos_impuestos_id,
	smn_base.smn_codigos_impuestos.imp_porcentaje_calculo,
	smn_base.smn_codigos_impuestos.imp_ui_sustraendo
FROM
	smn_base.smn_rel_afijo_cod_impuesto
INNER JOIN
	smn_base.smn_codigos_impuestos
ON
	smn_base.smn_rel_afijo_cod_impuesto.smn_codigos_impuestos_id = smn_base.smn_codigos_impuestos.smn_codigos_impuestos_id
WHERE
	smn_afijo_id = ${fld:smn_afijo_id}
	

	