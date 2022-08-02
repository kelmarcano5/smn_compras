select
		smn_compras.smn_roles.smn_roles_id,
	case
	when smn_compras.smn_roles.rol_tipo='SO' then '${lbl:b_solicitante}'
	when smn_compras.smn_roles.rol_tipo='AP' then '${lbl:b_autorizador}'
	end as rol_tipo,
	smn_base.smn_corporaciones.crp_codigo||'-'||smn_base.smn_corporaciones.crp_nombre as smn_corporaciones_id,
	smn_base.smn_entidades.ent_codigo||'-'||smn_base.smn_entidades.ent_descripcion_corta as smn_entidades_id,
	smn_base.smn_sucursales.suc_codigo||'-'||smn_base.smn_sucursales.suc_nombre as smn_sucursales_id,
	smn_base.smn_areas_servicios.ase_codigo||'-'||smn_base.smn_areas_servicios.ase_descripcion as smn_areas_servicios_id,
	smn_base.smn_unidades_servicios.uns_codigo||'-'||smn_base.smn_unidades_servicios.uns_descripcion as smn_unidades_servicios_id,
	smn_base.smn_estructura_organizacional.eor_codigo||'-'||smn_base.smn_estructura_organizacional.eor_nombre as smn_estructura_organizacional_id,
	smn_compras.smn_roles.rol_fecha_registro
	
from
	smn_compras.smn_roles

		left outer join smn_base.smn_corporaciones on smn_base.smn_corporaciones.smn_corporaciones_id = smn_compras.smn_roles.smn_corporaciones_id
		left outer join smn_base.smn_entidades on smn_base.smn_entidades.smn_entidades_id = smn_compras.smn_roles.smn_entidades_id
		left outer join smn_base.smn_sucursales on smn_base.smn_sucursales.smn_sucursales_id = smn_compras.smn_roles.smn_sucursales_id
		left outer join smn_base.smn_areas_servicios on smn_base.smn_areas_servicios.smn_areas_servicios_id = smn_compras.smn_roles.smn_areas_servicios_id
		left outer join smn_base.smn_unidades_servicios on smn_base.smn_unidades_servicios.smn_unidades_servicios_id = smn_compras.smn_roles.smn_unidades_servicios_id
		left outer join smn_base.smn_estructura_organizacional on smn_base.smn_estructura_organizacional.smn_estructura_organizacional_id = smn_compras.smn_roles.smn_estructura_organizacional_id
		
		
