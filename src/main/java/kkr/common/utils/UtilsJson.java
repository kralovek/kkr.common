package kkr.common.utils;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kkr.common.errors.BaseException;
import kkr.common.errors.TechnicalException;

public class UtilsJson {
	private static final Logger LOG = Logger.getLogger(UtilsJson.class);

	public static String toJSON(Object object) throws BaseException {
		LOG.trace("BEGIN");
		try {
			ObjectMapper mapper = new ObjectMapper();
			try {
				String retval = mapper.writeValueAsString(object);
				LOG.trace("OK");
				return retval;
			} catch (JsonProcessingException ex) {
				throw new TechnicalException("Cannot convert the object to JSON string", ex);
			}
		} finally {
			LOG.trace("END");
		}
	}

}
