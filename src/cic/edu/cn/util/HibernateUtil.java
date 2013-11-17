package cic.edu.cn.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * 参照官方文档写的HibernateUtil 工具类
 * 有些地方的写法还需要进一步了解
 * @author jiangxu
 *
 */
public class HibernateUtil {

	public static final SessionFactory sessionFactory;
	
	static {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			sessionFactory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable e) {
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}
	}
	
	public static final ThreadLocal<Session> session = new ThreadLocal<Session>();
	
	/**
	 * 取得当前session，没有则新生产一个
	 * @return
	 */
	public static Session currentSession(){
		Session s = session.get();
		if (s == null) {
			s = sessionFactory.openSession();
			session.set(s);
		}
		return s;
	}
	
	/**
	 * 关闭当前session
	 * 并清空ThreadLocal
	 */
	public static void close(){
		Session s = session.get();
		if(s != null){
			s.close();
			session.set(null);
		}
	}
}
