package xyz.justimagine.template;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class Main {
    public static void main(final String[] arguments) throws Exception {
        final Server server = new Server();
        server.addConnector(createHttpConnector(server));

        final ServletHandler handler = new ServletHandler();
        handler.addServletWithMapping(new ServletHolder(new HelloServlet("foo")), "/*");
        handler.addServletWithMapping(new ServletHolder(new HelloServlet("far")), "/other/*");

        server.setHandler(handler);
        server.start();
    }

    private static ServerConnector createHttpConnector(final Server server) {
        // HTTP connector
        ServerConnector http = new ServerConnector(server);
        http.setHost("0.0.0.0");
        http.setPort(8080);
        http.setIdleTimeout(30000);
        return http;
    }

   @SuppressWarnings("serial") public static class HelloServlet extends HttpServlet {
        private String str;
        public HelloServlet(String str) { this.str = str; }

        @Override
        protected void doGet(HttpServletRequest request,
                             HttpServletResponse response) throws IOException {
            String param = request.getParameter("foo");
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("text/html");
            response.setCharacterEncoding("utf-8");
            response.getWriter().println("<h1>Hello from HelloServlet</h1> " + str + ":" + param);
        }
    }
}
