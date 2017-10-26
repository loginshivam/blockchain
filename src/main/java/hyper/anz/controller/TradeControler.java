package hyper.anz.controller;

import java.util.concurrent.ThreadLocalRandom;

import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hyper.anz.cache.ClientCache;
import hyper.anz.cache.ClientCacheVO;
import hyper.anz.service.ANZFX;

@RestController
public class TradeControler {

	@RequestMapping("/allTrades")
	public String allTrades() {
		String output = null;
		try {
			ClientCacheVO cacheVO = ClientCache.getClientCache();
			ANZFX anzfx = new ANZFX();

			try {
				output = anzfx.getAllTrades(cacheVO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (CryptoException e) {
			e.printStackTrace();
		} catch (InvalidArgumentException e) {
			e.printStackTrace();
		} catch (TransactionException e) {
			e.printStackTrace();
		}
		return output;

	}

	@RequestMapping(value = "/getTrade/{tradeId}", method = RequestMethod.GET)
	public String getTrade(@PathVariable String tradeId) {
		String output = null;
		try {
			ClientCacheVO cacheVO = ClientCache.getClientCache();
			ANZFX anzfx = new ANZFX();
			System.out.println("helllo World");
			System.out.println(tradeId);
			try {
				output = anzfx.getTrade(cacheVO, tradeId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (CryptoException e) {
			e.printStackTrace();
		} catch (InvalidArgumentException e) {
			e.printStackTrace();
		} catch (TransactionException e) {
			e.printStackTrace();
		}
		return output;

	}

	@RequestMapping(value = "/createTrade/{sym}/{price}/{size}/{side}/{party}", method = RequestMethod.GET)
	public String createTrade(@PathVariable String sym, @PathVariable String price, @PathVariable String size,
			@PathVariable String side,@PathVariable String party) {
		int randomNum = ThreadLocalRandom.current().nextInt(1000, 2000 + 1);
		try {
			ClientCacheVO cacheVO = ClientCache.getClientCache();
			ANZFX anzfx = new ANZFX();
			try {
				
				anzfx.executeTrade(cacheVO, new String[] { "T" + randomNum, sym, price, size, side,party });
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (CryptoException e) {
			e.printStackTrace();
		} catch (InvalidArgumentException e) {
			e.printStackTrace();
		} catch (TransactionException e) {
			e.printStackTrace();
		}
		return "{ \"tradeId\" : \""+randomNum+"\"}";

	}

}