SELECT
	smn_entidades_id AS id,
	ent_codigo || ' - ' || ent_descripcion_corta AS item
FROM
	smn_base.smn_entidades