select
		smn_compras.smn_servicio.sco_nombre
from
		smn_compras.smn_servicio
where
		upper(smn_compras.smn_servicio.sco_nombre) = upper(${fld:sco_nombre})
