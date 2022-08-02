package dinamica;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;
import dinamica.xml.Document;
import dinamica.xml.Element;
import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.LabelCell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * Clase generica que permite cargar los datos de un archivo Excel (XLS)
 * mediante un archivo XML (excel.xml) el cual contiene los parametros o campos que 
 * tiene el Excel, en dicho XML se define cuales campos son obligatorios
 * y el tipo de datos de cada uno. El archivo XML guarda similitud a los 
 * archivos validator.xml que maneja el framework, el mismo permite inclusive
 * definir los custom-validator<br>
 * Ejemplo:<br>
 * <xmp>
 * <?xml version='1.0' encoding='ISO-8859-1'?>
 * <validator maxErrors='20'>
 *		<parameter id='alias' type='varchar' required='true' maxlen='20'/>
 *		<parameter id='fname' type='varchar' required='true' maxlen='30'/>
 *		<parameter id='lname' type='varchar' required='true' maxlen='30'/>
 *		<parameter id='email' type='varchar' required='true' maxlen='100' regexp='^([\w-]+\.)*?[\w-]+@[\w-]+\.([\w-]+\.)*?[\w]+$' regexp-error-label='Formato incorrecto xxxx@xxxxxx.xxx'/>
 *		<parameter id='company' type='varchar' required='false' maxlen='80'/>
 *		<parameter id='phone' type='varchar' required='false' maxlen='20'/>
 *		<parameter id='cellphone' type='varchar' required='false' maxlen='20'/>
 *	
 *	<custom-validator 
 *		classname="dinamica.validators.DuplicatedKeyValidator" 
 *		on-error-label="Este valor ya se encuentra registrado en la base de datos." 
 *		sql="duplicated.sql" id="alias"/>
 *		
 * </validator>
 * </xmp>
 * <br>
 * La data se graba ejecutando un batch con prepared statement y el orden de los
 * campos en el SQL (excel.sql) deben estar en el mismo orden en que se encuentran
 * definidos el archivo excel.xml 
 * <br><br>
 * Last update: 2013-12-05<br>
 * (c) 2004-2013 Martin Cordova<br>
 * This code is released under the LGPL license<br>
 * @author Martin Cordova (martin.cordova@gmail.com)
 * */
public class ImportExcel extends GenericTableManager {
	
	int rc = 0;
	ArrayList<String> parameter = null;
	
	@Override
	public int service(Recordset inputParams) throws Throwable {
		
		String fechaActual= new SimpleDateFormat("yyyy-MM-dd").format(new Date());

		String sistemaOperativo = System.getProperty("os.name");
		String file;
		  
		if(sistemaOperativo.equals("Windows 7") || sistemaOperativo.equals("Windows 8") || sistemaOperativo.equals("Windows 10")) 
			file =  "C:/log/logDebugImportExcel_"+fechaActual+".txt";
		else
			file = "./logDebugImportExcel_"+fechaActual+".txt";
		
		File newLogFile = new File(file);
		FileWriter fw;
		String str="";
		
		if(!newLogFile.exists())
			fw = new FileWriter(newLogFile);
		else
			fw = new FileWriter(newLogFile,true);
		
		BufferedWriter bw=new BufferedWriter(fw);
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		str = "----------"+timestamp+"----------";	
		bw.write(str);
		bw.flush();
		bw.newLine();
		bw.newLine();
		
		str = "[linea-109] Ejecutando metodo validateFile...";	
		bw.write(str);
		bw.flush();
		bw.newLine();
		bw.close();
		rc = 0;
		
		Recordset rs = validateFile(inputParams);
		
		if(rc == 0) {
		
			getDb().beginTrans();
			
			super.service(inputParams);
			
			save(rs, inputParams);
		}
		
		return rc;
	}
	
