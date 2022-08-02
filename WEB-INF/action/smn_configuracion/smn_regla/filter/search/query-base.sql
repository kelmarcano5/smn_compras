select
	smn_compras.smn_regla.rul_codigo,
	smn_compras.smn_regla.rul_nombre,
	smn_compras.smn_regla.rul_cantidad_desde,
	smn_compras.smn_regla.rul_cantidad_hasta,
	smn_compras.smn_regla.rul_monto_desde,
	smn_compras.smn_regla.rul_monto_hasta,
	smn_compras.smn_regla.rul_fecha_registro,
		smn_compras.smn_regla.smn_regla_id
	
from
	smn_compras.smn_regla
where
	smn_regla_id is not null
	${filter}
order by
		smn_regla_id
