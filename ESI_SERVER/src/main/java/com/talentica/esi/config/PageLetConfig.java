package com.talentica.esi.config;

import java.util.Set;

public class PageLetConfig {
	private Set<String> servers;
	private long ttl;
	private final String URI;

	public PageLetConfig(Set<String> servers, long ttl, String URI) {
		this.servers = servers;
		this.ttl = ttl;
		this.URI = URI;
	}

	public Set<String> getServers() {
		return servers;
	}

	public long getTtl() {
		return ttl;
	}

	public String getURI() {
		return URI;
	}

	public void setServers(Set<String> servers) {
		this.servers = servers;
	}

	public void setTtl(int ttl) {
		this.ttl = ttl;
	}

	public boolean isCacheable(){
		return this.ttl > 0;
	}

	@Override
	public String toString() {
		return "PageLetConfig [servers=" + servers + ", ttl=" + ttl + ", URI="
				+ URI + "]";
	}

}
