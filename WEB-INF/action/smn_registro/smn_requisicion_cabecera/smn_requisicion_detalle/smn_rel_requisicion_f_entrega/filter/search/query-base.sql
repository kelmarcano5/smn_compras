select 
	smn_compras.smn_rel_requisicion_f_entrega.smn_rel_requisicion_f_entrega_id,
	smn_compras.smn_requisicion_detalle.smn_naturaleza_id as smn_naturaleza_id_pl0,
	smn_compras.smn_rel_requisicion_f_entrega.smn_requisicion_detalle_id,
	smn_compras.smn_rel_requisicion_f_entrega.cfe_consecutivo,
	smn_compras.smn_rel_requisicion_f_entrega.cfe_cantidad,
	smn_compras.smn_rel_requisicion_f_entrega.cfe_fecha_de_entrega,
	smn_compras.smn_rel_requisicion_f_entrega.cfe_fecha_registro
from
	smn_compras.smn_rel_requisicion_f_entrega
	inner join smn_compras.smn_requisicion_detalle on smn_compras.smn_requisicion_detalle.smn_requisicion_detalle_id = 	smn_compras.smn_rel_requisicion_f_entrega.smn_requisicion_detalle_id

where
	smn_rel_requisicion_f_entrega_id is not null
	${filter}
order by
		smn_rel_requisicion_f_entrega_id