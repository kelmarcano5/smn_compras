INSERT INTO smn_compras.smn_roles
(
	smn_roles_id,
	smn_usuarios_id,
	rol_tipo,
	smn_corporaciones_id,
	smn_entidades_id,
	smn_sucursales_id,
	smn_areas_servicios_id,
	smn_unidades_servicios_id,
	smn_estructura_organizacional_id,
	rol_fecha_registro
)
VALUES
(
	${seq:nextval@smn_compras.seq_smn_roles},
	?,
	?,
	?,
	?,
	?,
	?,
	?,
	?,
	{d '${def:date}'}
)
