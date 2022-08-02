select
		smn_compras.smn_regla.rul_codigo,
	smn_compras.smn_regla.rul_nombre,
	smn_compras.smn_regla.rul_cantidad_desde,
	smn_compras.smn_regla.rul_cantidad_hasta,
	smn_compras.smn_regla.rul_var_cantidad,
	smn_compras.smn_regla.smn_monedas_id,
	smn_compras.smn_regla.rul_monto_desde,
	smn_compras.smn_regla.rul_monto_hasta,
	smn_compras.smn_regla.rul_var_monto,
	smn_compras.smn_regla.rul_fecha_registro
from
	smn_compras.smn_regla 
where
	smn_compras.smn_regla.smn_regla_id = ${fld:id}
