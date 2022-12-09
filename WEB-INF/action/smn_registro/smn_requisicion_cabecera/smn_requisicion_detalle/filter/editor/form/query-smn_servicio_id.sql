select smn_compras.smn_servicio.smn_servicio_id as id, smn_compras.smn_servicio.sco_codigo|| ' - ' || smn_compras.smn_servicio.sco_nombre as item from smn_compras.smn_servicio
inner join smn_compras.smn_rel_linea_servicio on smn_compras.smn_rel_linea_servicio.smn_servicio_id =  smn_compras.smn_servicio.smn_servicio_id
