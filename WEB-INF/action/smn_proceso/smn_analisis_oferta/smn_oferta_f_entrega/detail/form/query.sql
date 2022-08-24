select
	smn_compras.smn_oferta_f_entrega.smn_oferta_f_entrega_id,
	case
	when smn_compras.smn_oferta_f_entrega.ofe_status='RE' then '${lbl:b_register}'
	when smn_compras.smn_oferta_f_entrega.ofe_status='GE' then '${lbl:b_generated}'
	end as ofe_status,
	smn_compras.smn_oferta.smn_oferta_id,
	smn_compras.smn_oferta_f_entrega.ofe_consecutivo,
	smn_compras.smn_oferta_f_entrega.ofe_cantidad,
	smn_compras.smn_oferta_f_entrega.ofe_fecha_entrega
	
from
	smn_compras.smn_oferta_f_entrega
	inner join smn_compras.smn_oferta on smn_compras.smn_oferta.smn_oferta_id = smn_compras.smn_oferta_f_entrega.smn_oferta_id

 where 
		smn_compras.smn_oferta_f_entrega.smn_oferta_f_entrega_id=${fld:id}
