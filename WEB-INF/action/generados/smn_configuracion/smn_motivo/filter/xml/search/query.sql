select
		smn_compras.smn_motivo.smn_motivo_id,
	case
	when smn_compras.smn_motivo.mtv_tipo='AJ' then '${lbl:b_setup}'
	when smn_compras.smn_motivo.mtv_tipo='DI' then '${lbl:b_desincorporate}'
	when smn_compras.smn_motivo.mtv_tipo='VA' then '${lbl:b_variac}'
	end as mtv_tipo_combo,
	smn_compras.smn_motivo.mtv_tipo,
	smn_compras.smn_motivo.mtv_codigo,
	smn_compras.smn_motivo.mtv_nombre
	
from
	smn_compras.smn_motivo
