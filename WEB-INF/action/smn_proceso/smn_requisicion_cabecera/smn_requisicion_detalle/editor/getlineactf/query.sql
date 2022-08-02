select DISTINCT  smn_base.smn_activo_fijo.smn_afijo_id as id, smn_base.smn_activo_fijo.acf_codigo|| ' - ' ||smn_base.smn_activo_fijo.acf_nombre as item from smn_base.smn_activo_fijo
inner join smn_compras.smn_rel_linea_afijo on smn_compras.smn_rel_linea_afijo.smn_afijo_id = smn_base.smn_activo_fijo.smn_afijo_id
where 
smn_compras.smn_rel_linea_afijo.smn_lineas_id=${fld:id}