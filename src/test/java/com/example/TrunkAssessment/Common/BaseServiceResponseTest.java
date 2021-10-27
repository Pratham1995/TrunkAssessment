package com.example.TrunkAssessment.Common;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import junit.framework.Assert;


@RunWith(MockitoJUnitRunner.Silent.class)
public class BaseServiceResponseTest {
	
	@InjectMocks
	private BaseServiceResponse bsr;

	@Test
	public void test() {
		
		bsr.setCode(1);
		assertEquals(1, bsr.getCode(),0.1);
		bsr.setDescription("Message");
		assertEquals("Message",bsr.getDescription());
		bsr.setRequestType("Request");
		assertEquals("Request",bsr.getRequestType());
		bsr.setResponseData("Message data");
		assertEquals("Message data",bsr.getResponseData());
		bsr.setSuccess(true);
		assertEquals(true,bsr.isSuccess());
		
	}

}
