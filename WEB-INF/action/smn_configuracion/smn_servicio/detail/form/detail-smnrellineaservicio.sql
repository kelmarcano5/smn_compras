select
	smn_compras.smn_lineas.smn_lineas_id,
	smn_compras.smn_lineas.lin_codigo as lin_codigo_pl0,
	smn_compras.smn_rel_linea_servicio.*
from
	smn_compras.smn_servicio ,
	smn_compras.smn_lineas,
	smn_compras.smn_rel_linea_servicio
where
		smn_compras.smn_rel_linea_servicio.smn_servicio_id=smn_compras.smn_servicio.smn_servicio_id and
		smn_compras.smn_servicio.smn_servicio_id=${fld:id}  and
	smn_compras.smn_lineas.smn_lineas_id=smn_compras.smn_rel_linea_servicio.smn_lineas_id
	
