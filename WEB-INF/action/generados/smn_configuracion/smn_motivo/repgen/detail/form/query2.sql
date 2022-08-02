select
		smn_compras.smn_motivo.smn_motivos_id,
	smn_compras.smn_motivo.mtv_tipo,
	smn_compras.smn_motivo.mtv_codigo,
	smn_compras.smn_motivo.mtv_nombre,
	smn_compras.smn_motivo.mtv_fecha_registro
from
	smn_compras.smn_motivo 
where
	smn_compras.smn_motivo.smn_motivo_id = ${fld:id}
