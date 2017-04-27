package tags;

import dao.FriendsDao;
import lombok.SneakyThrows;
import model.User;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 17.03.2017 by K.N.K
 */
public class FriendQuery extends TagSupport {
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    @SneakyThrows
    public int doStartTag() throws JspException {
        FriendsDao friendsDao = (FriendsDao) pageContext.getServletContext().getAttribute("FriendsDao");
        HttpSession session = pageContext.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        int status = friendsDao.getStatus(currentUser.getId(), user.getId());

        if (status == 2){
            pageContext.getOut().write("<h4>My friend</h4>");
            pageContext.getOut().write("<h4><a href=/deletefriend?id=" + user.getId() + ">Delete friend</a></h4>");
            pageContext.getOut().write("<h4><a href=/message?id=" + user.getId() + ">Write message</a></h4>");
        } else if (status == 1){
            pageContext.getOut().write("<h4>I follow this page</h4>");
            pageContext.getOut().write("<h4><a href=/deletefriend?id=" + user.getId() + ">Delete subscribe</a></h4>");
            pageContext.getOut().write("<h4><a href=/message?id=" + user.getId() + ">Write message</a></h4>");
        } else if (currentUser.getId() != user.getId()){
            if(status == 3){
                pageContext.getOut().write("<h4>My follower</h4>");
                pageContext.getOut().write("<h4><a href=/deletefriend?id=" + user.getId() + ">Delete follower</a></h4>");
            }
            pageContext.getOut().write("<h4><a href=/addfriend?id=" + user.getId() + ">Add friend</a></h4>");
            pageContext.getOut().write("<h4><a href=/message?id=" + user.getId() + ">Write message</a></h4>");
        }
        return SKIP_BODY;
    }

}
