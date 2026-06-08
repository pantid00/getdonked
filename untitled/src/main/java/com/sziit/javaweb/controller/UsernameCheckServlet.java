import com.sziit.javaweb.dao.UserJdbc;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/checkUser")
public class UsernameCheckServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");

        // 调用我们刚才在 UserJdbc 里写的查询方法
        boolean exist = UserJdbc.isUserExist(username);

        // 返回 JSON 格式结果
        resp.setContentType("application/json;charset=utf-8");
        // 这里手动拼一下 JSON，如果你项目里没集成 Gson，这样最简单
        resp.getWriter().write("{\"exist\": " + exist + "}");
    }
}