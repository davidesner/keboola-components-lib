package esnerda.keboola.components.result;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;


/**
 * @author David Esner
 */
/**
 * Basic abstract implementation of result writer using Super CSV
 * 
 *
 * @param <T>
 */
public abstract class AbstractBeanResultWriter<T> implements IResultWriter<T>{
	
	protected File resFile;
	protected ICsvBeanWriter writer = null;
	protected  CellProcessor[] cellProcessors;
	protected String[] header;

	@Override
	public void initWriter(String path, Class<T> clazz) throws Exception {
		initHeader(clazz.newInstance());
		resFile = new File(path + File.separator + getFileName());
		this.writer = new CsvBeanWriter(new BufferedWriter(new FileWriter(resFile)), CsvPreference.STANDARD_PREFERENCE);			
		cellProcessors = getProcessors(getHeader().length);
		writer.writeHeader(getHeader());		
	}

	@Override
	public void writeResult(T obj) throws Exception {
		writer.write(obj, getHeader(), cellProcessors);		
	}

	@Override
	public void writeAllResults(List<T> objs) throws Exception {
		for (T o : objs){
			writeResult(o);
		}		
	}

	@Override
	public List<ResultFileMetadata> writeAndRetrieveResuts(List<T> objs) throws Exception {
		writeAllResults(objs);		
		return closeAndRetrieveMetadata();
	}

	/* get cell processors with dynamic size */
	protected CellProcessor[] getProcessors(int length) {
		CellProcessor[] processors = new CellProcessor[length];
		for (int i = 0; i < length; i++) {
			processors[i] = new Optional();
		}
		return processors;
	}

	protected void initHeader(T type) {
		List<String> fieldList = new ArrayList<String>();
		for (Field field : type.getClass().getDeclaredFields()) {
			fieldList.add(field.getName());
		}
		this.header = fieldList.toArray(new String[0]);
	}

	protected String[] getHeader() throws Exception {
		if (header != null) {
			return header;
		}
		throw new Exception("Writer is not properly initialized!");
	}

	public abstract String getFileName();


}
