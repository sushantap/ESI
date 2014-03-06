package com.talentica.esi.cache;

import com.talentica.esi.config.PageLetConfig;

public class CachedPageLet {
	private final PageLetConfig pageLetConfig;
	private final String html;

	CachedPageLet(PageLetConfig pageLetConfig, String html) {
		this.pageLetConfig = pageLetConfig;
		this.html = html;
	}

	public PageLetConfig getPageLetConfig() {
		return pageLetConfig;
	}

	public String getHtml() {
		return html;
	}

}
