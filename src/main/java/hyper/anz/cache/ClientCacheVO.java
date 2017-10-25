package hyper.anz.cache;

import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.HFClient;

public class ClientCacheVO {

	private Channel channel;
	private HFClient client;

	public ClientCacheVO(Channel channel, HFClient client) {

		this.channel = channel;
		this.client = client;

	}

	public Channel getChannel() {
		return channel;
	}

	public HFClient getClient() {
		return client;
	}

}
