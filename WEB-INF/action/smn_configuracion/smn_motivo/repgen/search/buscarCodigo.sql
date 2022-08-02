select
		smn_compras.smn_motivo.mtv_codigo
from
		smn_compras.smn_motivo
where
		upper(smn_compras.smn_motivo.mtv_codigo) = upper(${fld:mtv_codigo})