	/**
	 * Retorna el InputStream dado el objeto File del archivo Excel,
	 * Este metodo puede ser reescrito para realizar validacion del archivo
	 * Excel cuando el mismo es de tipo XLSX
	 * @param file Archivo Excel
	 * @return InputStream
	 * @throws Throwable
	 */
	public InputStream getInputStream(File file) throws Throwable {
		
		InputStream inp = new FileInputStream(file); 
		return inp;
		
	}
	
	/**
	 * Valida cada row del archivo Excel segun la configuracion
	 * definida en el archivo excel.xml
	 * @param inputParams Recordset del request
	 * @return Recordset con los datos del archivo Excel
	 * @throws Throwable
	 */
	public Recordset validateFile(Recordset inputParams) throws Throwable {
		
		//recordset para el manejo de los errores
		Recordset rsError = new Recordset();
		rsError.append("columna", java.sql.Types.VARCHAR);
		rsError.append("fila", java.sql.Types.INTEGER);
		rsError.append("error", java.sql.Types.VARCHAR);
		
		/* recordset to hold request parameters and optional parameters defined in validator.xml */
		Recordset inputs = new Recordset();
		
		/* load validator xml file */
		String file = "excel.xml";
		String xmlFile = getResource(file);

		Document xml = new Document( xmlFile );	
		Element root = xml.getRoot();	
		ArrayList<Element> customVals = new ArrayList<Element>();
		ArrayList<Element> params = new ArrayList<Element>();

		/* cantidad de errores que seran mostrados si es cero se asumen todos */
		String maxErrors = root.getAttribute("maxErrors");
		if (maxErrors==null)
			maxErrors = "0";
		
		String type = null;
		String id = null;
		int sqlType = 0;
		String required = null;
		
		parameter = new ArrayList<String>();
		
		Element[] xx = xml.getElements(root);
		for (int i = 0; i < xx.length; i++) {
			Element e = xx[i];
			if (e.getNodeName().equals("parameter")) {
				params.add(e);
				id = e.getAttribute( "id" );
				if (id==null)
					throw new Exception ("Invalid Validator. Attribute [id] not found: " + file);
				type = e.getAttribute( "type" );
				if (type==null)
					throw new Exception ("Invalid Validator. Attribute [type] not found: " + file);
				required = e.getAttribute( "required" );
				if (required==null)
					throw new Exception ("Invalid Validator. Attribute [required] not found: " + file);

				if (type.equals("varchar"))	
					sqlType = Types.VARCHAR;
				else if (type.equals("integer"))
					sqlType = Types.INTEGER;
				else if (type.equals("double"))
					sqlType = Types.DOUBLE;
				else if (type.equals("date"))
					sqlType = Types.DATE;
				else
					throw new Exception("Invalid validator data type (" + type + ") in file: " + file);
				
				inputs.append(id, sqlType);
				parameter.add(id);
				
			} else if (e.getNodeName().equals("custom-validator")) 
				customVals.add(e);
			
		}		

		//obtener el archivo Excel
		File f = new File(inputParams.getString("_tempfile"));
		InputStream is = getInputStream(f);
		Workbook wb = Workbook.getWorkbook(is);
		
		//obtener la hoja de calculo del archivo
		Sheet sheet = wb.getSheet(0);
		
		int numOfRows = sheet.getRows();
		for(int i = 1; i<numOfRows;i++)
		{
			//verificar la cantidad de errores a mostrar
			if(Integer.valueOf(maxErrors)!=0) {
				if(rsError.getRecordCount()>=Integer.valueOf(maxErrors))
					break;
			}
			
			/* add one record to hold the parameters values */
			inputs.addNew();
			
			/* read parameters pass 2 - validate parameters */
			for (int j = 0; j < params.size(); j++) 
			{

				Element p = params.get(j);
				int maxLen = 0;
				String maxLenAttr = "";
				String minValueAttr = "";
				String maxValueAttr = "";
				String regexp = null;
				String regexpError = null;
				String errorLabel = null;

				id = p.getAttribute( "id" );
				type = p.getAttribute( "type" );
				required = p.getAttribute( "required" );
				maxLenAttr = p.getAttribute( "maxlen" );
				minValueAttr = p.getAttribute( "min" );
				maxValueAttr = p.getAttribute( "max" );
				regexp = p.getAttribute( "regexp" );
				regexpError = p.getAttribute( "regexp-error-label" );
				errorLabel = p.getAttribute( "on-error-label" );
							
				if (maxLenAttr!=null)
					maxLen = Integer.parseInt(maxLenAttr);
				
				/* validate type attribute */
				if (type.equals("varchar"))	
					sqlType = Types.VARCHAR;
				else if (type.equals("integer"))
					sqlType = Types.INTEGER;
				else if (type.equals("double"))
					sqlType = Types.DOUBLE;
				else if (type.equals("date"))
					sqlType = Types.DATE;
				
				Cell cellValue = sheet.getCell(j,i);
				
				if(cellValue.getType() != CellType.EMPTY && (cellValue.getContents() != null && !cellValue.getContents().trim().equals(""))) {
					
					/* check maxlen rule */
					if (maxLen>0)
					{
						if (cellValue.getContents().length()>maxLen) {
							String desc = "${lbl:data_too_long}";
							TemplateEngine te = new TemplateEngine(getContext(), getRequest(), desc);
							te.replaceLabels();
							desc = te.toString();
							
							rsError.addNew();
							rsError.setValue("columna", (j+1));
							rsError.setValue("fila", (i+1));
							rsError.setValue("error", desc);
						}
					}

					/* check regular expression */
					if (sqlType==Types.VARCHAR && regexp!=null)
					{
						boolean isMatch = Pattern.matches(regexp, cellValue.getContents());
						if (!isMatch) {
							TemplateEngine te = new TemplateEngine(getContext(), getRequest(), regexpError);
							te.replaceLabels();
							regexpError = te.toString();
							
							rsError.addNew();
							rsError.setValue("columna", (j+1));
							rsError.setValue("fila", (i+1));
							rsError.setValue("error", regexpError);
						}
					}
					
					/* convert to datatype if valid */
					switch (sqlType)
					{
						case Types.DATE:
							if (cellValue.getType() == CellType.DATE || cellValue.getType() == CellType.DATE_FORMULA) 
							{ 
								DateCell dc = (DateCell) cellValue; 
								Date val = convertDate4JXL(dc.getDate());
								inputs.setValue(id, val);
							}
							else 
							{
								String desc = "${lbl:invalid_date}";
								if(errorLabel!=null)
									desc = errorLabel;
								TemplateEngine te = new TemplateEngine(getContext(), getRequest(), desc);
								te.replaceLabels();
								desc = te.toString();
								
								rsError.addNew();
								rsError.setValue("columna", (j+1));
								rsError.setValue("fila", (i+1));
								rsError.setValue("error", desc);
							}
							break;
							
						case Types.DOUBLE:
							if (cellValue.getType() == CellType.NUMBER || cellValue.getType() == CellType.NUMBER_FORMULA) 
							{ 
								NumberCell nc = (NumberCell) cellValue;
								double val = nc.getValue();
								inputs.setValue(id, val);
								
								if (minValueAttr!=null) {
									double minValue = Double.parseDouble(minValueAttr);
									if (val < minValue) {
										
										String desc = "${lbl:min_value_violation}";
										TemplateEngine te = new TemplateEngine(getContext(), getRequest(), desc);
										te.replaceLabels();
										desc = te.toString();
										
										rsError.addNew();
										rsError.setValue("columna", (j+1));
										rsError.setValue("fila", (i+1));
										rsError.setValue("error", desc);

									}
								}
								
								if (maxValueAttr!=null) {
									double maxValue = Double.parseDouble(maxValueAttr);
									if (val > maxValue) {
										
										String desc = "${lbl:max_value_violation}";
										TemplateEngine te = new TemplateEngine(getContext(), getRequest(), desc);
										te.replaceLabels();
										desc = te.toString();
										
										rsError.addNew();
										rsError.setValue("columna", (j+1));
										rsError.setValue("fila", (i+1));
										rsError.setValue("error", desc);
										
									}
								}
								
							}
							else 
							{
								String desc = "${lbl:invalid_double}";
								if(errorLabel!=null)
									desc = errorLabel;
								TemplateEngine te = new TemplateEngine(getContext(), getRequest(), desc);
								te.replaceLabels();
								desc = te.toString();
								
								rsError.addNew();
								rsError.setValue("columna", (j+1));
								rsError.setValue("fila", (i+1));
								rsError.setValue("error", desc);
							}
							break;
						
						case Types.INTEGER:
							if (cellValue.getType() == CellType.NUMBER || cellValue.getType() == CellType.NUMBER_FORMULA) 
							{ 
								NumberCell nc = (NumberCell) cellValue;
								double x = nc.getValue();
								String x2 = dinamica.StringUtil.formatNumber(x, "############");
								Integer val = ValidatorUtil.testInteger(x2);
								if (val==null) {
									String desc = "${lbl:invalid_integer}";
									if(errorLabel!=null)
										desc = errorLabel;
									TemplateEngine te = new TemplateEngine(getContext(), getRequest(), desc);
									te.replaceLabels();
									desc = te.toString();
									
									rsError.addNew();
									rsError.setValue("columna", (j+1));
									rsError.setValue("fila", (i+1));
									rsError.setValue("error", desc);
								} else
									inputs.setValue(id, val);
								
								if (minValueAttr!=null && val!=null) {
									int	minValue = Integer.parseInt(minValueAttr);
									if (val.intValue() < minValue) {
										
										String desc = "${lbl:min_value_violation}";
										TemplateEngine te = new TemplateEngine(getContext(), getRequest(), desc);
										te.replaceLabels();
										desc = te.toString();
										
										rsError.addNew();
										rsError.setValue("columna", (j+1));
										rsError.setValue("fila", (i+1));
										rsError.setValue("error", desc);
										
									}
								}
								if (maxValueAttr!=null && val!=null) {
									int maxValue = Integer.parseInt(maxValueAttr);
									if (val.intValue() > maxValue) {
										
										String desc = "${lbl:max_value_violation}";
										TemplateEngine te = new TemplateEngine(getContext(), getRequest(), desc);
										te.replaceLabels();
										desc = te.toString();
										
										rsError.addNew();
										rsError.setValue("columna", (j+1));
										rsError.setValue("fila", (i+1));
										rsError.setValue("error", desc);
										
									}
								}
								
							}
							else
							{
								String desc = "${lbl:invalid_integer}";
								if(errorLabel!=null)
									desc = errorLabel;
								TemplateEngine te = new TemplateEngine(getContext(), getRequest(), desc);
								te.replaceLabels();
								desc = te.toString();
								
								rsError.addNew();
								rsError.setValue("columna", (j+1));
								rsError.setValue("fila", (i+1));
								rsError.setValue("error", desc);
							}
							break;
						
						case Types.VARCHAR:
							
							if (cellValue.getType() == CellType.LABEL || cellValue.getType() == CellType.STRING_FORMULA) 
							{ 
								LabelCell lc = (LabelCell) cellValue; 
								String val = lc.getString();
								inputs.setValue(id, val);
								
							} else 
								inputs.setValue(id, cellValue.getContents());
							
							break;

					}
				} 
				else if (required.equals("true"))
				{
					String desc = "${lbl:parameter_required}";
					if(errorLabel!=null)
						desc = errorLabel;
					TemplateEngine te = new TemplateEngine(getContext(), getRequest(), desc);
					te.replaceLabels();
					desc = te.toString();
					
					rsError.addNew();
					rsError.setValue("columna", (j+1));
					rsError.setValue("fila", (i+1));
					rsError.setValue("error", desc);
				}
				
				/* 
				 * now check if there are any custom validators defined
				 * this is only necessary if there are no previous errors
				 * because custom-validator require valid request parameters 
				 */
				if (rsError.getRecordCount()==0)
				{
					for (int i1 = 0; i1 < customVals.size(); i1++) 
					{
					
						/* read validator configuration */
						HashMap<String, String> a = new HashMap<String, String>();
						Element validator = customVals.get(i1);
						String className = validator.getAttribute( "classname" );
						String onErrorLabel = validator.getAttribute( "on-error-label" );
						String idCustom = validator.getAttribute( "id" );
						
						if(idCustom!=null && idCustom.equals(id)) {
						
							/* custom attributes */
							a = validator.getAttributes();
						
							/* load class and instantiate object */
							AbstractValidator t = (AbstractValidator) getObject(className);
						
							/* call isValid method */
							boolean b = t.isValid(getRequest(), inputs, a);
						
							/* on error add error message to list of errors */
							if (!b)
							{
								String err = t.getErrorMessage();
								if (err==null)
									err = onErrorLabel;
								
								TemplateEngine te = new TemplateEngine(getContext(), getRequest(), err);
								te.replaceLabels();
								err = te.toString();
								
								rsError.addNew();
								rsError.setValue("columna", (j+1));
								rsError.setValue("fila", (i+1));
								rsError.setValue("error", err);
							}
							
						}
					}
				}
			}
			
			validateRow(inputs, rsError);
			
		}
		
		if(rsError.getRecordCount()>0) {
			getSession().setAttribute("error.excel", rsError);
			rc = 1;
		}
		
		//cierra y elimina el archivo temporal que contiene a la hoja excel
		wb.close();
		is.close();
		f.delete();
		
		return inputs;
	}
	
