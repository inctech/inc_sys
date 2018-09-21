package cn.inctech.app.common.cfg.bean;

import java.util.HashSet;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import lombok.extern.slf4j.Slf4j;

@SuppressWarnings({ "unchecked", "rawtypes" })
@Slf4j
@WebListener
public class SessionListener implements HttpSessionListener, HttpSessionAttributeListener {

	@Override
	public void attributeAdded(HttpSessionBindingEvent httpSessionBindingEvent) {
		log.debug("--attributeAdded--");
		HttpSession session = httpSessionBindingEvent.getSession();
		log.debug("key----:" + httpSessionBindingEvent.getName());
		log.debug("value---:" + httpSessionBindingEvent.getValue());
		log.debug("sessionId is:" + session.getId());
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent httpSessionBindingEvent) {
		log.debug("--attributeRemoved--");
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent httpSessionBindingEvent) {
		log.debug("--attributeReplaced--");
	}
	
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		log.debug("---sessionCreated----");
		HttpSession session = event.getSession();
		ServletContext application = session.getServletContext();
		// 在application范围由一个HashSet集保存所有的session
		HashSet sessions = (HashSet) application.getAttribute("sessions");
		if (sessions == null) {
			sessions = new HashSet();
			application.setAttribute("sessions", sessions);
		}
		// 新创建的session均添加到HashSet集中
		sessions.add(session);
		// 可以在别处从application范围中取出sessions集合
		// 然后使用sessions.size()获取当前活动的session数，即为“在线人数”
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) throws ClassCastException {
		log.debug("---sessionDestroyed----");
		HttpSession session = event.getSession();
		log.debug("deletedSessionId: " + session.getId());
		log.debug("session.getCreationTime() is:"+session.getCreationTime());
		log.debug("session.getLastAccessedTime() is:"+session.getLastAccessedTime());
		ServletContext application = session.getServletContext();
		HashSet sessions = (HashSet) application.getAttribute("sessions");
		sessions.remove(session);
	}

}