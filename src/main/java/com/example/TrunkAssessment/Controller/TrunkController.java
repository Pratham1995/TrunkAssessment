package com.example.TrunkAssessment.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.apache.el.parser.ParseException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.TrunkAssessment.Common.BaseServiceResponse;
import com.example.TrunkAssessment.types.PostWrapper;
import com.example.TrunkAssessment.types.Posts;
import com.example.TrunkAssessment.types.ResultWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class TrunkController {
	@Value("${trunk.endpoint}")
	String endpoint;
	
	@Resource(name="logger")
	Logger log;
	
	@RequestMapping(value="api/ping",method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<BaseServiceResponse> getPosts(@RequestParam String tag) throws ParseException, JsonMappingException, JsonProcessingException{
		CloseableHttpClient cli=HttpClientBuilder.create().build();
		BaseServiceResponse response=new BaseServiceResponse();
		ResultWrapper retVal=new ResultWrapper();
		String dest=endpoint;
		HttpGet get=new HttpGet(dest+"?"+"tag="+tag);
		HttpResponse res=null;
		String sanitizedJson=null;
		HttpEntity entity=null;
		try {
			res=cli.execute(get);
		}catch(IOException e) {
			e.printStackTrace();
			log.error("Error in getPostsController()"+e.getMessage());
		}
		if(res!=null) {
			entity=res.getEntity();
		}
		String responseString=null;
		try {
			responseString=EntityUtils.toString(entity, "UTF-8");
			sanitizedJson=com.google.json.JsonSanitizer.sanitize(responseString);
		}catch(IOException e) {
			e.printStackTrace();
			log.error("Error in getPostsController()"+e.getMessage());
		}finally {
			safeClose(cli);
		}
		ObjectMapper mapper=new ObjectMapper();
		mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		List<Posts> retId=mapper.readValue(sanitizedJson, new TypeReference<List<Posts>>() {});
		//List<Posts> list=new ArrayList<>();
		for(Posts P:retId) {
			PostWrapper f=new PostWrapper();
			f.posts=P.posts;
			retVal.posts.add(f);
			response.setResponseData(retVal);
			response.setSuccess(true);
			response.setCode(200);
		}
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@RequestMapping(value="api/posts",method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<BaseServiceResponse> getListPosts(@RequestParam List<String> tags,@RequestParam String sortBy) throws Exception{
		ResultWrapper retVal=new ResultWrapper();
		BaseServiceResponse response=new BaseServiceResponse();
		if(!sortBy.equals("asc")&&!sortBy.equals("desc")) {
			response.setCode(400);
			response.setResponseData( "sortBy parameter is invalid");
			response.setSuccess(false);
			return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
		}
		if(tags.isEmpty()) {
			response.setCode(400);
			response.setResponseData( "Tags parameter is required");
			response.setSuccess(false);
			return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
//			result.errors.add("Tags parameter is required");
		}
		for(String t:tags) {
			CloseableHttpClient cli=HttpClientBuilder.create().build();
			
			String dest=endpoint;
			HttpGet get=new HttpGet(dest+"?"+"tag="+t);
			HttpResponse res=null;
			String sanitizedJson=null;
			HttpEntity entity=null;
			try {
				res=cli.execute(get);
			}catch(IOException e) {
				e.printStackTrace();
				log.error("Error in getPostsController()"+e.getMessage());
			}
			if(res!=null) {
				entity=res.getEntity();
			}
			String responseString=null;
			try {
				responseString=EntityUtils.toString(entity, "UTF-8");
				sanitizedJson=com.google.json.JsonSanitizer.sanitize(responseString);
			}catch(IOException e) {
				e.printStackTrace();
				log.error("Error in getPostsController()"+e.getMessage());
			}finally {
				safeClose(cli);
			}
			ObjectMapper mapper=new ObjectMapper();
			mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			List<Posts> retId=mapper.readValue(sanitizedJson, new TypeReference<List<Posts>>() {});
			List<Posts> list=new ArrayList<>();
			for(Posts P:retId) {
				PostWrapper f=new PostWrapper();
				f.posts=P.posts;
				if(sortBy.equals("asc")) {
					retVal.posts.add(f);
					response.setResponseData(retVal);
					response.setSuccess(true);
					response.setCode(200);
				}else if(sortBy.equals("desc")){
					retVal.posts.add(f);
					Collections.sort(retVal.posts, Collections.reverseOrder());
					response.setResponseData(retVal);
					response.setSuccess(true);
					response.setCode(200);
				}
			}
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
		return null;
	}
	
	
	
	private void safeClose(CloseableHttpClient cli) {
		if(cli!=null) {
			try {
				cli.close();
			}catch(IOException e) {
				log.error("Error in TrunkController class funtion safeClose() Closeable is not close properly"+ e.getMessage());
				
			}
		}
		
	}
	

}
