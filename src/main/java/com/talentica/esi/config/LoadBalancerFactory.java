package com.talentica.esi.config;

import com.talentica.esi.lb.LBType;
import com.talentica.esi.lb.LoadBalancer;
import com.talentica.esi.lb.RandomLoadBalancer;
import com.talentica.esi.lb.RoundRobinLoadBalancer;

class LoadBalancerFactory {
	private static RoundRobinLoadBalancer RRLB;
	private static RandomLoadBalancer RLB;
	
	static LoadBalancer getLoadBalancer(final LBType lbType, final EsiServerResources esiServerResources){
		if(null == RRLB){
			RRLB = new RoundRobinLoadBalancer(esiServerResources);
		}
		if(null == RLB){
			RLB = new RandomLoadBalancer(esiServerResources);
		}
		switch (lbType) {
		case round_robin:
			return RRLB;
		default:
			return RLB;
		}
	}
}
