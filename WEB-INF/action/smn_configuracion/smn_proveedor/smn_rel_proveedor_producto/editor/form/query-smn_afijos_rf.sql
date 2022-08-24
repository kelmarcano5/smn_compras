SELECT
	smn_afijo_id AS id,
	acf_codigo ||' - '|| acf_nombre AS item	
FROM
	smn_base.smn_activo_fijo
