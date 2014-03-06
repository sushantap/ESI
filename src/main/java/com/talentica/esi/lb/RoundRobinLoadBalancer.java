package com.talentica.esi.lb;

import com.talentica.esi.cache.Cache;
import com.talentica.esi.config.EsiServerResources;
import com.talentica.esi.config.PageLetConfig;

public class RoundRobinLoadBalancer implements LoadBalancer{
	private final Cache lbCache;
	
	public RoundRobinLoadBalancer(EsiServerResources esiServerResources) {
		this.lbCache = esiServerResources.getCache();
	}

	@Override
	public String getServer(PageLetConfig pageLetConfig) {
		String server = ""; 
		Object[] servers = pageLetConfig.getServers().toArray();
		if(servers.length == 1){
			server = (String) servers[0];
		}else{
			int currentOffset = this.lbCache.getLBServerOffset(pageLetConfig);
			++currentOffset;
			if(currentOffset == servers.length){
				currentOffset = 0;
			}
			this.lbCache.putLBServerOffset(pageLetConfig, currentOffset);
			server = (String) servers[currentOffset];
		}
		return server;
	}
}
