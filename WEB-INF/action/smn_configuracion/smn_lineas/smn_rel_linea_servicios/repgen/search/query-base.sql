select
		smn_compras.smn_rel_linea_servicio.smn_rel_linea_servicio_id,
	${field}
from
	smn_compras.smn_rel_linea_servicio
where
		smn_compras.smn_rel_linea_servicio.smn_rel_linea_servicio_id is not null
	${filter}
	
	
