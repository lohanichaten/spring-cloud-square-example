package learning.spring;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.square.retrofit.EnableRetrofitClients;
import org.springframework.cloud.square.retrofit.core.RetrofitClient;
import org.springframework.context.annotation.Bean;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

@SpringBootApplication
@EnableRetrofitClients
public class SquareExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SquareExampleApplication.class, args);
	}

	
	@Bean
	ApplicationRunner runner(GreetingClient client) {
			return event->{
				for(var i=0;i<5;i++) {
				 var call=	client.greet("Spring test"+i);
				 System.out.println("Result"+call.execute().body());
				}
			};
	}
	
	
	@Bean
	@LoadBalanced
	OkHttpClient.Builder okHttpClientBuilder(){
		return new OkHttpClient.Builder();
	}
	
}

@RetrofitClient(
		"greeting-service"
)
interface GreetingClient{
	
	@GET("/hello/{name}")
	Call<String> greet(@Path("name") String name);
}