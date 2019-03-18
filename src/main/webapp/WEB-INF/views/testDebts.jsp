<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<div id="showDebts">
		<div class="row">
			<div class="col-sm-9">
				<table>
					<thead>
						<tr>
							<th style="width: 80%;">Debt type</th>
							<th>Recovery amount</th>
							<th></th>
						</tr>
					</thead>

					<tbody id="debts-box" class="debts-box">
						<c:forEach items="${debt.debts}" var="debt" varStatus="status">

							<tr class="debt">
								<form:hidden path="debts[${status.index}].debtType" />
								<form:hidden path="debts[${status.index}].recoveryAmount" />
								<td><span class="bold"><c:out
											value="${debt.debtType}" /></span></td>
								<td><fmt:formatNumber type="currency" currencySymbol="$"
										value="${debt.recoveryAmount}" /></td>
								<td><a title="Remove this attachment"
									class="spc-md no-text-decoration remove-debt" href="0"> <span
										class="glyphicon glyphicon-remove-sign"></span>
								</a></td>
							</tr>
						</c:forEach>

					</tbody>
					<tbody>
						<tr>
							<td><input type="text" class="form-control" id="debtType"
								maxlength="100"></td>
							<td><input type="text" class="form-control numbers-only"
								id="recoveryAmount"></td>
							<td><a href="#" id="add">Add</td>
						</tr>

					</tbody>
				</table>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-7">
				<span class="bold">Total recoverable debt:</span>
			</div>
			<div class="col-sm-5">
				<span id="totalAmount" class="bold"><fmt:formatNumber
						type="currency" currencySymbol="$" value="${debt.totalDebtAmount}" /></span>
			</div>
		</div>
	</div>

</body>
</html>