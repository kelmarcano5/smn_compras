select
	smn_compras.smn_lineas.lin_codigo||'-'||smn_compras.smn_lineas.lin_nombre as smn_lineas_id, 
	smn_compras.smn_servicio.sco_codigo||'-'||smn_compras.smn_servicio.sco_nombre as smn_servicio_id,
	smn_compras.smn_rel_linea_servicio.smn_rel_linea_servicio_id
from
	smn_compras.smn_lineas,
	smn_compras.smn_servicio,
	smn_compras.smn_rel_linea_servicio
where
	smn_rel_linea_servicio_id is not null
	and 	smn_compras.smn_lineas.smn_lineas_id=smn_compras.smn_rel_linea_servicio.smn_lineas_id and
	smn_compras.smn_servicio.smn_servicio_id=smn_compras.smn_rel_linea_servicio.smn_servicio_id
	${filter}
order by
		smn_rel_linea_servicio_id
