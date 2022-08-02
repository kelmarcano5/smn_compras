select
		smn_compras.smn_rel_linea_servicio.smn_lineas_id,
	smn_compras.smn_rel_linea_servicio.smn_servicio_id
from
	smn_compras.smn_rel_linea_servicio 
where
	smn_compras.smn_rel_linea_servicio.smn_rel_linea_servicio_id = ${fld:id}
