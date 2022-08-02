var modalidad = '${fld:dcc_modalidad}';

if(modalidad == 'MA')
{
	listboxClear("smn_item_id");

	var optionChoose = document.createElement("option");
	optionChoose.text = '[${lbl:b_any}]';
	optionChoose.value = "0";
	document.form1.smn_item_id.add(optionChoose, 0);

	<smn_item_id_rows>
		var option = document.createElement("option");
		option.text = "${fld:item@js}"; 
		option.value = "${fld:id}";
		document.form1.smn_item_id.add(option, 0);
	</smn_item_id_rows>
}
else
{
	listboxClear("smn_item_id");

	var optionChoose = document.createElement("option");
	optionChoose.text = '[${lbl:b_any}]';
	optionChoose.value = "0";
	document.form1.smn_item_id.add(optionChoose, 0);

	<query2_rows>
		var option = document.createElement("option");
		option.text = "${fld:item@js}"; 
		option.value = "${fld:id}";
		document.form1.smn_item_id.add(option, 0);
	</query2_rows>
}
