package dinamica.upload;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import dinamica.GenericTransaction;
import dinamica.Recordset;
import dinamica.StringUtil;
import dinamica.xml.*;

/**
 * Dejar un blob grabado como archivo temporal y publicar un recordset con los datos
 * del blob para retornarlo al cliente (ruta del temporal, nombre original, tamaño y formato),
 * de tal manera que estos datos puedan ser luego posteados con el resto del formulario via Ajax. 
 * Asume que el campo que contiene al blob se llama "file".<br>
 * Debe activar el filtro y el mapping al Action en cuestión en web.xml para esto funcione.<br>
 * El validator.xml de un action que use esta clase se verá así:<br><br>
 * <xmp>
 * <?xml version='1.0' encoding='ISO-8859-1'?>
 * <validator>
 * 	<parameter id="file" type="varchar" required="false" label="Archivo temporal" maxlen="500"/>
 * 	<parameter id="file.content-type" type="varchar" required="false" label="Formato" maxlen="150"/>
 * 	<parameter id="file.filename" type="varchar" required="false" label="Archivo" maxlen="400"/>
 * 	<parameter id="image_size" type="integer" required="false" label="Tamaño del archivo"/>
 * </validator>
 * </xmp>
 * <br><br>
 * En el config.xml del Action que utiliza a esta clase tambien se soportan los siguientes elementos de 
 * uso opcional para limitar los formatos permitidos y el tamaño de los archivos:
 * <xmp>
 * 	<upload-maxsize errmsg="El archivo supera el tamaño máximo de 200K">512000</upload-maxsize>
 *	<upload-accept-format errmsg="Solo se aceptan formato JPEG">jpg</upload-accept-format>
 * </xmp>
 * <br>
 * @author mcordova
 *
 */
public class UploadTransaction extends GenericTransaction 
{

	public int service(Recordset inputParams) throws Throwable
	{
		String fechaActual= new SimpleDateFormat("yyyy-MM-dd").format(new Date());

		String sistemaOperativo = System.getProperty("os.name");
		String file;
		  
		if(sistemaOperativo.equals("Windows 7") || sistemaOperativo.equals("Windows 8") || sistemaOperativo.equals("Windows 10")) 
			file =  "C:/log/logDebugUploadTransaction_"+fechaActual+".txt";
		else
			file = "./logDebugUploadTransaction__"+fechaActual+".txt";
		
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
		
		str = "[linea-82] Ejecutando metodo service...";	
		bw.write(str);
		bw.flush();
		bw.newLine();
		bw.close();
		
		int rc = super.service(inputParams);
		
		//validar si la ruta representa un archivo
		if (inputParams.isNull("file.filename"))
			throw new Throwable("No se puede cargar el archivo, bien sea porque la ruta no es válida o porque la carga no está activada en la configuración de la aplicación (web.xml)");
		
		//patch 2010-09-02: get real content-type
		String mimeType = getContext().getMimeType(inputParams.getString("file.filename").toLowerCase());
		if (mimeType!=null)
			inputParams.setValue("file.content-type", mimeType);		
		
		//get temp file
		String path = (String)inputParams.getValue("file");
		File f = new File(path);
		
		//get file size
		Integer size = new Integer((int)f.length()); 
		inputParams.setValue("image_size", size);

		Element eFmts = getConfig().getDocument().getElement("upload-accept-format");
		if (eFmts!=null) {
			boolean isFormatOk = false;
			String errMsg = eFmts.getAttribute("errmsg");
			String fmtsAccepted = eFmts.getValue();
			String fmts[] = StringUtil.split(fmtsAccepted, "|");
			String fname = inputParams.getString("file.filename").toLowerCase();
			for (int i = 0; i < fmts.length; i++) {
				if (fname.endsWith(fmts[i].toLowerCase())) {
					isFormatOk = true;
					break;
				}
			}
			if (!isFormatOk) {
				f.delete();
				throw new Throwable(errMsg);
			}
		}
		
		if (size.intValue()==0)
			throw new Throwable("¡No se puede cargar un archivo vacío!");

		Element eSize = getConfig().getDocument().getElement("upload-maxsize");
		if (eSize!=null) {
			String errMsg = eSize.getAttribute("errmsg");
			Integer size2 = Integer.valueOf(eSize.getValue());
			if (size2.compareTo(size)<0) {
				f.delete();
				throw new Throwable(errMsg);
			}
		}
		
		return rc;
		
	}
	
}
