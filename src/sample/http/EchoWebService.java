package sample.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import common.TextUtil;
import core.http.HttpMethod;
import core.http.HttpProtocolParser;
import core.http.HttpRequest;
import core.http.HttpResponse;
import core.http.IWebServiceLogic;

public class EchoWebService implements IWebServiceLogic{
	
	public HttpResponse Respond(HttpRequest request){
		
		HttpResponse response = null;
		if(request.getRequestType().equals(HttpMethod.GET)){
			response = ResponseGET(request);
		}else if(request.getRequestType().equals(HttpMethod.POST)){
			response = null;
		}
		return response;
	}
	
	private HttpResponse ResponseGET(HttpRequest request){
		
		String output = "[Jinseng] Says Hello : " + request.getUrl() + TextUtil.CRLF + "(If you want to see a bird, than call query /image)";
		
		System.out.println(output);
		
		HttpResponse respond = new HttpResponse("HTTP/1.1", 200, "OK");
		
		if(request.getUrl().contains("image")){
			//Send Image when protocol has the string "image"
			File f = new File("toucan.jpg");
			try {
				FileInputStream is = new FileInputStream(f);
				byte[] obj = new byte[(int) f.length()];
				is.read(obj);
				respond.setHeaderProperty("Content-Type", "image/jpeg");
				respond.setHeaderProperty("Content-Length", String.valueOf(obj.length));
				respond.setHeaderProperty("Cache-Control", "no-cache");
				respond.setMessageBody(obj);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			//Send url path echo system.
			respond.setHeaderProperty("Content-Type", "text/html;charset=utf-8");
			respond.setHeaderProperty("Content-Length", String.valueOf(output.length() + 2));
			respond.setHeaderProperty("Cache-Control", "no-cache");
			respond.setMessageBody(output);
		}
		
		return respond;
	}

}
