package com.talentica.esi.lb;

import java.util.Random;

import com.talentica.esi.cache.Cache;
import com.talentica.esi.config.EsiServerResources;
import com.talentica.esi.config.PageLetConfig;

public class RandomLoadBalancer implements LoadBalancer{
	private final Cache lbCache;
	private final Random random;
	public RandomLoadBalancer(EsiServerResources esiServerResources) {
		this.random = new Random();
		this.lbCache = esiServerResources.getCache();
	}

	@Override
	public String getServer(PageLetConfig pageLetConfig) {
		Object[] servers = pageLetConfig.getServers().toArray();
		int randomOffset = this.random.nextInt(pageLetConfig.getServers().size());
		String server = (String) servers[randomOffset];
		int previousOffset = this.lbCache.getLBServerOffset(pageLetConfig);
		if(previousOffset == randomOffset){
			++randomOffset;
			if(randomOffset >= servers.length){
				randomOffset = 0;
			}
		}
		server = (String) servers[randomOffset];
		this.lbCache.putLBServerOffset(pageLetConfig, randomOffset);
		return server;
	}

}
