package kkr.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;

public class UtilsException {

	public static String[][] getCauses(Throwable th) {
		Collection<String[]> causes = new ArrayList<String[]>();
		for (Throwable thLoc = th; thLoc != null; thLoc = thLoc.getCause()) {
			causes.add(new String[] { thLoc.getClass().getName(), thLoc.getMessage() });
		}
		String[][] retval = causes.toArray(new String[causes.size()][2]);
		return retval;
	}

	public static String toStringException(Throwable th) {
		StringWriter errors = new StringWriter();
		th.printStackTrace(new PrintWriter(errors));
		return errors.toString();
	}
}
