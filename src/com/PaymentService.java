package com;
import model.Payment;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

//For JSON
import com.google.gson.*;

//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Payments")
public class PaymentService {

	Payment PaymentObj = new Payment();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readPayments()
	{
		return PaymentObj.readpayments();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertPayment(@FormParam("paymentcode") String paymentcode,
			@FormParam("paymentType") String paymentType,
			@FormParam("researchercode") String researchercode,
			@FormParam("buyerCode") String buyerCode,
			@FormParam("amount") String amount,
			@FormParam("createdDate") String createdDate)
	{
		String output = PaymentObj.insertpayment(paymentcode, paymentType, researchercode, buyerCode, amount, createdDate);
		return output;
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updatePayment(String PaymentData)
	{
		//Convert the input string to a JSON object
		JsonObject PaymentObject = new JsonParser().parse(PaymentData).getAsJsonObject();
		//Read the values from the JSON object
		String ID = PaymentObject.get("PaymentID").getAsString();
		String paymentcode = PaymentObject.get("paymentcode").getAsString();
		String paymentType = PaymentObject.get("paymentType").getAsString();
		String researchercode = PaymentObject.get("researchercode").getAsString();
		String buyerCode = PaymentObject.get("buyerCode").getAsString();
		String amount = PaymentObject.get("amount").getAsString();
		String createdDate = PaymentObject.get("createdDate").getAsString();
		String output = PaymentObj.updatepayment(ID, paymentcode, paymentType, researchercode, buyerCode, amount, createdDate);
		return output;
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deletePayment(String PaymentData)
	{
		//Convert the input string to an XML document
		Document doc = Jsoup.parse(PaymentData, "", Parser.xmlParser());

		//Read the value from the element <PaymentID>
		String PaymentID = doc.select("PaymentID").text();
		String output = PaymentObj.deletepayment(PaymentID);
		return output;
	}




}
