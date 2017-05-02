package controllers.userpage;

import dao.FriendsDao;
import dao.MessageDao;
import dao.UserSearchDao;
import model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import services.FollowersFriendsCount;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

/**
 * 30.04.2017 by K.N.K
 */

//@RunWith(PowerMockRunner.class)
//@PrepareForTest(StringEncryptUtil.class)
class LogInServletTest {

    private static ServletContext sc;
    private static HttpServletRequest mockRequest;
    private static HttpServletResponse mockResponse;
    private static UserSearchDao searchUser;
    private static RequestDispatcher mockRd;

    @BeforeAll
    static void init() {
        sc = mock(ServletContext.class);
        mockRequest = mock(HttpServletRequest.class);
        mockResponse = mock(HttpServletResponse.class);
        searchUser = mock(UserSearchDao.class);
        mockRd = mock(RequestDispatcher.class);
    }

    @Test
    void errorLogIn() throws ServletException, IOException {
//        StringEncryptUtil stringEncryptUtil = mock(StringEncryptUtil.class);

        // Execute
        LogInServlet logInServlet = new LogInServlet() {
            public ServletContext getServletContext() {
                return sc;
            }
        };

//        H2UserSearchDao h2UserSearchDao = mock(H2UserSearchDao.class);
        when(sc.getAttribute("UserSearchDao")).thenReturn(searchUser);
//        when(searchUser.isUserRegistered(anyString(), anyString())).thenReturn(mockUser);
        when(mockRequest.getRequestDispatcher(anyString())).thenReturn(mockRd);

        when(mockRequest.getParameter("j_username")).thenReturn("name");
        when(mockRequest.getParameter("j_password")).thenReturn("password");


        // Static methods mock???
//        PowerMockito.mockStatic(StringEncryptUtil.class);
//        BDDMockito.given(StringEncryptUtil.encrypt(Mockito.anyString())).willReturn("hash");
//        BDDMockito.when(StringEncryptUtil.encrypt("password")).thenReturn("hash");
//        Mockito.when(StringEncryptUtil.encrypt(Mockito.anyString())).thenReturn("hash");

        logInServlet.doPost(mockRequest, mockResponse);

        verify(mockRequest, atLeastOnce()).getParameter("j_username");
        verify(mockRequest, atLeastOnce()).getParameter("j_password");

//        PowerMockito.verifyStatic();
//        StringEncryptUtil.encrypt(Mockito.anyString());
    }

    @Test
    void rightLogIn() throws ServletException, IOException {
        HttpSession mockSession = mock(HttpSession.class);
        User mockUser = mock(User.class);
        FollowersFriendsCount count = mock(FollowersFriendsCount.class);

        FriendsDao friendsDao = mock(FriendsDao.class);
        MessageDao messageDao = mock(MessageDao.class);

        // Object
        LogInServlet logInServlet = new LogInServlet() {
            public ServletContext getServletContext() {
                return sc;
            }
        };

        when(mockRequest.getParameter("j_username")).thenReturn("name");
        when(mockRequest.getParameter("j_password")).thenReturn("password");

        when(sc.getAttribute("UserSearchDao")).thenReturn(searchUser);
        when(searchUser.isUserRegistered(anyString(), anyString())).thenReturn(mockUser);
        when(sc.getAttribute("FriendsDao")).thenReturn(friendsDao);
        when(sc.getAttribute("MessageDao")).thenReturn(messageDao);

        when(friendsDao.getFollowersCount(any())).thenReturn(count);
        when(messageDao.getMessagesCount(any())).thenReturn(1);

        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockRequest.getRequestDispatcher(anyString())).thenReturn(mockRd);


        // Execute
        logInServlet.doPost(mockRequest, mockResponse);

        // Verify
        verify(mockRequest, atLeastOnce()).getParameter("j_username");
        verify(mockRequest, atLeastOnce()).getParameter("j_password");

        verify(mockRequest, atLeastOnce()).setAttribute("count", count);
        verify(mockRequest, atLeastOnce()).setAttribute("msgcount", 1);

        verify(mockSession, atLeastOnce()).setAttribute("currentUser", mockUser);
        verify(mockRequest, atLeastOnce()).setAttribute("user", mockUser);

        verify(mockRd, atLeastOnce()).forward(mockRequest, mockResponse);
    }
}