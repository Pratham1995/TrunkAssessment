package com.example.TrunkAssessment.types;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.Silent.class)
public class PostTest {
	
	@InjectMocks
	private Posts posts;

	@Test
	public void test() {
		String post="Message";
		posts.setPosts(post);
		assertEquals("Message",posts.getPosts());
	}

}
