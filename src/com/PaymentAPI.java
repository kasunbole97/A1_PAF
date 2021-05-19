package com;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Payment;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@WebServlet("/PaymentAPI")
public class PaymentAPI extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Payment PaymentObj = new Payment();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		String output = PaymentObj.insertPayment(request.getParameter("PaymentID"),
				request.getParameter("paymentcode"),
				request.getParameter("paymentType"),
				request.getParameter("researchercode"),
				request.getParameter("amount"));
		response.getWriter().write(output);
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		Map<String, String> paras = getParasMap(request);
		String output = PaymentObj.updatePayment(paras.get("PaymentID").toString(),
				paras.get("paymentcode").toString(),
				paras.get("paymentType").toString(),
				paras.get("researchercodec").toString(),
				paras.get("amount").toString());
		response.getWriter().write(output);
	} 

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		Map<String, String> paras = getParasMap(request);
		String output = PaymentObj.deletePayment(paras.get("PaymentID").toString());
		response.getWriter().write(output);
	}

	private static Map<String, String> getParasMap(HttpServletRequest request)
	{
		Map<String, String> map = new HashMap<String, String>();
		try
		{
			Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
			String queryString = scanner.hasNext() ?
					scanner.useDelimiter("\\A").next() : "";
					scanner.close();
					String[] params = queryString.split("&");
					for (String param : params)
					{ 
						String[] p = param.split("=");
						map.put(p[0], p[1]);
					}
		}
		catch (Exception e)
		{
		}
		return map;
	}

}
