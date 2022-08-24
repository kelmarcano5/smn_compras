select
		smn_compras.smn_oferta_f_entrega.smn_oferta_f_entrega,
	smn_compras.smn_oferta_f_entrega.smn_oferta_id,
	smn_compras.smn_oferta_f_entrega.ofe_consecutivo,
	smn_compras.smn_oferta_f_entrega.ofe_cantidad,
	smn_compras.smn_oferta_f_entrega.ofe_fecha_entrega,
	smn_compras.smn_oferta_f_entrega.ofe_status
from
	smn_compras.smn_oferta_f_entrega 
where
	smn_compras.smn_oferta_f_entrega.smn_oferta_f_entrega_id = ${fld:id}
