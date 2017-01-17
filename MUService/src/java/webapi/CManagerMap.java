package webapi;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author dima
 */
public class CManagerMap {

    private static final Map<String, DataSource> _m_conn = new HashMap<String, DataSource>();

    public static void addDs(String _name, DataSource _ds) {
        _m_conn.put(_name, _ds);
    }

    public static Connection getCon(String _name) {
        Connection ret = null;
        try {
           // MyLogger.log("CManagerMap try get conn:" + _name);
            DataSource ds = _m_conn.get(_name);
            if (ds != null) {
                ret = ds.getConnection();
            } else {
                Common.debugingLine2D("ERROR datasource:" + _name + " probably is not mapped");
            }
        } catch (SQLException ex) {
            // Logger.getLogger(CManagerMap.class.getName()).log(Level.SEVERE, null, ex);
            //System.out.println(ex.getMessage());
            Common.debugingLine2D("CManagerMap.getCon" + ex.getMessage());
        }
        return ret;
    }

    public static DataSource getDs(String _name) {
        DataSource ret = null;

        ret = _m_conn.get(_name);

        return ret;
    }

    public static Iterator<String> keySet() {
        Iterator<String> ret = _m_conn.keySet().iterator();
        return ret;
    }

    public static boolean enumerate(Context _ctx, NamingEnumeration e, String string) throws NamingException {
        while (e.hasMore()) {
            Binding binding = (Binding) e.next();
            Common.debugingLine2D("DataSource binding Name: " + binding.getName());
            // System.out.println("Type: " + binding.getClassName());
            //  System.out.println("Value: " + binding.getObject());

            DataSource _ds1 = (DataSource) _ctx.lookup("jdbc/" + binding.getName());
            addDs(binding.getName(), _ds1);
        }
        return !_m_conn.isEmpty();
    }
    private static boolean loaded = false;

    public static void Init() {
        if (!loaded) {
            Common.debugingLine2D("enter CManagerMap.Init");
            try {
                Context _ctx = new InitialContext();
                _ctx = (Context) _ctx.lookup("java:comp/env");
                NamingEnumeration e = _ctx.listBindings("jdbc");
                loaded=CManagerMap.enumerate(_ctx, e, "jdbc");
                _ctx.close();
               
            } catch (Exception n) {
                Common.debugingLine2D(n.getMessage());
            } finally {
                Common.debugingLine2D("exit CManagerMap.Init");
            }
            
        }
    }
}
