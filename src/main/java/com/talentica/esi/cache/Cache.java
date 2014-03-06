package com.talentica.esi.cache;

import com.talentica.esi.config.PageLetConfig;

public interface Cache {
	public void put(PageLetConfig pageLetConfig, String html);
	public CachedPageLet get(PageLetConfig pageLetConfig);
	
	public void putLBServerOffset(PageLetConfig pageLetConfig, int pos);
	public int getLBServerOffset(PageLetConfig pageLetConfig);
}
