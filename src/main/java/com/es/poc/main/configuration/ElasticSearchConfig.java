package com.es.poc.main.configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.Settings.Builder;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Eraine Otayde
 *
 */
@Configuration
public class ElasticSearchConfig {

	@Value("${es.nodes}")
	private String[] nodes;

	@Value("${es.cluster.name}")
	private String clusterName;

	@Bean
	public TransportClient elasticClient() throws NumberFormatException, UnknownHostException {
		Builder settingsBuilder = Settings.builder();
		settingsBuilder.put("cluster.name", clusterName);

		TransportClient client = new PreBuiltTransportClient(settingsBuilder.build());

		for (String node : nodes) {
			client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(node.split(":")[0]),
					Integer.valueOf(node.split(":")[1])));
		}

		return client;
	}

}
