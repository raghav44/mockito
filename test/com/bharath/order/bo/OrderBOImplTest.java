package com.bharath.order.bo;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import java.sql.SQLException;

import com.bharath.order.bo.exception.BOException;
import com.bharath.order.dao.OrderDAO;
import com.bharath.order.dto.Order;

public class OrderBOImplTest {
	
	@Mock
	OrderDAO dao;
	private OrderBOImpl bo;
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		bo = new OrderBOImpl();
		bo.setDao(dao);
	}
	@Test
	public void placeOrder_Should_Create_An_Order() throws SQLException, BOException {
		Order order = new Order();
		when(dao.create(any(Order.class))).thenReturn(new Integer(1));
		boolean placeOrder = bo.placeOrder(order);
        assertTrue(placeOrder);	
        verify(dao,atLeast(1)).create(order);
	}
	@Test
	public void placeOrder_ShouldNot_Create_An_Order() throws SQLException, BOException {
		OrderBOImpl bo = new OrderBOImpl();
		bo.setDao(dao);
		Order order = new Order();
		when(dao.create(order)).thenReturn(new Integer(0));
		boolean placeOrder = bo.placeOrder(order);
        assertFalse(placeOrder);	
        verify(dao).create(order);
	}
	
	
	@Test(expected=BOException.class)
	public void placeOrder_Should_Throw_BOException_Create_An_Order() throws SQLException, BOException {
		OrderBOImpl bo = new OrderBOImpl();
		bo.setDao(dao);
		Order order = new Order();
		when(dao.create(order)).thenThrow(SQLException.class);
		boolean result = bo.placeOrder(order);
	}
	
	@Test
	public void cancelOrder_Should_Cancel_Order() throws SQLException, BOException {
		Order order = new Order();
		when(dao.read(anyInt())).thenReturn(order);
		when(dao.update(order)).thenReturn(1);
		boolean cancelOrder = bo.cancelOrder(123);
		assertTrue(cancelOrder);
		verify(dao).read(123);
		verify(dao).update(order);
	}
	
	
	@Test
	public void cancelOrder_Should_Not_Cancel_Order() throws SQLException, BOException {
		Order order = new Order();
		when(dao.read(123)).thenReturn(order);
		when(dao.update(order)).thenReturn(0);
		boolean cancelOrder = bo.cancelOrder(123);
		assertFalse(cancelOrder);
		verify(dao).read(123);
		verify(dao).update(order);
	}
	
	
	@Test(expected=BOException.class)
	public void cancelOrder_Should_Throw_Exception() throws SQLException, BOException {
	//	Order order = new Order();
	//	when(dao.read(123)).thenThrow(BOException.class);
		when(dao.read(123)).thenThrow(SQLException.class);
	//	when(dao.update(order)).thenThrow(BOException.class);
		bo.cancelOrder(123);
	//	assertTrue(cancelOrder);
	//	verify(dao).read(123);
	//	verify(dao).update(order);
	}
	
	
	@Test(expected=BOException.class)
	public void cancelOrder_Should_Throw_BOException_OnUpdate() throws SQLException, BOException {
		Order order = new Order();
	//	when(dao.read(123)).thenThrow(BOException.class);
		when(dao.read(123)).thenReturn(order);
		when(dao.update(order)).thenThrow(SQLException.class);
	//	when(dao.update(order)).thenThrow(BOException.class);
		bo.cancelOrder(123);
	//	assertTrue(cancelOrder);
	//	verify(dao).read(123);
	//	verify(dao).update(order);
	}
	
}
