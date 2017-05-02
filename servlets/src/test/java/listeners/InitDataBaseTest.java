package listeners;

import dao.h2.H2UserSearchDao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

/**
 * 02.05.2017 by K.N.K
 */
class InitDataBaseTest {

    private static ServletContextEvent sce;
    private static ServletContext sc;

    @BeforeAll
    static void init(){
        sce = mock(ServletContextEvent.class);
        sc = mock(ServletContext.class);
    }


    @Test
    void contextInitialized() throws ClassNotFoundException, SQLException, NoSuchFieldException, IllegalAccessException {
        Class.forName("org.h2.Driver");
        Connection connection = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "", "");

        InitDataBase initDataBase = new InitDataBase();

        // Set DataSource mock from reflection
        DataSource ds = mock(DataSource.class);
        Field field = InitDataBase.class.getDeclaredField("dataSource");
        field.setAccessible(true);
        field.set(initDataBase, ds);

        when(sce.getServletContext()).thenReturn(sc);
        when(sc.getRealPath("/WEB-INF/classes/sql")).thenReturn("src/main/resources/sql");
        when(ds.getConnection()).thenReturn(connection);

        // Execute
        initDataBase.contextInitialized(sce);

        verify(sc, atLeastOnce()).setAttribute("UserSearchDao", eq(H2UserSearchDao.class));
    }

    @Test
    void contextDestroyed() {
//        when(sce.getServletContext()).thenReturn(sc);
//        initDataBase.contextDestroyed(sce);
//        verify(sc, atLeastOnce()).removeAttribute("UserDao");
//        verify(sc, atLeastOnce()).removeAttribute("UserSearchDao");
//        verify(sc, atLeastOnce()).removeAttribute("FriendsDao");
//        verify(sc, atLeastOnce()).removeAttribute("MessageDao");
//        verify(sc, atLeastOnce()).removeAttribute("NewsDao");
    }

}