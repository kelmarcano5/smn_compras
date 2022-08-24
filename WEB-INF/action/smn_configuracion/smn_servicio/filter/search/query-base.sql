select
		smn_compras.smn_servicio.smn_servicio_id,
	case
	when smn_compras.smn_servicio.sco_maneja_contrato='SI' then '${lbl:b_yes}'
	when smn_compras.smn_servicio.sco_maneja_contrato='NO' then '${lbl:b_not}'
	end as sco_maneja_contrato,
	case
	when smn_compras.smn_servicio.sco_proveedor_exclusivo='SI' then '${lbl:b_yes}'
	when smn_compras.smn_servicio.sco_proveedor_exclusivo='NO' then '${lbl:b_not}'
	end as sco_proveedor_exclusivo,
	smn_compras.smn_servicio.sco_codigo,
	smn_compras.smn_servicio.sco_nombre,
	smn_compras.smn_servicio.sco_maneja_contrato,
	smn_compras.smn_servicio.sco_proveedor_exclusivo,
	smn_base.smn_areas_servicios.ase_codigo||'-'||smn_base.smn_areas_servicios.ase_descripcion as smn_area_servicio_id,
	smn_base.smn_unidades_servicios.uns_codigo||'-'||smn_base.smn_unidades_servicios.uns_descripcion as smn_unidades_servicios_id,
	smn_compras.smn_servicio.sco_fecha_registro
	
from
	smn_compras.smn_servicio

	left outer join smn_base.smn_areas_servicios on smn_base.smn_areas_servicios.smn_areas_servicios_id = smn_compras.smn_servicio.smn_area_servicio_id
		left outer join smn_base.smn_unidades_servicios on smn_base.smn_unidades_servicios.smn_unidades_servicios_id = smn_compras.smn_servicio.smn_unidades_servicios_id

where
	smn_servicio_id is not null
	${filter}
order by
		smn_servicio_id
