select
select
	case
	when smn_compras.smn_oferta_f_entrega.ofe_status='RE' then '${lbl:b_register}'
	when smn_compras.smn_oferta_f_entrega.ofe_status='GE' then '${lbl:b_generated}'
	end as ofe_status,
	smn_compras.smn_oferta_f_entrega.*
from
	smn_compras.smn_oferta_f_entrega
where
	smn_oferta_f_entrega_id = ${fld:id}
