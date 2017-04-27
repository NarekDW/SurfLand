package logger;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * <p>Servlet starts in application first, once and create Logger Object (Log4j)
 * and put this Object to ServletContext</p>
 *
 * <p>Properties in /WEB-INF/log4j.properties and
 * description in /WEB-INF/web.xml</p>
 *
 * How to use  - {@code Logger log = (Logger)getServletContext().getAttribute("log4j")} ...
 *
 * 22.03.2017 by K.N.K
 */
@WebServlet(name = "Log4jInit")
public class Log4jInit extends HttpServlet {

    @Override
    public void init(){
        String logFilename = getInitParameter("logfile");
        String pref = getServletContext().getRealPath("/");
        PropertyConfigurator.configure(pref+logFilename);
        Logger globalLog = Logger.getRootLogger();
        getServletContext().setAttribute("log4j", globalLog);
        globalLog.info("Load on start-up Log4jInit");
    }
}
