select
		smn_compras.smn_servicio.smn_servicio_id,
	case
	when smn_compras.smn_servicio.sco_maneja_contrato='SI' then '${lbl:b_yes}'
	when smn_compras.smn_servicio.sco_maneja_contrato='NO' then '${lbl:b_not}'
	end as sco_maneja_contrato,
	smn_compras.smn_servicio.sco_codigo,
	smn_compras.smn_servicio.sco_nombre,
	smn_compras.smn_servicio.sco_maneja_contrato,
	aux_codigo ||'-'|| aux_descripcion AS sco_proveedor_exclusivo,
	smn_base.smn_areas_servicios.ase_codigo||'-'||smn_base.smn_areas_servicios.ase_descripcion as smn_area_servicio_id,
	smn_base.smn_unidades_servicios.uns_codigo||'-'||smn_base.smn_unidades_servicios.uns_descripcion as smn_unidades_servicios_id,
	smn_compras.smn_servicio.sco_fecha_registro
	
from
	smn_compras.smn_servicio
	left outer join smn_base.smn_areas_servicios on smn_base.smn_areas_servicios.smn_areas_servicios_id = smn_compras.smn_servicio.smn_area_servicio_id
	left outer join smn_base.smn_unidades_servicios on smn_base.smn_unidades_servicios.smn_unidades_servicios_id = smn_compras.smn_servicio.smn_unidades_servicios_id
	left outer join smn_compras.smn_proveedor ON smn_compras.smn_proveedor.smn_proveedor_id = smn_compras.smn_servicio.sco_proveedor_exclusivo
	left outer join	smn_base.smn_auxiliar ON smn_base.smn_auxiliar.smn_auxiliar_id = smn_compras.smn_proveedor.smn_auxiliar_rf
