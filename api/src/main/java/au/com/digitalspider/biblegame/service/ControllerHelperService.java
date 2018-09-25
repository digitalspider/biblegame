package au.com.digitalspider.biblegame.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import au.com.digitalspider.biblegame.io.ActionResponse;

@Service
public class ControllerHelperService {
	public static final String HEADER_FORMAT = "Format";

	public void formatResponse(HttpServletRequest request, ActionResponse response) {
		boolean formatHtml = request.getHeader(HEADER_FORMAT) != null
				&& request.getHeader(HEADER_FORMAT).equalsIgnoreCase("html");
		if (formatHtml) {
			String message = response.getMessage() != null ? response.getMessage().replace("\n", "<br/>") : null;
			response.setMessage(message);
			String nextActionMessage = response.getNextActionMessage() != null
					? response.getNextActionMessage().replace("\n", "<br/>")
					: null;
			response.setNextActionMessage(nextActionMessage);
		}
	}

}
