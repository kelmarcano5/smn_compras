select
	smn_base.smn_auxiliar.aux_codigo || '-' || smn_base.smn_auxiliar.aux_descripcion as smn_auxiliar_rf_combo,
	smn_base.smn_centro_costo.cco_codigo || '-' || smn_base.smn_centro_costo.cco_descripcion_corta as smn_centro_costo_rf_combo,
	smn_base.smn_estructura_organizacional.eor_codigo || '-' || smn_base.smn_estructura_organizacional.eor_nombre as smn_estructura_organizacional_rf_combo,
	smn_compras.smn_rel_auxiliar_ceco_estorg.rac_fecha_registro,
	smn_compras.smn_rel_auxiliar_ceco_estorg.smn_rel_auxiliar_ceco_estorg_id
	
from
	smn_compras.smn_rel_auxiliar_ceco_estorg
	INNER JOIN
	smn_base.smn_auxiliar on smn_base.smn_auxiliar.smn_auxiliar_id = smn_compras.smn_rel_auxiliar_ceco_estorg.smn_auxiliar_rf
	INNER JOIN
	smn_base.smn_centro_costo on smn_base.smn_centro_costo.smn_centro_costo_id = smn_compras.smn_rel_auxiliar_ceco_estorg.smn_centro_costo_rf
	INNER JOIN
	smn_base.smn_estructura_organizacional on smn_base.smn_estructura_organizacional.smn_estructura_organizacional_id = smn_compras.smn_rel_auxiliar_ceco_estorg.smn_estructura_organizacional_rf