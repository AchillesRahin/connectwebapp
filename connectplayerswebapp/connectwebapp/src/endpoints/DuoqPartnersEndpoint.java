package endpoints;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import api.DuoqPartnerAPIResponse;
import main.java.com.lookup.FindDuoqPartners;

/**
 * Servlet implementation class DuoqPartnersEndpoint
 */
@WebServlet("/DuoqPartnersEndpoint")
public class DuoqPartnersEndpoint extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DuoqPartnersEndpoint() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		FindDuoqPartners fdp = new FindDuoqPartners();
		String summonerOne = (String) request.getParameter("summonerOne");
		String region = (String) request.getParameter("region");
		String queue = (String) request.getParameter("queue");
		int q = 0;
		if (queue.equals("solo/duo")) q = 420;
		else if (queue.equals("flex")) q = 440;
		else if (queue.equals("normal")) q = 400;
		else if (queue.equals("normalBlind")) q = 430;
		DuoqPartnerAPIResponse res = fdp.search(summonerOne, region, q);
		
		HttpSession session = request.getSession();
		if (res.getResponseCode() == 500) {
			session.setAttribute("errorResponse", res.getError());
			response.sendRedirect("/connectwebapp/ConnectTwo.jsp");
			return;
		}

		if (session.getAttribute("errorDuoqResponse") != null) session.removeAttribute("errorDuoqResponse");
		session.setAttribute("duoqData", res);
		response.sendRedirect("/connectwebapp/DuoqPartners");
	}

}
