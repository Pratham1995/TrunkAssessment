package com.example.TrunkAssessment.Controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.TrunkAssessment.Common.BaseServiceResponse;



@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TrunkControllerTest {
	
	@Autowired
	private TrunkController trunkController;
	
	@Mock
	Logger log;
	
	@Test
	public void testgetPosts() throws Exception {
		
		ResponseEntity<BaseServiceResponse>  res=trunkController.getPosts("tech");
		assertNotNull(res);
	}
	
	

	@Test
	public void testgetListPosts() throws Exception {
		List<String> list=new ArrayList<>();
		list.add("tech");
		ResponseEntity<BaseServiceResponse>  res=trunkController.getListPosts(list,"asc");
		assertNotNull(res);
	}
	
	@Test
	public void testgetListPost2s() throws Exception {
		List<String> list=new ArrayList<>();
		list.add("tech");
		ResponseEntity<BaseServiceResponse>  res=trunkController.getListPosts(list,"desc");
		assertNotNull(res);
	}
	
	@Test
	public void testgetListPosts3() throws Exception {
		List<String> list=new ArrayList<>();
		//list.add();
		ResponseEntity<BaseServiceResponse>  res=trunkController.getListPosts(list,"asc");
		assertNotNull(res);
	}
	@Test
	public void testgetListPosts4() throws Exception {
		List<String> list=new ArrayList<>();
		list.add("tech");
		ResponseEntity<BaseServiceResponse>  res=trunkController.getListPosts(list,"sdsdsdd");
		assertNotNull(res);
	}

}
