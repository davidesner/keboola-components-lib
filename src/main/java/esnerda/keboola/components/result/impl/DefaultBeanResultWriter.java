package esnerda.keboola.components.result.impl;

import java.util.Collections;
import java.util.List;

import esnerda.keboola.components.result.AbstractBeanResultWriter;
import esnerda.keboola.components.result.ResultFileMetadata;

/**
 * @author David Esner
 */
public class DefaultBeanResultWriter<T> extends AbstractBeanResultWriter<T> {

	protected static final String DEFAULT_DELIMITER = ",";
	protected static final String DEFAULT_ENCLOSURE = "\"";
	private final String fileName;
	private final String[] idCols;
	private final String delimiter;
	private final String enclosure;
	private final String destination;
	private final boolean incremental;

	public DefaultBeanResultWriter(String resFileName, String[] idCols) {
		this.fileName = resFileName;
		this.idCols = idCols;
		this.delimiter = DEFAULT_DELIMITER;
		this.enclosure = DEFAULT_ENCLOSURE;
		this.destination = null;
		this.incremental = true;
	}

	public DefaultBeanResultWriter(String resFileName, String[] idCols, String destination) {
		this.fileName = resFileName;
		this.idCols = idCols;
		this.delimiter = ",";
		this.enclosure = "\"";
		this.destination = destination;
		this.incremental = true;
	}

	public DefaultBeanResultWriter(String resFileName, String[] idCols, String destination, String delimiter,
			String enclosure, boolean incremental) {
		this.fileName = resFileName;
		this.idCols = idCols;
		this.delimiter = ",";
		this.enclosure = "\"";
		this.destination = destination;
		this.incremental = incremental;
	}

	@Override
	public List<ResultFileMetadata> closeAndRetrieveMetadata() throws Exception {
		close();
		return Collections.singletonList(
				new ResultFileMetadata(resFile, destination, idCols, header, delimiter, enclosure, true));
	}

	@Override
	public String getFileName() {
		return fileName;
	}

}
