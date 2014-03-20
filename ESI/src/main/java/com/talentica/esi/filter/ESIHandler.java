package com.talentica.esi.filter;

import java.io.IOException;
import java.io.StringReader;
import java.io.Writer;
import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletResponse;

import net.htmlparser.jericho.Segment;
import net.htmlparser.jericho.StartTag;
import net.htmlparser.jericho.StreamedSource;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.talentica.esi.cache.Cache;
import com.talentica.esi.cache.CachedPageLet;
import com.talentica.esi.config.EsiConfig;
import com.talentica.esi.config.EsiServerResources;
import com.talentica.esi.config.PageLetConfig;

public class ESIHandler {
	private static final String CONTENT_TYPE_HEADER = "Content-Type";
	private static final String HTML_CONTENT_TYPE = "text/html";
	private final HttpServletResponse response;
	private final EsiServerResources esiServerResources;
	private final Cache cache;
	private final PageLetConfig pageLetConfig;

	public ESIHandler(PageLetConfig pageLetConfig, EsiServerResources esiServerResources, HttpServletResponse response) {
		this.pageLetConfig = pageLetConfig;
		this.response = response;
		this.esiServerResources = esiServerResources;
		this.cache = esiServerResources.getCache();
	}

	private String getURL(String URI) throws MalformedURLException{
		EsiConfig ec = this.esiServerResources.getEcConfig();
		PageLetConfig pageLetConfig = ec.getPageLetConfig(URI);
		if(URI.contains("http:")){
			return URI;
		}else{
			String url = "http://" + this.esiServerResources.getLoadBalancer().getServer(pageLetConfig) + URI;
			return url;
		}
	}

	public void handle() throws IOException, InterruptedException, ExecutionException {

		//check cache
		StreamedSource streamedSource;
		CachedPageLet pageLet = this.cache.get(this.pageLetConfig);

		if(null != pageLet){
			String html = pageLet.getHtml();
			streamedSource = new StreamedSource(new StringReader(html));
			parseESITags(streamedSource);
		}else{
			System.err.println("did not get stuff from cache for pagelet ->" + this.pageLetConfig);
			String URI = this.pageLetConfig.getURI();
			HttpClient client = HttpClients.createDefault();
			HttpGet request = new HttpGet(getURL(URI));
			HttpResponse response = client.execute(request);
			if(!response.getFirstHeader(CONTENT_TYPE_HEADER).getValue().contains(HTML_CONTENT_TYPE)){
				this.response.getWriter().write(EntityUtils.toString(response.getEntity()));
				for(Header header: response.getAllHeaders()){
					this.response.setHeader(header.getName(), header.getValue());
				}
			}else{
				String html = EntityUtils.toString(response.getEntity());
				this.response.setContentType("text/html");
				streamedSource = new StreamedSource(new StringReader(html));
				String cacheHtml = parseESITags(streamedSource);
				if(this.pageLetConfig.isCacheable()){
					this.cache.put(this.pageLetConfig, cacheHtml);
				}
			}
		}
	}

	private String parseESITags(StreamedSource streamedSource ) throws IOException, InterruptedException, ExecutionException {
		Writer writer=this.response.getWriter();
		String cacheHtml = "";
		int lastSegmentEnd=0;
		for (Segment segment : streamedSource) {
			cacheHtml = cacheHtml.concat(segment.toString());
			if (segment.getEnd()<=lastSegmentEnd) continue; 
			lastSegmentEnd=segment.getEnd();
			if (segment instanceof StartTag) {
				StartTag tag = (StartTag) segment;
				String tagName = tag.getName();					
				switch (tagName) {
				case "esi:include":
					this.response.flushBuffer();
					String uri = tag.getAttributes().get("src").getValue();
					int ttl = 0;
					if(null != tag.getAttributes().get("ttl")){
						ttl = Integer.valueOf(tag.getAttributes().get("ttl").getValue());
					}
					EsiConfig ec = this.esiServerResources.getEcConfig();
					PageLetConfig childPageLetConfig = ec.getPageLetConfig(uri);
					if(null == childPageLetConfig){
						childPageLetConfig = new PageLetConfig(this.pageLetConfig.getServers(), ttl, uri);
					}else{
						childPageLetConfig.setTtl(ttl);
					}
					ESIHandler esiHandler = new ESIHandler(childPageLetConfig, this.esiServerResources, this.response);
					esiHandler.handle();
					break;
				default:
					writer.write(segment.toString());
					break;
				}
			} else {
				writer.write(segment.toString());
			}
		}
		return cacheHtml;
	}
}
