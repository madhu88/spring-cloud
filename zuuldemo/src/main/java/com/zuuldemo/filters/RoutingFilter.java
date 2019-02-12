package com.zuuldemo.filters;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.zuuldemo.config.ZuulConfig;

@Component
public class RoutingFilter extends ZuulFilter {
	
	private static final int FILTER_ORDER = 1;
	private static final boolean SHOULD_FILTER = true;
	
	private static final Logger logger = LoggerFactory.getLogger(RoutingFilter.class);
	
	private ProxyRequestHelper helper = new ProxyRequestHelper();
	
	@Autowired
	private FilterUtils filterUtils;
	
	@Autowired
	private ZuulConfig zuulConfig;

	@Override
	public boolean shouldFilter() {
		return SHOULD_FILTER;
	}

	@Override
	public Object run() throws ZuulException {		
		if (shoudlGoToV2()) {
			logger.debug("Going to V2");
			forwardToSpecialRoute(zuulConfig.getGetNameURL());
		}
		logger.debug("Going to V1");
		return null;
	}
	
	private void route() {
		/*OkHttpClient httpClient = new OkHttpClient.Builder()
				// customize
				.build();

		RequestContext context = RequestContext.getCurrentContext();
		HttpServletRequest request = context.getRequest();

		String method = request.getMethod();

		String uri = this.helper.buildZuulRequestURI(request);

		Headers.Builder headers = new Headers.Builder();
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String name = headerNames.nextElement();
			Enumeration<String> values = request.getHeaders(name);

			while (values.hasMoreElements()) {
				String value = values.nextElement();
				headers.add(name, value);
			}
		}

		InputStream inputStream = request.getInputStream();

		RequestBody requestBody = null;
		if (inputStream != null && HttpMethod.permitsRequestBody(method)) {
			MediaType mediaType = null;
			if (headers.get("Content-Type") != null) {
				mediaType = MediaType.parse(headers.get("Content-Type"));
			}
			requestBody = RequestBody.create(mediaType, StreamUtils.copyToByteArray(inputStream));
		}

		Request.Builder builder = new Request.Builder()
				.headers(headers.build())
				.url(uri)
				.method(method, requestBody);

		Response response = httpClient.newCall(builder.build()).execute();

		LinkedMultiValueMap<String, String> responseHeaders = new LinkedMultiValueMap<>();

		for (Map.Entry<String, List<String>> entry : response.headers().toMultimap().entrySet()) {
			responseHeaders.put(entry.getKey(), entry.getValue());
		}

		this.helper.setResponse(response.code(), response.body().byteStream(),
				responseHeaders);
		context.setRouteHost(null); // prevent SimpleHostRoutingFilter from running
*/	}

	@Override
	public String filterType() {
		return FilterUtils.ROUTE_FILTER_TYPE;
	}

	@Override
	public int filterOrder() {		
		return FILTER_ORDER;
	}	
	
	private boolean shoudlGoToV2() {
		if (null != filterUtils.getServiceVersion() && filterUtils.getServiceVersion().equalsIgnoreCase("v2")) {
			return true;
		}
		return false;
	}
	
	private void forwardToSpecialRoute(String route) {
		RequestContext context = RequestContext.getCurrentContext();
		HttpServletRequest request = context.getRequest();
		
		MultiValueMap<String, String> headers = this.helper.buildZuulRequestHeaders(request);
		MultiValueMap<String, String> params = this.helper.buildZuulRequestQueryParams(request);
		
		String verb = getVerb(request);
		InputStream requestEntity = getRequestBody(request);
		if (request.getContentLength() < 0) {
			context.setChunkedRequestBody();
		}
		
		this.helper.addIgnoredHeaders();
		CloseableHttpClient httpClient = null;
		HttpResponse response = null;				
		
		try {
			httpClient = HttpClients.createDefault();
			response = forward(httpClient, verb, route, request, headers,
					params, requestEntity);
			setResponse(response);
		} catch (IOException e) {
			logger.error("IOException while forwarding the request", e);
		}
		
		finally{
            try {
                httpClient.close();
            } catch(IOException ex){
            	logger.error("IOException while closing client", ex);
            }
		}
	}
	
	private String getVerb(HttpServletRequest request) {
        String sMethod = request.getMethod();
        return sMethod.toUpperCase();
	}
	
	private InputStream getRequestBody(HttpServletRequest request) {
		InputStream requestEntity = null;
		try {
			requestEntity = request.getInputStream();
		} catch (IOException e) {
			//request body is not mandatory for all services hence we can ignore logging
			//but we have logged it as we need request body organization service
			logger.error("Error in getting request body.", e);
		}
		return requestEntity;		
	}
	
	private HttpResponse forward(HttpClient httpClient, String verb, String url, HttpServletRequest request,
			MultiValueMap<String, String> headers, MultiValueMap<String, String> params, InputStream requestEntity)
					throws IOException {
		
		String uri = this.helper.buildZuulRequestURI(request);
		
		logger.debug("**** uri from request = " + uri);
		
		Map<String, Object> info = this.helper.debug(verb, url, headers, params, requestEntity);
		
		URL host = new URL(url);
		HttpHost httpHost = getHttpHost(host);
		
		HttpRequest httpRequest;
		int contentLength = request.getContentLength();
		
		InputStreamEntity entity = new InputStreamEntity(requestEntity, contentLength,
				request.getContentType() != null ? ContentType.create(request.getContentType()) : null);
		
		switch (verb.toUpperCase()) {
			case "POST":
				HttpPost httpPost = new HttpPost(url);
				httpRequest = httpPost;
				httpPost.setEntity(entity);
				break;
			case "PUT":
				HttpPut httpPut = new HttpPut(url);
				httpRequest = httpPut;
				httpPut.setEntity(entity);
				break;
			case "PATCH":
				HttpPatch httpPatch = new HttpPatch(url);
				httpRequest = httpPatch;
				httpPatch.setEntity(entity);
				break;
			default:
				httpRequest = new BasicHttpRequest(verb, url);
		}
		
		try {
            httpRequest.setHeaders(convertHeaders(headers));
            HttpResponse zuulResponse = forwardRequest(httpClient, httpHost, httpRequest);
            return zuulResponse;
        }
        finally {
        	
        }		
	}
	
	private HttpHost getHttpHost(URL host) {
		HttpHost httpHost = new HttpHost(host.getHost(), host.getPort(), host.getProtocol());
		return httpHost;
	}
	
	private Header[] convertHeaders(MultiValueMap<String, String> headers) {
		List<Header> headersList = new ArrayList<Header>();
		for (String name : headers.keySet()) {
            for (String value : headers.get(name)) {
            	headersList.add(new BasicHeader(name, value));
            }
		}
		return headersList.toArray(new BasicHeader[0]);
	}
	
	private HttpResponse forwardRequest(HttpClient httpclient, HttpHost httpHost,
            HttpRequest httpRequest) throws IOException {
		return httpclient.execute(httpHost, httpRequest);
	}
	
	private void setResponse(HttpResponse response) throws IOException {
        this.helper.setResponse(response.getStatusLine().getStatusCode(),
                response.getEntity() == null ? null : response.getEntity().getContent(),
                revertHeaders(response.getAllHeaders()));
	}
	
	private MultiValueMap<String, String> revertHeaders(Header[] headers) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        for (Header header : headers) {
            String name = header.getName();
            if (!map.containsKey(name)) {
                map.put(name, new ArrayList<String>());
            }
            map.get(name).add(header.getValue());
        }
        return map;
	}	
	
}
