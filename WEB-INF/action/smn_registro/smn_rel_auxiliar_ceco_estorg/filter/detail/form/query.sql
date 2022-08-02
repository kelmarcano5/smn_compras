select
	(select aux_codigo || ' - ' || aux_descripcion from  smn_base.smn_auxiliar where smn_auxiliar_id is not null  and smn_auxiliar_id=smn_compras.smn_rel_auxiliar_ceco_estorg.smn_auxiliar_rf) as smn_auxiliar_rf_combo,
	(select cco_codigo || ' - ' || cco_descripcion_corta from  smn_base.smn_centro_costo where smn_centro_costo_id is not null  and smn_centro_costo_id=smn_compras.smn_rel_auxiliar_ceco_estorg.smn_centro_costo_rf) as smn_centro_costo_rf_combo,
	(select eor_codigo || ' - ' || eor_nombre from  smn_base.smn_estructura_organizacional where smn_estructura_organizacional_id is not null  and smn_estructura_organizacional_id=smn_compras.smn_rel_auxiliar_ceco_estorg.smn_estructura_organizacional_rf) as smn_estructura_organizacional_rf_combo,
	smn_compras.smn_rel_auxiliar_ceco_estorg.*
from 
	smn_compras.smn_rel_auxiliar_ceco_estorg
where
	smn_rel_auxiliar_ceco_estorg_id = ${fld:id}
