-- select smn_base.smn_entidades.smn_entidades_id as id, smn_base.smn_entidades.ent_codigo || ' - ' || smn_base.smn_entidades.ent_descripcion_corta as item from smn_base.smn_entidades order by smn_base.smn_entidades.ent_descripcion_corta

select smn_compras.smn_proveedor.smn_proveedor_id as id, smn_base.smn_auxiliar.aux_codigo || ' - ' || smn_base.smn_auxiliar.aux_descripcion as item
from smn_compras.smn_proveedor 
	inner join smn_base.smn_auxiliar on smn_base.smn_auxiliar.smn_auxiliar_id = smn_compras.smn_proveedor.smn_auxiliar_rf