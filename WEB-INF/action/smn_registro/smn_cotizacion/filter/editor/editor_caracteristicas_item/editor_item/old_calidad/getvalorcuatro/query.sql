select smn_base.smn_atributo_valor.smn_atributo_valor_id as id,smn_base.smn_atributo_valor.atv_codigo ||' - '|| smn_base.smn_atributo_valor.atv_descripcion as item from smn_base.smn_atributo_valor
left outer join smn_base.smn_atributo_item on smn_base.smn_atributo_item.smn_atributo_item_id = smn_base.smn_atributo_valor.smn_atributo_item_id
left outer join smn_base.smn_rel_nivel_estruc_atributo on smn_base.smn_rel_nivel_estruc_atributo.smn_atributo_item_id = smn_base.smn_atributo_item.smn_atributo_item_id
where smn_base.smn_atributo_valor.smn_atributo_item_id=${fld:id} and smn_base.smn_rel_nivel_estruc_atributo.rna_secuencia=4 group BY id