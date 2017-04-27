package tags;

import lombok.SneakyThrows;
import model.User;
import services.MessageDirection;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;

/**
 * Define count of new messages for us,
 * or count messages which partner not read.
 *
 * 20.03.2017 by K.N.K
 */
public class ContactQuery extends TagSupport {
    private User user;
    private List<MessageDirection> contacts;

    public void setUser(User user) {
        this.user = user;
    }

    public void setContacts(List<MessageDirection> contacts) {
        this.contacts = contacts;
    }

    @Override
    @SneakyThrows
    public int doStartTag() throws JspException {
        HttpSession session = pageContext.getSession();
        User currentUser = (User) session.getAttribute("currentUser");

        for (MessageDirection md : contacts) {
            if (currentUser.getId() == md.getFromId() && user.getId() == md.getToId()) {
                pageContext.getOut().write("<h4><a href=/message?id=" + user.getId()
                        + ">Write message</a></h4>");
                if (md.getCount() != 0)
                    pageContext.getOut().write("<h4 style='color: red'>Not read +" + md.getCount() + "</h4>");
                break;
            } else if (currentUser.getId() == md.getToId() && user.getId() == md.getFromId()) {
                pageContext.getOut().write("<h4><a href=/message?id=" + user.getId() + ">Read message</a></h4>");
                if (md.getCount() != 0)
                    pageContext.getOut().write("<h4 style='color: red'>New message +" + md.getCount() + "</h4>");
                break;
            }
        }
        return SKIP_BODY;
    }
}
