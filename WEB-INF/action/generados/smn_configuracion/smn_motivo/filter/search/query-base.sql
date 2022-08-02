select
	case
	when smn_compras.smn_motivo.mtv_tipo='AJ' then '${lbl:b_setup}'
	when smn_compras.smn_motivo.mtv_tipo='DI' then '${lbl:b_desincorporate}'
	when smn_compras.smn_motivo.mtv_tipo='VA' then '${lbl:b_variac}'
	end as mtv_tipo_combo,
	smn_compras.smn_motivo.mtv_tipo,
	smn_compras.smn_motivo.mtv_codigo,
	smn_compras.smn_motivo.mtv_nombre,
	smn_compras.smn_motivo.smn_motivos_id
	
from
	smn_compras.smn_motivo
where
	smn_motivos_id is not null
	${filter}
order by
		smn_motivos_id
