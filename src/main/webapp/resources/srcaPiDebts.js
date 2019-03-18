'use strict';

var taskId, taskClaimId; 
var RECOVERY_AMOUNT_MAX = 999999.99;
var RECOVERY_AMOUNT_MIN = 1.00;
$(function(){
	
	taskId = $('#taskId').val();
	taskClaimId = $('#taskClaimId').val();
	
	$('#edit-button').click(function(event) {
		event.preventDefault();
		edit();
	});
	
	$('#cancel-button').click(function(event) {
		event.preventDefault();
		cancel();
	});
	
	$('#debtType').bind('input', function() {
		showHideAddLink();
	})
	
	$('#recoveryAmount').bind('input', function() {
		showHideAddLink();
	})
	
	$('#add').click(function(){
		var debtType= $('#debtType').val();
		var recoveryAmount= $('#recoveryAmount').val();
		if(debtType && recoveryAmount){
			addDebt($('#debts-box'));
			updateTotalAmount(recoveryAmount);
			
		}else{
			return false;
		}
	})
	
	   $('#recoveryAmount').blur(function(){
		   var element= $(this);
		   showHideValidationMessage(element);
		   var value= element.val();
		   if(value){
			   var num = parseFloat(element.val());
		        var cleanNum = num.toFixed(2);
		        element.val(cleanNum);	   
		        validateonAdd(element);
		   }  
	       
	        //^\d{0,6}(\.\d{1,2})?$
	    });
	
	$('input[name="debtFlag"]:radio').change(function(){
		var value= $(this).val();
		if (value == 'N' || value == 'U' ) {
			$("#debts-box").find("tr:gt(0)").remove();
			$('#totalAmount').text('');
		}
	})
	
	$('.remove-debt').click(function(event){
		event.preventDefault();
		remove($(this));
	})
	
	$('#receivedDate').change(function(){
		showHideDebts();
	})
	$("input[name='debtFlag']").change(function(event) {
		showHideDebtDetails();
	})
	$("input[name='dmuFlag']").change(function(event) {
		var value= $(this).val();
		if (value == 'N'  ) {
			$("#debts-box").find("tr:gt(0)").remove();
			$('#totalAmount').text('');
			$('#trim').val('true');	
		}if( value == 'Y'){
			$('#receivedDate').val('');
			showHideTrimLinks();
			showHideOnDmu();
		}

	
		
		showHideOnDmu();
		clearDmuData();
	})
	
	$('#requestDmuButtonId').click(function(event){
		event.preventDefault();
		var element= $(this);
		var caseId = $('#caseId').val();
		ajaxGenerateDoc(element, caseId);
	})
	pageOnLoad();
	showHideAddLink();
	setDmuFlagDefaultValue();
	hideShowOnView();
	showHideTrimLinks();
});


function showHideTrimLinks(){
	var trim= $('#trim').val();
	if(trim == 'false'){
		$('#trimBox').find('button').hide();
		$('#trimBox').find('.generationStatus').hide();
		$('#trimBox').find('div[id="generated"]').show()
	}else if(trim == 'true'){
		$('#trimBox').find('button').show();
		$('#trimBox').find('.generationStatus').hide();
		$('#trimBox').find('div[id="generated"]').hide();
	}
	
	
}
function hideShowOnView(){
	var dmuFlag= $('#dmu');
	if(dmuFlag){
		var value= dmuFlag.val();
		if(value == 'No'){
			$('#dmubox').hide();
		}
	}
}

function ajaxGenerateDoc(element, caseId ){
	var param ="caseId="+caseId+"&taskId="+taskId+"&taskClaimId="+taskClaimId;
	var debtsForm= $('debtsForm');
	var statusDiv = element.parent().parent().find('.generationStatus');
	statusDiv.html("<span class='label-text'><strong>Document generation in progress</strong></span>");
	
	statusDiv.show();
	$.ajax({
	    url : "debtsRequestClearance.html",
	    type: "POST",
	    data : {
	    	caseId : caseId, 
	    	taskId : taskId,
	    	taskClaimId : taskClaimId
		},
	    success: function(returnData, textStatus, jqXHR)
	    {
	    	if (returnData.length > 0) {
				var trimRef = returnData.split("|", 1)[0];				
				var fileName = returnData.substring(trimRef.length+1);	
				
				var trimInput=$('#requestTrimReference');
				trimInput.val(trimRef); 
				var trimfile= $('#requestDocumentName');
				trimfile.val(fileName);
				showGeneratedDocument(element);
			} else {
				showFailedMessage(element);		
				element.prop("disabled", false);
			}
	    },
	    error: function (jqXHR, textStatus, errorThrown)
	    {
	    	showFailedMessage(element);
	    	element.prop("disabled", false);
	    }
	});		
	
}
function showFailedMessage(element) {	
	var statusDiv = element.parent().find('.generationStatus');
	statusDiv.html("<span class='label-text'><strong>Document generation failed, please retry</strong></span>");	
	statusDiv.show();
}

