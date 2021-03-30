package servlets;

import jakarta.servlet.http.HttpServlet;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utils.FractionConversion;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AllRequestsServlet extends HttpServlet {
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        Object obj = null;
        FractionConversion converter = new FractionConversion();

        try {
            obj = new JSONParser().parse(request.getReader());
            if(obj != null) {
                JSONObject jo = (JSONObject) obj;
                FractionConversion.Response answer = converter.conversion((String) jo.get("from"), (String) jo.get("to"));
                response.setContentType("text/html;charset=utf-8");
                if(answer.getResponseCode() == 200){
                    response.setStatus(answer.getResponseCode());
                    response.getWriter().println(answer.getValue());
                } else {
                    response.setStatus(answer.getResponseCode());
                    response.getWriter().println("Error");
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
