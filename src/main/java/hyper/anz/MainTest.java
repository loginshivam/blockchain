package hyper.anz;

import hyper.anz.cache.ClientCache;
import hyper.anz.cache.ClientCacheVO;
import hyper.anz.service.ANZFX;

public class MainTest {

	public static void main(String[] args) throws Exception {
		ClientCacheVO cacheVO = ClientCache.getClientCache();
		ANZFX anzfx = new ANZFX();

		anzfx.getTrade(cacheVO, "TRADE11");
		// executeTrade(cacheVO,new String[] {"TRADE11","BSTUSD","150","200","S"});
	}

}
