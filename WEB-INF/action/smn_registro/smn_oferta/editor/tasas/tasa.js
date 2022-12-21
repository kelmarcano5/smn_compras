listboxClear("ofe_tasa");

var optionChoose = document.createElement("option");
optionChoose.text = '[${lbl:b_choose}]';
optionChoose.value = "0";
document.form1.ofe_tasa.add(optionChoose, 0);

<smn_tasa_id_rows>
	var option = document.createElement("option");
	var valor_tasa = "${fld:item}";
	
	option.text = "${fld:fecha_item}"+"->"+valor_tasa;  
	option.value = "${fld:id}";
	document.form1.ofe_tasa.add(option, 0);
</smn_tasa_id_rows>
