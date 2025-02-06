package kr.or.iei.common;

import java.util.ArrayList;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import kr.or.iei.common.service.CommonService;
import kr.or.iei.common.vo.Board;

/**
 * Application Lifecycle Listener implementation class SearchNoticeTypeListener
 *
 */
@WebListener
public class SearchNoticeTypeListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public SearchNoticeTypeListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
        CommonService commonService = new CommonService();
        ArrayList<Board> typeList = commonService.selectNoticeTypeList();
       
        sce.getServletContext().setAttribute("noticeTypeList", typeList);
        
        
    }
	
}
