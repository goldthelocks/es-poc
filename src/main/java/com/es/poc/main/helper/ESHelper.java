package com.es.poc.main.helper;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.Settings.Builder;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.stereotype.Component;

/**
 * @author Eraine Otayde
 *
 */
@Component
public class ESHelper {

	public Client createClient(String[] nodes, String clusterName) throws NumberFormatException, UnknownHostException {
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
