package org.oa.vshalimov.library.core;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Log {

    private static final Logger logger = Logger.getLogger("vs_library_restful_services");

    public static void error(String message) {
        logger.log(Level.SEVERE, message);
    }

}