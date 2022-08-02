SELECT
	smn_motivos_id AS id,
	mtv_codigo||' - '||mtv_nombre AS item
FROM
	smn_compras.smn_motivo