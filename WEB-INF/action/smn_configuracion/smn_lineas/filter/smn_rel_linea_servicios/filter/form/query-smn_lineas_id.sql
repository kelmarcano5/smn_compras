select smn_compras.smn_lineas.smn_lineas_id as id, 
smn_compras.smn_lineas.lin_codigo|| ' - ' || smn_compras.smn_lineas.lin_nombre as item from smn_compras.smn_lineas