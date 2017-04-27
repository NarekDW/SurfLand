package tags;

import dao.MessageDao;
import lombok.SneakyThrows;
import model.Message;
import model.User;
import status.MessageStatus;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;

/**
 * <P>Show message status in chat scope</P>
 *
 * 18.03.2017 by K.N.K
 */
public class MessageQuery extends TagSupport {

    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    @SneakyThrows
    public int doStartTag() throws JspException {
        MessageDao messageDao = (MessageDao) pageContext.getServletContext().getAttribute("MessageDao");
        HttpSession session = pageContext.getSession();
        User currentUser = (User) session.getAttribute("currentUser");

        List<Message> messages = messageDao.getMessages(user, currentUser);

        for(int i = messages.size()-1; i>=0; i--){
            if(messages.get(i).getFromId() == currentUser.getId()){
                if(messages.get(i).getStatus()== MessageStatus.NOTREAD){
                    pageContext.getOut().write("<h5 style='float: right; height: 0px; clear: both;'>"
                            +messages.get(i).getDate()+" "+messages.get(i).getTime()+
                            "<label style='color: red'> not read</label></h5><br/>");
                } else {
                    pageContext.getOut().write("<h5 style='float: right; height: 0px; clear: both;'>"
                            +messages.get(i).getDate()+" "+messages.get(i).getTime()+"</h5><br/>");
                }
                pageContext.getOut().write("<div class='rmsg'>"
                        +messages.get(i).getMessage()+"</div>");
            } else {
                if(messages.get(i).getStatus()== MessageStatus.NOTREAD){
                    pageContext.getOut().write("<h5 style='float: left; height: 0px; clear: both;'>"
                            +messages.get(i).getDate()+" "+messages.get(i).getTime()+
                            "<label style='color: red'> new message</label></h5><br/>");
                } else {
                    pageContext.getOut().write("<h5 style='float: left; height: 0px; clear: both;'>"
                            +messages.get(i).getDate()+" "+messages.get(i).getTime()+"</h5><br/>");
                }
                pageContext.getOut().write("<div class='lmsg'>"
                        +messages.get(i).getMessage()+"</div>");
            }
        }
        return SKIP_BODY;
    }
}
