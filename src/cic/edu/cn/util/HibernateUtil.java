package cic.edu.cn.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * ���չٷ��ĵ�д��HibernateUtil ������
 * ��Щ�ط���д������Ҫ��һ���˽�
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
	 * ȡ�õ�ǰsession��û����������һ��
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
	 * �رյ�ǰsession
	 * �����ThreadLocal
	 */
	public static void close(){
		Session s = session.get();
		if(s != null){
			s.close();
			session.set(null);
		}
	}
}
