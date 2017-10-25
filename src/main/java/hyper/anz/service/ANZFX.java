package hyper.anz.service;

import java.util.Collection;
import java.util.Iterator;

import org.hyperledger.fabric.sdk.ChaincodeID;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.hyperledger.fabric.sdk.QueryByChaincodeRequest;
import org.hyperledger.fabric.sdk.TransactionProposalRequest;

import hyper.anz.cache.ClientCacheVO;

public class ANZFX {

	public void getTrade(ClientCacheVO cacheVO, String key) throws Exception {
		QueryByChaincodeRequest req = cacheVO.getClient().newQueryProposalRequest();
		ChaincodeID cid = ChaincodeID.newBuilder().setName("anzfx").build();
		req.setChaincodeID(cid);
		req.setFcn("getTrade");
		req.setArgs(new String[] { key });
		System.out.println("Querying for " + key);
		Collection<ProposalResponse> resps = cacheVO.getChannel().queryByChaincode(req);
		for (ProposalResponse resp : resps) {
			String payload = new String(resp.getChaincodeActionResponsePayload());
			System.out.println("response: " + payload);
		}

	}

	public void executeTrade(ClientCacheVO cacheVO, String[] key) throws Exception {
		TransactionProposalRequest req = cacheVO.getClient().newTransactionProposalRequest();
		ChaincodeID cid = ChaincodeID.newBuilder().setName("anzfx").build();
		req.setChaincodeID(cid);
		req.setFcn("executeTrade");
		req.setArgs(key);
		Collection<ProposalResponse> resps = cacheVO.getChannel().sendTransactionProposal(req);
		Iterator<ProposalResponse> p = resps.iterator();
		while (p.hasNext()) {
			System.out.println(p.next().getStatus());
		}
		cacheVO.getChannel().sendTransaction(resps);

	}

}
