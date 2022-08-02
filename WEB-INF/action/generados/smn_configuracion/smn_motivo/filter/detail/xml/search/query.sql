select
	case
	when smn_compras.smn_motivo.mtv_tipo='AJ' then '${lbl:b_setup}'
	when smn_compras.smn_motivo.mtv_tipo='DI' then '${lbl:b_desincorporate}'
	when smn_compras.smn_motivo.mtv_tipo='VA' then '${lbl:b_variac}'
	end as mtv_tipo_combo,
	smn_compras.smn_motivo.*
from
	smn_compras.smn_motivo
where
	smn_motivos_id = ${fld:id}
