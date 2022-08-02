select 
	smn_base.smn_auxiliar.aux_codigo||'-'||smn_base.smn_auxiliar.aux_descripcion as smn_auxiliar_rf,
	smn_base.smn_clasificacion_abc.clf_codigo||'-'||smn_base.smn_clasificacion_abc.clf_descripcion as smn_clasificacion_abc_rf,
	smn_compras.smn_proveedor.prv_fecha_registro,
	smn_base.smn_condicion_financiera.cfi_description,
	smn_base.smn_condicion_pago.cop_descripcion
from 
	smn_compras.smn_proveedor
left outer join smn_base.smn_clasificacion_abc on 
smn_base.smn_clasificacion_abc.smn_clasificacion_abc_id= smn_compras.smn_proveedor.smn_clasificacion_abc_rf
left outer join smn_base.smn_auxiliar on 
smn_base.smn_auxiliar.smn_auxiliar_id = smn_compras.smn_proveedor.smn_auxiliar_rf
left outer join smn_base.smn_condicion_financiera ON smn_base.smn_auxiliar.aux_condicion_financiera_rf = smn_base.smn_condicion_financiera.smn_condicion_financiera_id
left outer join smn_base.smn_condicion_pago ON smn_base.smn_auxiliar.aux_cond_pago_rf = smn_base.smn_condicion_pago.smn_condicion_pago_id
where
	smn_compras.smn_proveedor.smn_proveedor_id = ${fld:id}