	/**
	 * Permite realiza validaciones extras para un row del
	 * archivo Excel
	 * @param rs Recordset que contiene el row del archivo Excel
	 * @param rsError Recordset que contiene los errores
	 * @throws Throwable
	 */
	public void validateRow(Recordset rs, Recordset rsError) throws Throwable {
	}
	
	/**
	 * Graba la data del archivo Excel en la base de datos
	 * @param rs Recordset que contiene la data
	 * @param inputParams Recordset del request
	 * @throws Throwable
	 */
	public void save(Recordset rs, Recordset inputParams) throws Throwable {
		
		String[] params = getParamsNames();
			
		String sql = getSQL(getResource("excel.sql"), inputParams);
		getDb().execBatch(sql, rs, params);
		
	}
	
	/**
	 * Retorna array de parametros de los campos que seran usados
	 * en el prepared statement
	 * @return Array de parametros
	 * @throws Throwable
	 */
	public String[] getParamsNames() throws Throwable {
		
		String[] params = new String[parameter.size()];
		int i = 0;
		for (Iterator<String> iterator = parameter.iterator(); iterator.hasNext();) {
			String value = (String) iterator.next();
			params[i] = value;
			i++;
		}
		
		return params;
	}
	
	/**
	 * Realiza una conversion de la fecha obtenida en el archivo Excel
	 * a un formato correcto en Java
	 * @param jxlDate Fecha del archivo Exceñ
	 * @return Fecha correcta en Java
	 * @throws Throwable
	 */
	public static java.util.Date convertDate4JXL (java.util.Date jxlDate) throws Throwable {

		if (jxlDate == null)
			return null;
		TimeZone gmt = TimeZone.getTimeZone ("GMT");
		DateFormat dateFormat = new	SimpleDateFormat ("yyyy-MM-dd HH:mm:ss ", Locale.getDefault ());
		dateFormat.setTimeZone (gmt);
		String str = dateFormat.format (jxlDate);
		TimeZone local = TimeZone.getDefault ();
		dateFormat.setTimeZone (local);

		return dateFormat.parse (str);
	}
}
