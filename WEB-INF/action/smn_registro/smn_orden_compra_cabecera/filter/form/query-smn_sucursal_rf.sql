SELECT 
	smn_sucursales_id AS id, 
	suc_codigo || ' - ' || suc_nombre AS item 
FROM 
	smn_base.smn_sucursales