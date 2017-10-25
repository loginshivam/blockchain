package hyper.anz.cache;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.User;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;

import hyper.anz.user.Trader;

public class ClientCache implements Serializable {

	private static final long serialVersionUID = -305021503282754667L;
	private static volatile HFClient client = null;
	private static final Object _OBJECT = new Object();
	private static ClientCacheVO cacheVO;

	private void writeObject(ObjectOutputStream os) throws Exception {
		throw new Exception();
	}

	private void readObject(ObjectInputStream is) throws Exception {
		throw new Exception();
	}

	public static final ClientCacheVO getClientCache() throws CryptoException, InvalidArgumentException, TransactionException {

		if (client == null) {
			synchronized (_OBJECT) {
				if (client == null) {
					client = HFClient.createNewInstance();
					CryptoSuite cs = CryptoSuite.Factory.getCryptoSuite();
					client.setCryptoSuite(cs);
					User user = new Trader("c:\\data\\creds", "PeerAdmin");
					client.setUserContext(user);
					Channel channel = client.newChannel("mychannel");
					channel.addPeer(client.newPeer("peer", "grpc://192.168.99.100:7051"));
					channel.addOrderer(client.newOrderer("orderer", "grpc://192.168.99.100:7050"));
					channel.initialize();
					cacheVO = new ClientCacheVO(channel, client);
				}
			}
		}

		return cacheVO;

	}

}
