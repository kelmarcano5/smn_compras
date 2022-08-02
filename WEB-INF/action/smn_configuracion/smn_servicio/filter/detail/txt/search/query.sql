select
	case
		when smn_compras.smn_servicio.sco_maneja_contrato='SI' then '${lbl:b_yes}'
		when smn_compras.smn_servicio.sco_maneja_contrato='NO' then '${lbl:b_not}'
	end as sco_maneja_contrato,
	case
		when smn_compras.smn_servicio.sco_proveedor_exclusivo='SI' then '${lbl:b_yes}'
		when smn_compras.smn_servicio.sco_proveedor_exclusivo='NO' then '${lbl:b_not}'
	end as sco_proveedor_exclusivo,
	(select smn_base.smn_areas_servicios.ase_codigo|| ' - ' || smn_base.smn_areas_servicios.ase_descripcion from  smn_base.smn_areas_servicios where smn_base.smn_areas_servicios.smn_areas_servicios_id is not null  and smn_base.smn_areas_servicios.smn_areas_servicios_id=smn_compras.smn_servicio.smn_area_servicio_id) as smn_area_servicio_id,
	(select smn_base.smn_unidades_servicios.uns_codigo|| ' - ' || smn_base.smn_unidades_servicios.uns_descripcion from  smn_base.smn_unidades_servicios where smn_base.smn_unidades_servicios.smn_unidades_servicios_id is not null  and smn_base.smn_unidades_servicios.smn_unidades_servicios_id=smn_compras.smn_servicio.smn_unidades_servicios_id) as smn_unidades_servicios_id,
	smn_compras.smn_servicio.*
from 
	smn_compras.smn_servicio
where
	smn_servicio_id = ${fld:id}
