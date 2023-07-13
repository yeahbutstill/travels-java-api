package io.github.mariazevedo88.travelsjavaapi.service.ratelimiting.impl;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.mariazevedo88.travelsjavaapi.enumeration.APIUsagePlansEnum;
import io.github.mariazevedo88.travelsjavaapi.service.ratelimiting.APIUsagePlansService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class that implements the API usage plans service methods.
 * 
 * @author Mariana Azevedo
 * @since 11/06/2020
 */
@Service
public class APIUsagePlansServiceImpl implements APIUsagePlansService {
	
	private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

	/**
	 * @see APIUsagePlansService#resolveBucket(String)
	 */
	@Override
	public Bucket resolveBucket(String apiKey) {
		return cache.computeIfAbsent(apiKey, this::newBucket);
	}

	/**
	 * @see APIUsagePlansService#newBucket(String)
	 */
	@Override
	public Bucket newBucket(String apiKey) {
		APIUsagePlansEnum pricingPlan = APIUsagePlansEnum.resolvePlanFromApiKey(apiKey);
		return bucket(pricingPlan.getLimit());
	}

	/**
	 * @see APIUsagePlansService#bucket(Bandwidth)
	 */
	@Override
	public Bucket bucket(Bandwidth limit) {
		return Bucket4j.builder().addLimit(limit).build();
	}

}
