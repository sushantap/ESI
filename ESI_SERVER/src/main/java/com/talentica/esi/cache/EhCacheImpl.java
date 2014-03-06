package com.talentica.esi.cache;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import com.talentica.esi.config.PageLetConfig;

public class EhCacheImpl implements Cache{
	private final CacheManager cchm;

	public EhCacheImpl(){
		this.cchm = CacheManager.getInstance();
		this.cchm.addCache("esi");
		this.cchm.addCache("lb");
	}
	@Override
	public void put(PageLetConfig pageLetConfig, String html) {
		Element element = new Element(pageLetConfig.getURI(), html);
		element.setTimeToLive((int)pageLetConfig.getTtl());
		this.cchm.getCache("esi").put(element);
		
	}

	@Override
	public CachedPageLet get(PageLetConfig pageLetConfig) {
		Element element = this.cchm.getCache("esi").get(pageLetConfig.getURI());
		CachedPageLet cachedPageLet = null;
		if(null != element){
			cachedPageLet = new CachedPageLet(pageLetConfig, (String)element.getObjectValue()); 
		}
		return cachedPageLet;
	}

	@Override
	public void putLBServerOffset(PageLetConfig pageLetConfig, int pos) {
		Element element = new Element(pageLetConfig.getURI(), pos);
		element.setTimeToLive(60*60);
		this.cchm.getCache("lb").put(element);
		
	}

	@Override
	public int getLBServerOffset(PageLetConfig pageLetConfig) {
		int offset = -1;
		Element element = this.cchm.getCache("lb").get(pageLetConfig.getURI());
		if(null != element){
			offset = (int) element.getObjectValue();
		}
		return offset;
	}

	
}
