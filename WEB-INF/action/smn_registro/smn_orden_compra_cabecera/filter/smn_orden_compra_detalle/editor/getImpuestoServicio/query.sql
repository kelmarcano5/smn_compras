SELECT
	smn_base.smn_codigos_impuestos.imp_porcentaje_calculo,
	smn_base.smn_codigos_impuestos.imp_ui_sustraendo
FROM 
	smn_base.smn_rel_serv_cod_impuesto 
	INNER JOIN 
	smn_base.smn_codigos_impuestos ON smn_base.smn_rel_serv_cod_impuesto.smn_codigos_impuestos_id = smn_base.smn_codigos_impuestos.smn_codigos_impuestos_id
WHERE 
	smn_base.smn_rel_serv_cod_impuesto.smn_servicios_id = ${fld:smn_servicios_id}