function showGeneratedDocument(element ) {
	var documentName = $('#requestDocumentName').val();
	var trimReference = $('#requestTrimReference').val();
	if (trimReference.length > 0) {
		
		var trimLink = 'trim://'+trimReference+'/?view';
		var parentDiv = element.parent('div').parent('div');
		var statusDiv = parentDiv.find('.generationStatus');
		statusDiv.html("<span class='label-text'><strong>Generated:</strong></span>&nbsp;");
		var hyperlinkName= "Debts request document"
		statusDiv.append($('<a target="_blank" />').attr('href', trimLink).html(hyperlinkName));
		statusDiv.show();
		element.hide();
	}
}

function setDebtFlag(){
	var dmuFlag= getRadioValue('dmuFlag');
	if(dmuFlag == 'Y'){
		$("#allYes").prop('checked', true);
	}
}


function setDmuFlagDefaultValue(){

	showHideOnDmu();
	makeDmuReadonly();
}
function makeDmuReadonly(){
	var dmuFlag= getRadioValue('dmuFlag');	//dmu flag	
	var hasDebtFlag= $('#hasDebtFlag').val(); //ws variable 

	if((hasDebtFlag == 'true') && (dmuFlag == 'Y')){
		$("#dmuYes").prop('checked', true);
		$("input[name='dmuFlag']").readonly(true);
	}else if(dmuFlag == 'N'){
		$("input[name='dmuFlag']").readonly(false);
	}
}

function showHideOnDmu(){
	var dmuFlag= getRadioValue('dmuFlag');
	if(dmuFlag == 'Y'){
		$('#showAll').show();
		showHideDebts();
	}else{
		$('#showAll').hide();
	}
}

function showHideDebts(){	
	var receivedDate=$('#receivedDate').val();
	
	if(receivedDate){
		var debtFlag= getRadioValue('debtFlag');
		if(!debtFlag){
			$("#allYes").prop('checked', true);
		}
		$('#debtFlagDiv').show();
	}else{
		$('#debtFlagDiv').hide();
		$("#allYes").prop('checked', false);
		
	}
	showHideDebtDetails();
}

function showHideDebtDetails(){
	var debtFlag= getRadioValue('debtFlag');
	
	if(debtFlag == 'Y'){
		$('#showDebts').show();
	}else{
		$('#showDebts').hide();
	}
}

function clearDmuData(){
	var dmu= getRadioValue('dmuFlag');
	if (dmu == 'N'  ) {
		$("#sentDate").val('');
		$("#receivedDate").val('');
	}
	clearDebtsData();
}

function clearDebtsData(){
	var debts= getRadioValue('debtFlag'); 
	if (debts == 'N'  ) {
		$("#debts-box").find("tr:gt(0)").remove();
		$('#totalAmount').text('');
	}
}


function updateTotalAmount(recoveryAmount){
	var totalAmount= $('#totalAmount');
	var amount= $('#totalAmount').text().replace('$', '').replace(/,/g, '');
	if(amount == ""){
		amount=0;
	} 
	amount= parseFloat(amount) + parseFloat(recoveryAmount);
	totalAmount.text(amount.toFixed(2));
}



function validateonAdd(element){
	var amount= element.val();
	 if((amount > RECOVERY_AMOUNT_MAX) || (amount < RECOVERY_AMOUNT_MIN)){
       	var message = 'The minimum value is $1 and the maximum is $999,999.99';
 		element.parent().after($('<div class="alert-danger"/>').html(message));
 		element.addClass('errorblock');
 		element.val('');
 		showHideAddLink();
     }
     
}

