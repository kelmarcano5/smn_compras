select
	smn_requisicion_detalle_id	
from 
	smn_compras.smn_requisicion_detalle
where
	smn_requisicion_detalle_id = ${fld:smn_requisicion_detalle_id}
	