package com.talentica.esi.lb;

import com.talentica.esi.config.PageLetConfig;

public interface LoadBalancer {
	String getServer(PageLetConfig pageLetConfig);
}
