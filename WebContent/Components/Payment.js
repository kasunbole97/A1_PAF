$(document).ready(function()
		{
	if ($("#alertSuccess").text().trim() == "" || $("#alertSuccess").text().trim() == null)
	{
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
		
	$("#researchid").val("");
	$("#formResearch")[0].reset();
		});

//SAVE ============================================
$(document).on("click", "#btnSave", function(event)
		{ debugger;
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	// Form validation-------------------
	var status = validatepaymentForm();
	if (status != true)
	{
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	// If valid------------------------
	var type = ($("#paymentid").val() == "") ? "POST" : "PUT";
	$.ajax(
			{
				url : "PaymentAPI",
				type : type,
				data : $("#formResearch").serialize(),
				dataType : "text",
				complete : function(response, status)
				{
					onpaymentSaveComplete(response.responseText, status);
				}
			});
		});

//UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event)
		{
	$("#paymentcode").val($(this).data("paymentcode")); 
	$("#paymenttype").val($(this).closest("tr").find('td:eq(0)').text());
	$("#researchercode").val($(this).closest("tr").find('td:eq(1)').text());
	$("#buyercode").val($(this).closest("tr").find('td:eq(2)').text());
	$("#amount").val($(this).closest("tr").find('td:eq(3)').text());
	$("#createddate").val($(this).closest("tr").find('td:eq(4)').text());
		});

$(document).on("click", ".btnRemove", function(event)
		{
	$.ajax(
			{
				url : "PaymentAPI",
				type : "DELETE",
				data : "paymentCode=" + $(this).data("paymentcode"),
				dataType : "text",
				complete : function(response, status)
				{
					onpaymentDeleteComplete(response.responseText, status);
				}
			});
		});


//CLIENT-MODEL================================================================
function validateresearchForm()
{
//	paymentCode
	if ($("#paymentCode").val().trim() == "")
	{
		return "Insert payment Code.";
	}
//	paymentType
	if ($("#paymentType").val().trim() == "")
	{
		return "Insert payment Type.";
	} 
//	researcherCode
	if ($("#researcherCode").val().trim() == "")
	{
		return "Insert researcher Code.";
	} 

//	Amount-------------------------------
	if ($("#amount").val().trim() == "")
	{
		return "Insert amount.";
	}
	return true;
}

function onpaymentSaveComplete(response, status)
{
	if (status == "success")
	{
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divpaymentGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error")
		{
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error")
	{
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else
	{
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	} 
	$("#hidpaymentIDSave").val("");
//	$("#formpayment")[0].reset();
	window.location.reload();
}

function onpaymentDeleteComplete(response, status)
{
	if (status == "success")
	{
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divpaymentGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error")
		{
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error")
	{
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else
	{
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
	window.location.reload();
}

