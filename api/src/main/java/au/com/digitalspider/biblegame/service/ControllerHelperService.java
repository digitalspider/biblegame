package au.com.digitalspider.biblegame.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import au.com.digitalspider.biblegame.action.Action;
import au.com.digitalspider.biblegame.io.ActionResponse;

@Service
public class ControllerHelperService {
	public static final String HEADER_FORMAT = "Format";

	public void formatResponse(HttpServletRequest request, Action action) {
		boolean formatHtml = isHtmlFormat(request);
		if (formatHtml) {
			String message = formatHtml(action.getPreMessage());
			action.setPreMessage(message);
			String nextActionMessage = formatHtml(action.getPostMessage());
			action.setPostMessage(nextActionMessage);
		}
	}

	public void formatResponse(HttpServletRequest request, ActionResponse response) {
		boolean formatHtml = isHtmlFormat(request);
		if (formatHtml) {
			String message = formatHtml(response.getMessage());
			response.setMessage(message);
			String nextActionMessage = formatHtml(response.getNextActionMessage());
			response.setNextActionMessage(nextActionMessage);
		}
	}

	public String formatHtml(HttpServletRequest request, String input) {
		boolean formatHtml = isHtmlFormat(request);
		if (formatHtml) {
			return formatHtml(input);
		}
		return input;
	}

	private String formatHtml(String input) {
		return input != null ? input.replace("\n", "<br/>") : null;
	}

	private boolean isHtmlFormat(HttpServletRequest request) {
		boolean formatHtml = request.getHeader(HEADER_FORMAT) != null
				&& request.getHeader(HEADER_FORMAT).equalsIgnoreCase("html");
		return formatHtml;
	}

}
