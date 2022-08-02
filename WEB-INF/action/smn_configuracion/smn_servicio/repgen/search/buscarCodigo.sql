select
		smn_compras.smn_servicio.sco_codigo
from
		smn_compras.smn_servicio
where
		upper(smn_compras.smn_servicio.sco_codigo) = upper(${fld:sco_codigo})