function showHideValidationMessage(element){
	var alertMessage= $('.alert-danger');
	   if(alertMessage){
		   alertMessage.remove();
		   element.removeClass('errorblock');
	   }
}
function showHideAddLink(){
	var debtType= $('#debtType').val();
	var recoveryAmount= $('#recoveryAmount').val();
	if(debtType && recoveryAmount){
		$('#add').show();
	}else{
		$('#add').hide();
	}
}

function pageOnLoad(){
    //Hide the first Child
	var container= $('#debts-box');
    container.find('tr.debt:first').addClass('hidden');
    //sort index
    ipsSortTableIndex();

}

function addDebt(container){
   //clone the first child, set the value for hidden input and span
	var hiddenRow= container.find('tr.debt:first');
	var clonedRow= cloneFirstElement(hiddenRow);
	updateValues(clonedRow);	
	clonedRow.removeClass('hidden');
	container.append(clonedRow);	
	pageOnLoad();
}

function updateValues(clonedRow){
	var debtType= $('#debtType').val();
	var recoveryAmount= $('#recoveryAmount').val();
	$('#debtType').val('');
	$('#recoveryAmount').val('');
	var debtTypeInput= clonedRow.children('input[name*=debtType]');
	var recoveryAmountInput= clonedRow.children('input[name*=recoveryAmount]');
	
	debtTypeInput.val(debtType);
	recoveryAmountInput.val(recoveryAmount);
	
	
	var tds= clonedRow.children('td');
	
	tds.each(function(index){
		var element= $(this);
		if(index == 0){
			var spn= element.children('span');
			if(spn){
				spn.text(debtType);
			}
		}else if(index == 1){
			element.text(recoveryAmount);
		}
		
	})
	
}



function cloneFirstElement(element){
	var clonedElement= element.clone(true);	
	return clonedElement;
}

function remove(element){
	var parentTr= element.closest('tr');
	var recovery= parentTr.find('input[name*="recoveryAmount"]').val();
	subtractTotalAmount(recovery)
   parentTr.remove();
   pageOnLoad();
} 

function subtractTotalAmount(recoveryAmount){
	var totalAmount= $('#totalAmount');
	var amount= $('#totalAmount').text().replace('$', '').replace(/,/g, '');
	
	amount= parseFloat(amount) - parseFloat(recoveryAmount);
	totalAmount.text(amount.toFixed(2));
}

function ipsSortTableIndex(){
	$('tbody.debts-box').each(function(){
		var index = 0;
		var container = $(this);
		container.find('tr.debt').each(function(){
			ipsSortIndexContainer($(this), index++);
		});
	});
}

function ipsSortIndexContainer(div, index){
	var attributesWithIndex = ['href', 'for', 'id'];
	$.each(attributesWithIndex, function( ind, attr ) {
		var selector = "["+attr+"*='_']";
		div.find(selector).each(function(){
			var element = $(this);
			element.attr(attr, getNewAttributeWithIndex1(element.attr(attr), index));
		});
	});
	attributesWithIndex = ['name'];
	$.each(attributesWithIndex, function( ind, attr ) {
		var selector = "["+attr+"*='[']";
		div.find(selector).each(function(){
			var element = $(this);
			element.attr(attr, getNewAttributeWithIndex2(element.attr(attr), index));
		});
	});
}

function getNewAttributeWithIndex1(value, index){
	var lastIndex = value.lastIndexOf('_');
	var result = value.slice(0, lastIndex+1) + index;
	return result;
}

function getNewAttributeWithIndex2(value, index){
	var leftIndex = value.lastIndexOf('[');
	var rightIndex = value.lastIndexOf(']');
	var result = value.slice(0, leftIndex+1) + index + value.slice(rightIndex);
	return result;
}


function edit(){
	var caseId = $('#caseId').val();
	$(location).attr('href', 'srcaPiDebtsEdit.html?taskId='+taskId+'&taskClaimId='+taskClaimId
			+ '&caseId='+ caseId);
}

function cancel(){
	var caseId = $('#caseId').val();
	$(location).attr('href', 'srcaPiDebtsView.html?taskId='+taskId+'&taskClaimId='+taskClaimId+ '&caseId='+ caseId);
}


