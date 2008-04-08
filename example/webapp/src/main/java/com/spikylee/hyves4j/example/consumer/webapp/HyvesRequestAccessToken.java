package com.spikylee.hyves4j.example.consumer.webapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.RedirectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spikylee.hyves4j.H4jException;
import com.spikylee.hyves4j.Hyves4j;
import com.spikylee.hyves4j.client.H4jClient;
import com.spikylee.hyves4j.client.config.H4jClientConfig;

public class HyvesRequestAccessToken extends HttpServlet {

	private static final long serialVersionUID = 1L;

	final Logger logger = LoggerFactory.getLogger(HyvesRequestAccessToken.class);
	
	private static ThreadLocal<H4jClientConfig> tLocal = new ThreadLocal<H4jClientConfig>();
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		PrintWriter w = response.getWriter();
		w.append("<html><head>");
		w.append("<script language=\"javascript\" type=\"text/javascript\" src=\"resources/js/jquery-1.2.3.js\">");
		w.append("<script language=\"javascript\" type=\"text/javascript\" src=\"resources/js/formHelper.js\">");
		w.append("</head><body>");
		w.append("<h1>Hyves4j request new acces token</h1>");
		
		H4jClientConfig config = tLocal.get();
		if(config == null) {
			URL consumerPropertiesURL = getClass().getResource(
			"consumer.properties");
			config = new H4jClientConfig("hyves", consumerPropertiesURL);
			tLocal.set(config);
		}
		
		String oauthToken = request.getParameter("oauth_token");
		String expirationType = request.getParameter("type");
		String methods = request.getParameter("methods");
		
		if(!isEmpty(oauthToken)) {
			config.setAccessToken(oauthToken);
		}
		if(!isEmpty(expirationType)) {
			logger.debug("Setting custom expirationType: " + expirationType);
			config.setExpirationType(expirationType);
		}
		if(!isEmpty(methods)) {
			logger.debug("Methods is " + methods);
			String[] m = methods.split("\\,");
			for(int i=0; i<m.length; i++) {
				logger.debug("add methods: " + m[i]);
				config.addMethod(m[i].trim());
			}
			
			config.addCallbackParameter("methods", methods);
		}
		
		if(!isEmpty(methods) || !isEmpty(oauthToken)) {
			
			try {
				H4jClient client = Hyves4j.createAuthenticatedClient(config);
				String callbackMethods = request.getParameter("methods");
				logger.debug("Successfully aquired access token for method(s) " + callbackMethods);
				w.append("<p>You now have a valid access token for method(s) ").append(callbackMethods).append("<br/>");
				w.append("AccessToken: " + client.getConfig().getAccessToken()).append("<br/>");
				w.append("TokenSecret: " + client.getConfig().getTokenSecret());
				w.append("</p>");
				
				config.setExpirationType("user");
				config.getMethods().clear();
			} catch(RedirectException re) {
				//time to go and click yes
				logger.debug("Redirect caught, please go to: " + re.getMessage());
				response.sendRedirect(re.getMessage());
				
			} catch (H4jException e) {
				logger.error(e.getErrorCode());
				logger.error(e.getErrorMessage());
			}
		} else {
			w.append("<form action=\"\">");
			w.append("<input type=\"text\" name=\"methods\" />");
			w.append("<select name=\"type\">");
			w.append("<option value=\"default\">default</option>");
			w.append("<option value=\"infinite\">infinite</option>");
			w.append("<option value=\"user\">user</option>");
			w.append("</select>");
			w.append("<input type=\"submit\" value=\"go\" /></form>");
		}
	}
	
	private boolean isEmpty(String str) {
		return str == null || "".equals(str);
	}
}
