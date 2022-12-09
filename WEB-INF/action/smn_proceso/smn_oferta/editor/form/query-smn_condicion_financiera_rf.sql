SELECT 
	smn_condicion_financiera_id AS id,
	cfi_codigo||'-'||cfi_description AS item 
FROM	
	smn_base.smn_condicion_financiera