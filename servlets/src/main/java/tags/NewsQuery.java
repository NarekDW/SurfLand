package tags;

import dao.NewsDao;
import lombok.SneakyThrows;
import model.News;
import model.User;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;

/**
 * 20.03.2017 by K.N.K
 */
public class NewsQuery extends TagSupport {
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    @SneakyThrows
    public int doStartTag() throws JspException {
        NewsDao newsDao = (NewsDao) pageContext.getServletContext().getAttribute("NewsDao");

        HttpSession session = pageContext.getSession();
        User currentUser = (User) session.getAttribute("currentUser");

        if(currentUser.getId() == user.getId()){
            pageContext.getOut().write(
                    "<div style='float: left; width: 100%; height: 45%; clear: both;" +
                            " text-align: center; padding-top: 10%;'>"+
                    "<h3>Create News</h3>"+
                    "<form action='/createnews' method='post'>"+
                        "<p><textarea name='msg'"+
            "style='resize: none; ' maxlength='2000' rows='5' cols='105'></textarea></p>"+
                        "<p><input type='submit' name='submit' value='Create'" +
                            " style='height: 35px; width: 150px;'></p>"+
                    "</form></div>"
            );
        }

        List<News> userNews = newsDao.getUserNews(user);

        for(int i = userNews.size()-1; i>=0; i--){
            pageContext.getOut().write("<h5 style='float: centr; height: 0px;" +
                    " clear: both; text-align: center; color: red;'>"
                    +userNews.get(i).getDate()+" "+userNews.get(i).getTime()+"</h5>");
            pageContext.getOut().write("<div class='msg'>"
                    +userNews.get(i).getMessage()+"</div>");
        }
        return SKIP_BODY;
    }
}
