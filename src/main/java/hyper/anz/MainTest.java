package hyper.anz;

import java.util.Collection;
import java.util.Iterator;

import org.hyperledger.fabric.sdk.ChaincodeID;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.hyperledger.fabric.sdk.QueryByChaincodeRequest;
import org.hyperledger.fabric.sdk.TransactionProposalRequest;
import org.hyperledger.fabric.sdk.User;
import org.hyperledger.fabric.sdk.security.CryptoSuite;

public class MainTest {

	private static HFClient client = null;

	public static void main(String[] args) throws Exception {
		client = HFClient.createNewInstance();
		CryptoSuite cs = CryptoSuite.Factory.getCryptoSuite();
		client.setCryptoSuite(cs);

		User user = new SampleUser("c:\\data\\creds", "PeerAdmin");
		// "Log in"
		client.setUserContext(user);
		Channel channel = client.newChannel("mychannel");
		channel.addPeer(client.newPeer("peer", "grpc://192.168.99.100:7051"));
		// It always wants orderer, otherwise even query does not work
		channel.addOrderer(client.newOrderer("orderer", "grpc://192.168.99.100:7050"));
		channel.initialize();

		String newOwner = "2000";
		System.out.println("New owner is '" + newOwner + "'\n");

		queryFabcar(channel, "TRADE10");
		//executeTrade(channel,new String[] {"TRADE10","BSTUSD","150","200","S"});
		// updateCarOwner(channel, "CAR1", newOwner, false);

		// System.out.println("after request for transaction without commit");
		// queryFabcar(channel, "CAR1");
		// updateCarOwner(channel, "CAR1", newOwner, true);

		// System.out.println("after request for transaction WITH commit");
		// queryFabcar(channel, "CAR1");

		// System.out.println("Sleeping 5s");
		// Thread.sleep(5000); // 5secs
		// queryFabcar(channel, "CAR1");
		// System.out.println("all done");

	}

	private static void queryFabcar(Channel channel, String key) throws Exception {
		QueryByChaincodeRequest req = client.newQueryProposalRequest();
		ChaincodeID cid = ChaincodeID.newBuilder().setName("anzfx").build();
		req.setChaincodeID(cid);
		req.setFcn("getTrade");
		req.setArgs(new String[] { key });
		System.out.println("Querying for " + key);
		Collection<ProposalResponse> resps = channel.queryByChaincode(req);
		for (ProposalResponse resp : resps) {
			String payload = new String(resp.getChaincodeActionResponsePayload());
			System.out.println("response: " + payload);
		}

	}

	private static void executeTrade(Channel channel, String[] key) throws Exception {
		TransactionProposalRequest req = client.newTransactionProposalRequest();
		ChaincodeID cid = ChaincodeID.newBuilder().setName("anzfx").build();
		req.setChaincodeID(cid);
		req.setFcn("executeTrade");
		req.setArgs(key);
		Collection<ProposalResponse> resps = channel.sendTransactionProposal(req);
		Iterator<ProposalResponse> p = resps.iterator();
		while(p.hasNext()) {
			System.out.println(p.next().getStatus());
		}
		channel.sendTransaction(resps);

	}

//	private static void updateCarOwner(Channel channel, String key, String newOwner, Boolean doCommit)
//			throws Exception {
//		TransactionProposalRequest req = client.newTransactionProposalRequest();
//		ChaincodeID cid = ChaincodeID.newBuilder().setName("fabcar").build();
//		req.setChaincodeID(cid);
//		req.setFcn("changeCarOwner");
//		req.setArgs(new String[] { key });
//		System.out.println("Executing for " + key);
//		Collection<ProposalResponse> resps = channel.sendTransactionProposal(req);
//		Iterator<ProposalResponse> it = resps.iterator();
//		while (it.hasNext()) {
//			System.out.println("-------------------");
//			System.out.println("-------------------");
//			System.out.println(it.next().isVerified());
//			System.out.println("-------------------");
//			System.out.println("-------------------");
//		}
//		if (doCommit) {
//			// channel.sendTransaction(resps);
//			CompletableFuture<TransactionEvent> event = channel.sendTransaction(resps);
//			// System.out.println(event.isCompletedExceptionally());
//		}
//	}

}
