select
	smn_compras.smn_rel_requisicion_f_entrega.*
from 
	smn_compras.smn_rel_requisicion_f_entrega,
	smn_compras.smn_requisicion_detalle
where
	smn_compras.smn_rel_requisicion_f_entrega.smn_requisicion_detalle_id=smn_compras.smn_requisicion_detalle.smn_requisicion_detalle_id and 
	smn_compras.smn_requisicion_detalle.smn_requisicion_detalle_id=${fld:id}
