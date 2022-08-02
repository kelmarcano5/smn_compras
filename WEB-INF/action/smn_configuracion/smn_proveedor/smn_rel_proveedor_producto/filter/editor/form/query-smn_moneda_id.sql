SELECT
	smn_monedas_id AS id,
	mon_codigo ||'-'||mon_nombre AS item
FROM
	smn_base.smn_monedas