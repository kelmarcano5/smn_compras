SELECT
	smn_condicion_financiera_id AS id,
	cfi_codigo||'-'||cfi_description AS item 
FROM	
	smn_base.smn_condicion_financiera
INNER JOIN
	smn_base.smn_auxiliar ON smn_base.smn_condicion_financiera.smn_condicion_financiera_id = smn_base.smn_auxiliar.aux_condicion_financiera_rf
WHERE
	smn_auxiliar_id = ${fld:id}	