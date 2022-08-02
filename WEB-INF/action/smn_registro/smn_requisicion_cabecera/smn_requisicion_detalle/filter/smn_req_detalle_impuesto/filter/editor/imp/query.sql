select 
	smn_base.smn_codigos_impuestos.imp_monto_sustraendo as sustraendo, 
	smn_base.smn_codigos_impuestos.imp_porcentaje_base as base, 
	smn_base.smn_codigos_impuestos.imp_porcentaje_calculo as calculo 
from 
	smn_base.smn_codigos_impuestos
where smn_base.smn_codigos_impuestos.smn_codigos_impuestos_id=${fld:id}