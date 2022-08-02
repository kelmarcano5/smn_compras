select
		smn_compras.smn_motivo.mtv_nombre
from
		smn_compras.smn_motivo
where
		upper(smn_compras.smn_motivo.mtv_nombre) = upper(${fld:mtv_nombre})
