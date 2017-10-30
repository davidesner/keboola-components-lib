package esnerda.keboola.components.result;

import java.io.File;

/**
 * @author David Esner
 */
public class ResultFileMetadata {

	private final File resFile;
	private final String[] idColums;
	private final String[] columns;
	private final String destination;
	private String delimiter;
	private String enclosure;
	private boolean incremental = true;


	public ResultFileMetadata(File resFile, String destination, String[] idColums, String[] columns) {
		super();
		this.resFile = resFile;
		this.idColums = idColums;
		this.columns = columns;
         this.destination = destination;
         //set default values for required params
         this.delimiter = ",";
         this.enclosure = "\"";
	}
	
	public ResultFileMetadata(File resFile, String destination, String[] idColums, String[] columns, boolean incremental) {
		super();
		this.resFile = resFile;
		this.idColums = idColums;
		this.columns = columns;
		this.destination = destination;
		this.incremental = incremental;
		//set default values for required params
		this.delimiter = ",";
		this.enclosure = "\"";
	}
	
	public ResultFileMetadata(File resFile, String destination, String[] idColums, String[] columns, String delimiter, String enclosure, boolean incremental) {
		super();
		this.resFile = resFile;
		this.idColums = idColums;
		this.columns = columns;
		this.destination = destination;
		this.incremental = incremental;
		//set default values for required params
		this.delimiter = delimiter;
		this.enclosure = enclosure;
	}

	public File getResFile() {
		return resFile;
	}

	public String[] getIdColums() {
		return idColums;
	}

	public String[] getColumns() {
		return columns;
	}

	public String getDestination() {
		return destination;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public String getEnclosure() {
		return enclosure;
	}

	public boolean isIncremental() {
		return incremental;
	}

	
}
