package tags;

import lombok.SneakyThrows;
import model.User;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;

/**
 * <p>Show current user followers.</p>
 *
 * 21.03.2017 by K.N.K
 */
public class FollowersQuery extends TagSupport {
    private List<User> users;

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    @SneakyThrows
    public int doStartTag() throws JspException {

        for(int i = users.size()-1; i>=0; i-- ){
            pageContext.getOut().write("<div class='userscope'>" +
                    "<div class='pic'> <img src='/_images/avatar.png' alt='some img'  width='100%' height='100%'></div>");
            pageContext.getOut().write("<div class='txt'>" +
                    "<a href='user?id="+users.get(i).getId()+"'>"+
                    users.get(i).getFirstName()+" "+users.get(i).getFirstName()+"</a>");
            if(users.get(i).getDateOfBirth()!=null)
                pageContext.getOut().write("<h4>Age: "+users.get(i).getAge()+"</h4>");
            if(users.get(i).getAddress().getCountry()!=null)
                pageContext.getOut().write("<h4>Country: "+users.get(i).getAddress().getCountry()+"</h4>");
            if(users.get(i).getAddress().getCity()!=null)
                pageContext.getOut().write("<h4>City: "+users.get(i).getAddress().getCity()+"</h4></div>");

            pageContext.getOut().write("<div class='txt'><h4>Sex: "+users.get(i).getSex()+"</h4>");

            if(users.get(i).getNextTrip()!=null)
                pageContext.getOut().write("<h4>Next trip: "+users.get(i).getNextTrip()+"</h4></div></div></div>");

        }
        return SKIP_BODY;
    }

}
