package ejb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ejb.entities.Message;
import ejb.entities.MessageLight;
import ejb.session.MySessionBeanInterface;

/**
 * Servlet implementation class BeanServlet
 */
@WebServlet("/BeanServlet")
public class BeanServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB
	MySessionBeanInterface sessionBean;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BeanServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("BeanServlet.doGet()");
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// stykles
		out.append("<style type=\"text/css\">");
		out.append("span {");
		out.append("display: inline-block;");
		out.append("}");
		out.append("</style>");
		
		out.println("sessionBeanData: " + sessionBean.getData());
		out.append("<br />");
		out.append("\r\n");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	List<MessageLight> messages = sessionBean.getDataList();
    	for (MessageLight m : messages) {
    		out.append("<span style='min-width:150px'>");
    		out.append(sdf.format(m.getTime()));
    		out.append("</span>");
    		out.append("&nbsp;");
    		out.append("&nbsp;");

    		out.append("<span style='min-width:200px'>");
    		out.append(m.getSender());
    		out.append("</span>");
    		out.append("&nbsp;");
    		out.append("&nbsp;");

    		out.append("<span style='min-width:450px'>");
    		out.append(m.getContent());
    		out.append("</span>");

    		out.append("<br />");
    		out.append("\r\n");
    	}
    	out.flush();
	}

}
