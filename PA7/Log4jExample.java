import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4jExample {
  static Logger logger = LogManager.getLogger(Log4jExample.class);  

  public static void main(String[] args) {
    logger.debug("DEBUG!");
    logger.info("INFO!!");
    logger.warn("WARNING!!!");
    logger.fatal("FATAL!!!!");
  }
}
