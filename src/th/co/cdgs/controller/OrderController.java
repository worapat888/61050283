package th.co.cdgs.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import th.co.cdgs.bean.CustomerDto;
import th.co.cdgs.bean.OrderDto;
import th.co.cdgs.bean.ProductDto;

@Path("orders")
public class OrderController {
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryOrderById(@PathParam("id") Long orderId) {
		List<OrderDto> list = new ArrayList<>();
		ResultSet rs = null;
		PreparedStatement pst = null;
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("select order_id,c.customer_id,CONCAT(first_name,' ',last_name) as\r\n" + 
					"full_name,p.product_id,product_name,price,amount,order_date,order_status\r\n" + 
					"from orders o\r\n" + 
					"left join product p on p.product_id = o.product_id\r\n" + 
					"left join customer c on c.customer_Id = o.customer_Id\r\n" + 
					"where order_id = ?");
			rs = pst.executeQuery();
			OrderDto orderDto = null;
			while (rs.next()) {
				orderDto = new OrderDto();
				CustomerDto customerDto = new CustomerDto();
				ProductDto productDto = new ProductDto ();
				orderDto.setOrderId(rs.getLong("order_Id"));
				customerDto.setCustomerId(rs.getLong("customer_Id"));
				customerDto.setFullName(rs.getString("full_name"));
				orderDto.setCustomer(customerDto);
				productDto.setProductId(rs.getLong("product_Id"));
				productDto.setProductName(rs.getString("product_name"));
				productDto.setPrice(rs.getBigDecimal("price"));
				orderDto.setProduct(productDto);
				orderDto.setAmount(rs.getLong("amount"));
				orderDto.setOrderDate(rs.getDate("order_date"));
				orderDto.setStatus(rs.getString("order_status"));
				list.add(orderDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (pst != null) {
				try {
					pst.close();
				} catch (SQLException e) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}

		}
		return Response.ok().entity(list).build();

	}
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createOrder(OrderDto order) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/workshop", "root", "p@ssw0rd");
			pst = conn.prepareStatement(
					"INSERT INTO orders (customer_id,product_id,amount,order_date,order_status)"+" VALUES (?,?,?,?,?)");
			int index = 1;
			pst.setLong(index++,order.getCustomer().getCustomerId());
			pst.setLong(index++,order.getProduct().getProductId());
			pst.setLong(index++, order.getAmount());
			pst.setDate(index++, order.getOrderDate());
			pst.setString(index++, order.getStatus());
			pst.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (pst != null) {
				try {
					pst.close();
				} catch (SQLException e) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return Response.status(Status.CREATED).entity(order).build();

	}
}
	


