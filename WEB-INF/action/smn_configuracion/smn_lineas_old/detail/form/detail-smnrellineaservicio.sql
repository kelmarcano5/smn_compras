select	
	smn_compras.smn_servicio.smn_servicio_id, 
	smn_compras.smn_servicio.sco_codigo as sco_codigo_pl0,
	(select smn_compras.smn_servicio.sco_codigo|| ' - ' || smn_compras.smn_servicio.sco_nombre from  smn_compras.smn_servicio where smn_compras.smn_servicio.smn_servicio_id is not null  and smn_compras.smn_servicio.smn_servicio_id=smn_compras.smn_lineas.smn_lineas_id) as smn_servicio_id,
	smn_compras.smn_rel_linea_servicio.*
from
	smn_compras.smn_lineas ,
	smn_compras.smn_servicio,
	smn_compras.smn_rel_linea_servicio
where
	smn_compras.smn_rel_linea_servicio.smn_lineas_id=smn_compras.smn_lineas.smn_lineas_id and 
	smn_compras.smn_lineas.smn_lineas_id=${fld:id}  and 
	smn_compras.smn_servicio.smn_servicio_id=smn_compras.smn_rel_linea_servicio.smn_servicio_id 

