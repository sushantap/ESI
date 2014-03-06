package com.talentica.esi.config;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.talentica.esi.lb.LBType;

public class EsiConfig {
	private static final int DEFAULT_TTL = 60;
	private final Map<String, PageLetConfig> pageLetConfigMap;
	private final Map<String, PageLetConfig> wildCardConfigMap;
	private final LBType lbType;

	EsiConfig(Map<String, PageLetConfig> pageLetConfigMap,Map<String, PageLetConfig> wildCardConfigMap,  LBType lbType) {
		this.pageLetConfigMap = pageLetConfigMap;
		this.lbType = lbType;
		this.wildCardConfigMap = wildCardConfigMap;
	}

	static EsiConfig loadEsiConfig(final String configFilePath) throws IOException, ParseException {
		JSONParser parser = new JSONParser();
		final JSONObject configObj = (JSONObject) parser.parse(new FileReader(configFilePath));
		Map<String, PageLetConfig> esiTemplateURLMap = new HashMap<String, PageLetConfig>();
		Map<String, PageLetConfig> wildCardURLMap = new HashMap<String, PageLetConfig>();
		LBType lbType = LBType.random;
		if(configObj.containsKey("lb_type")){
			lbType = LBType.valueOf( (String) configObj.get("lb_type") );
		}

		long defaultTTL = DEFAULT_TTL;
		if(configObj.containsKey("ttl")){
			defaultTTL = (long) configObj.get("ttl");
		}

		JSONArray defaultServers = (JSONArray) configObj.get("servers");
		Set<String> defaultServerSet = new HashSet<>();
		for(Object server: defaultServers){
			defaultServerSet.add((String)server);
		}

		//		PageLetConfig defaultPageLetConfig = new PageLetConfig(defaultServerSet, defaultTTL, "/*");
		//		esiTemplateURLMap.put("/*", defaultPageLetConfig);

		JSONObject urlMap = (JSONObject) configObj.get("url_map");

		@SuppressWarnings("unchecked")
		Set<String> keySet = urlMap.keySet();
		for(String key: keySet ){
			long ttl = defaultTTL;
			String URI = key;
			JSONObject urlMapObj = (JSONObject) urlMap.get(key);
			Set<String> ecServers= new HashSet<>();
			if( urlMapObj.containsKey("servers") ){
				JSONArray ecServerArray = (JSONArray) urlMapObj.get("servers");
				for(Object server: ecServerArray){
					ecServers.add((String)server);
				}
			}else{
				ecServers = defaultServerSet;
			}

			if(urlMapObj.containsKey("ttl")){
				ttl = (long) urlMapObj.get("ttl");
			}

			if(urlMapObj.containsKey("uri")){
				URI = (String) urlMapObj.get("uri");
				if(URI.contains("*")){
					PageLetConfig wildCardConfig = new PageLetConfig(defaultServerSet, defaultTTL, URI);
					wildCardURLMap.put(key, wildCardConfig);
				}
				else{
					PageLetConfig pageLetConfig = new PageLetConfig(ecServers, ttl, URI);
					esiTemplateURLMap.put(key, pageLetConfig);
				}
			}
		}
		return new EsiConfig(esiTemplateURLMap, wildCardURLMap, lbType);
	}

	public PageLetConfig getPageLetConfig(String URI){
		PageLetConfig pageLetConfig = this.pageLetConfigMap.get(URI);
		if(null == pageLetConfig){
			pageLetConfig = this.getWildCardPageLetConfig(URI);
		}
		return pageLetConfig;
	}

	private PageLetConfig getWildCardPageLetConfig(String URI){
		for(String key: this.wildCardConfigMap.keySet()){
			String regex = key.replace("*", "(.*)");
			if(URI.matches(regex)){
				PageLetConfig wildCardPageLetConfig = this.wildCardConfigMap.get(key);
				String resolvedURI = wildCardPageLetConfig.
										getURI().
										replace("*", "").
										concat(URI.replaceFirst("(" + key + ")", ""));
				PageLetConfig resolvedPageLetConfig = new PageLetConfig(wildCardPageLetConfig.getServers(),
																		wildCardPageLetConfig.getTtl(),
																		resolvedURI);
				return resolvedPageLetConfig;
			}
				
		}
		PageLetConfig defaultPageLetConfig = this.wildCardConfigMap.get("/*");
		return new PageLetConfig(defaultPageLetConfig.getServers(),
								 defaultPageLetConfig.getTtl(),
								 URI);
	}

	public LBType getLbType() {
		return lbType;
	}
}
