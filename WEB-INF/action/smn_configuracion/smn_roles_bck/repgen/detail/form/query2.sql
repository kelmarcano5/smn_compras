select
		smn_compras.smn_roles.rol_tipo,
	smn_compras.smn_roles.smn_corporaciones_id,
	smn_compras.smn_roles.smn_entidades_id,
	smn_compras.smn_roles.smn_sucursales_id,
	smn_compras.smn_roles.smn_areas_servicios_id,
	smn_compras.smn_roles.smn_unidades_servicios_id,
	smn_compras.smn_roles.smn_estructura_organizacional_id,
	smn_compras.smn_roles.rol_fecha_registro
from
	smn_compras.smn_roles 
where
	smn_compras.smn_roles.smn_roles_id = ${fld:id}
