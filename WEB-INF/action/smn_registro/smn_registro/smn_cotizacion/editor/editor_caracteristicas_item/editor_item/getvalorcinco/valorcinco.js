listboxClear("smn_valor_cinco");

var optionChoose = document.createElement("option");
optionChoose.text = '[${lbl:b_choose}]';
optionChoose.value = "0";
document.form1.smn_valor_cinco.add(optionChoose, 0);

<smn_valor_cinco_rows>
	var option = document.createElement("option");
	option.text = "${fld:item@js}"; 
	option.value = "${fld:id}";
	document.form1.smn_valor_cinco.add(option, 0);
</smn_valor_cinco_rows>

