package hyper.anz;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MainTest {

	public static void main(String[] args) throws Exception {
//		ClientCacheVO cacheVO = ClientCache.getClientCache();
//		ANZFX anzfx = new ANZFX();
		 SpringApplication.run(MainTest.class, args);
		// anzfx.getTrade(cacheVO, "TRADE20");
		//anzfx.executeTrade(cacheVO, new String[] {"TRADE20","USDCNH","2","100","B"});
		// "2330", "S" });
//		anzfx.getAllTrades(cacheVO);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			System.out.println("Let's inspect the beans provided by Spring Boot:");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}

		};
	}

}
