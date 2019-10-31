package endpoints;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import api.BanChampsAPIResponse;
import api.DuoqPartnerAPIResponse;
import main.java.com.lookup.FindChampionData;
import main.java.com.lookup.FindDuoqPartners;

/**
 * Servlet implementation class DuoqPartnersEndpoint
 */
@WebServlet("/BanMatchEndpoint")
public class BanMatchEndpoint extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BanMatchEndpoint() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		FindChampionData fdp = new FindChampionData();
		String summonerOne = (String) request.getParameter("summonerOne");
		String region = (String) request.getParameter("region");
		String queue = (String) request.getParameter("queue");
		String champIDFilter = (String) request.getParameter("champID");
		int q = 0;
		if (queue.equals("solo/duo")) q = 420;
		else if (queue.equals("flex")) q = 440;
		else if (queue.equals("normal")) q = 400;
		else if (queue.equals("normalBlind")) q = 430;
		BanChampsAPIResponse res = fdp.find(summonerOne, region, champIDFilter, q);
		
		HttpSession session = request.getSession();
		if (res.getResponseCode() == 500) {
			session.setAttribute("errorResponse", res.getError());
			response.sendRedirect("/connectwebapp/BanChamps");
			return;
		}

		if (session.getAttribute("errorBanChamps") != null) session.removeAttribute("errorBanChamps");
		session.setAttribute("banChampsData", res);
		response.sendRedirect("/connectwebapp/BanChamps");
	}

}
