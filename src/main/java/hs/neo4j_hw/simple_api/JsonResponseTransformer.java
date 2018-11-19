package hs.neo4j_hw.simple_api;

import com.google.gson.Gson;
import spark.ResponseTransformer;

public class JsonResponseTransformer implements ResponseTransformer {

	private final Gson gson = new Gson();
	
	@Override
	public String render(Object model) throws Exception {
		 return gson.toJson(model);
	}

}
