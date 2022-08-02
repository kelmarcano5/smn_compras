select
		smn_compras.smn_servicio.sco_codigo,
	smn_compras.smn_servicio.sco_nombre,
	smn_compras.smn_servicio.sco_maneja_contrato,
	smn_compras.smn_servicio.sco_proveedor_exclusivo,
	smn_compras.smn_servicio.smn_area_servicio_id,
	smn_compras.smn_servicio.smn_unidades_servicios_id,
	smn_compras.smn_servicio.sco_fecha_registro
from
	smn_compras.smn_servicio 
where
	smn_compras.smn_servicio.smn_servicio_id = ${fld:id}
