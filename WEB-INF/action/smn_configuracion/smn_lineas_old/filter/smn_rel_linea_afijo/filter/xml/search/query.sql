select
	smn_compras.smn_rel_linea_afijo.smn_rel_linea_afijo_id,
  smn_compras.smn_lineas.lin_codigo|| ' - ' || smn_compras.smn_lineas.lin_nombre as smn_lineas_id,
  smn_base.smn_activo_fijo.acf_codigo||'-'||smn_base.smn_activo_fijo.acf_nombre as smn_afijo_id
from
	smn_compras.smn_lineas,
	smn_base.smn_activo_fijo,
	smn_compras.smn_rel_linea_afijo
  where
	smn_compras.smn_lineas.smn_lineas_id=smn_compras.smn_rel_linea_afijo.smn_lineas_id