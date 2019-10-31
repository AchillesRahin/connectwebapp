package endpoints;



import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import api.APIResponse;
import main.java.com.lookup.FindConnection;

/**
 * Servlet implementation class Summoners
 */
@WebServlet("/Summoners")
public class Summoners extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Summoners() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		FindConnection searcher = new FindConnection();
		String summonerOne = (String) request.getParameter("summonerOne");
		String summonerTwo = (String) request.getParameter("summonerTwo");
		String region = (String) request.getParameter("region");
		String queue = (String) request.getParameter("queue");
		int q = 0;
		if (queue.equals("solo/duo")) q = 420;
		else if (queue.equals("flex")) q = 440;
		else if (queue.equals("normal")) q = 400;
		else if (queue.equals("normalBlind")) q = 430;
		
		HttpSession session = request.getSession();
		
		APIResponse apiRes = searcher.searchMatches(summonerOne, summonerTwo, region, q);
		
		if (apiRes.getResponseCode() == 500) {
			session.setAttribute("errorResponse", apiRes.getError());
			response.sendRedirect("/connectwebapp/ConnectTwo.jsp");
			return;
		}

		if (session.getAttribute("errorResponse") != null) session.removeAttribute("errorResponse");
		session.setAttribute("connectData", apiRes);
		response.sendRedirect("/connectwebapp/ConnectTwo");
	}


}
