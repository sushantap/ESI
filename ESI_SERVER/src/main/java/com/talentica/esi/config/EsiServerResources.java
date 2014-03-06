package com.talentica.esi.config;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import com.talentica.esi.cache.Cache;
import com.talentica.esi.cache.EhCacheImpl;
import com.talentica.esi.lb.LoadBalancer;

public class EsiServerResources {
	private final Cache cache;
	private final EsiConfig ecConfig;

	public EsiServerResources(String configFilePath) throws IOException, ParseException{
		this.ecConfig = EsiConfig.loadEsiConfig(configFilePath);
		this.cache = new EhCacheImpl();
	}

	public LoadBalancer getLoadBalancer() {
		return LoadBalancerFactory.getLoadBalancer(this.ecConfig.getLbType(), this);
	}

	public EsiConfig getEcConfig() {
		return ecConfig;
	}

	public Cache getCache() {
		return cache;
	}

}
