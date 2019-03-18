'use strict';

var taskId, taskClaimId, caseId;


$(function(){
	var page = new Page();
	page.init();
});

function Page(){
	
}

Page.prototype.init = function(){
	var that = this;
	that.onPageLoad();
	that.bindButtons();
}


Page.prototype.onPageLoad = function(){
	setCommonVars();
	showHideEmailToBox();
	showHideUpdateRecipient();
	
	
	displayTrimLinkAndRemoveLink($('.trim-reference').parent());
		
}

Page.prototype.bindButtons = function(){
	var that=this;
	
	$('.upload-attachment1').click(function(event){
		event.preventDefault();
		popupAttachmentWindow1($(this));
	});
		
	$(document).on('click', '.remove-attachment', function(event){
		event.preventDefault();
		removeAttachment($(this));
	});
	
	
	$('#showAssessmentPassword').click(function(event){
		event.preventDefault();
		showAssessmentPassword(false);
	});
	
	$('#resetAssessmentPassword').click(function(event){
		event.preventDefault();
		showAssessmentPassword(true);
	});
	
	$('#hideAssessmentPassword').click(function(event){
		event.preventDefault();
		$('#passwordSpan').html("********");	
		$('#showAssessmentPasswordDiv').show();
		$('#hideAssessmentPasswordDiv').hide();
		
	});
	
	
	$('#editButtonId').click(
			function(event) {
				event.preventDefault();
				$(location).attr(
						'href',
						'editCommunication.html' + that.getCommonUrl());
	});
	
	$('#createCommunication').click(
			function(event) {
				event.preventDefault();
				$(location).attr(
						'href',
						'createCommunication.html' + that.getCommonUrl());
	});
	
	$('#cancelCommunicate').click(
			function(event) {
				event.preventDefault();
				var recipientId= $('#recipientId').val()
				$(location).attr(
						'href',
						'viewCommunication.html' + that.getCommonUrl()+ "&recipientId=" +recipientId);
	});
	
	
	$('#resendRequest').click(function(event){
		event.preventDefault();
		$(location).attr(
				'href',
				'emailCommunication.html'+ that.getCommonUrl()+ "&uploadFlag=N" );
	});
	
	$('#resend').click(function(event){
		event.preventDefault();
		$(location).attr(
				'href',
				'emailCommunication.html'+ that.getCommonUrl()+ "&uploadFlag=Y" );
	});
	
	
	
	
	$('#addNewRecipient').click(function(event){
		event.preventDefault();
		$("#layoutMode").val('ADD_NEW_RECIPIENT');
		$("#communicationFormId").attr("action", "addRecipient.html");
		$("#communicationFormId").submit();
		
	});
	
	
	$("#communicate").click(function () {
		event.preventDefault();
		$("#draftFlag").val('N');
		$("#communicationFormId").attr("action", "saveCommunication.html");
		$("#communicationFormId").submit();
	});
	
	$("#saveDraft").click(function () {
		event.preventDefault();
		
		$("#draftFlag").val('Y');
		$("#communicationFormId").attr("action", "saveCommunication.html");
		$("#communicationFormId").submit();
	});
	
	$("#updateRecipient").click(function () {
		event.preventDefault();
		$("#layoutMode").val('UPDATE_RECIPIENT');
		$("#communicationFormId").attr("action", "updateRecipient.html");
		$("#communicationFormId").submit();
	});
	
	$('#cancelUpdateRecipient').click(function(event){
		event.preventDefault();
		$("#layoutMode").val('CANCEL_RECIPIENT');
		$("#communicationFormId").attr("action", "addRecipient.html" + that.getCommonUrl());
		$("#communicationFormId").submit();
	});
	
	$("#recipientName").ishautocomplete({
		lookup : function(query, done) {					
			ISHRC.COMMUNICATION.searchRecipientByName(query, done);
		},
		
		onSelect : function(suggestion) {
			var done = function(recipient){
				$("#searchRecipientInfo")
					.empty()
					.append(recipient.displayRecipientDetails);
				
				$("#encryptedRecipientId").val(recipient.encryptedRecipientId);
				/*$("#hiddenRecipientId").val(recipient.id);*/
				

			};
			ISHRC.COMMUNICATION.searchRecipientDetailsById(suggestion.data, done);
			showHideEmailToBox();
			showHideUpdateRecipient();
		}
	});
	
	/*$("#providerNameUpdate").ishautocomplete({
		lookup : function(query, done) {					
			ISHRC.COMMUNICATION.searchRecipientByName(query, done);
		},
		
		onSelect : function(suggestion) {
			var done = function(recipient){
				$("#hiddenRecipientId").val(recipient.id);
				$("#layoutMode").val('UPDATE_RECIPIENT');
				$("#communicationFormId").attr("action", "updateRecipient.html");
				$("#communicationFormId").submit();
			};
			ISHRC.COMMUNICATION.searchRecipientDetailsById(suggestion.data, done);				
		}
	});*/
	
	
	
};


function showAssessmentPassword(newPassword){
	var taskId =  $('#taskId').val();
	var taskClaimId =  $('#taskClaimId').val();
	var caseId =  $('#caseId').val();
	//var rehabAssessmentId =  $('#encryptAssessmentId').val();
		
	
	var url = $('#showAssessmentPasswordUrl').val();
	 $.post(url, {
		 taskId : taskId,
		 taskClaimId : taskClaimId,
		 caseId : caseId,		 
		// rehabAssessmentId : rehabAssessmentId, 	
		 newPassword : newPassword
		}, function(data) {		
			$('#passwordSpan').html(data);	
			$('#resetAssessmentPassword').html("Reset password");
			if($('#showAssessmentPassword').length > 0){
				$('#showAssessmentPasswordDiv').hide();
				$('#hideAssessmentPasswordDiv').show();
			}
	});	

}

function popupAttachmentWindow1(element){
	var check = window.showModalDialog;
	var windowName= 'Upload / link document';
	var claimId = $('#claimId').val();
	var url = 'reviewUploadDocumentEdit.html?claimId='+claimId;
	var uin = $('#uin').val();
	if ($.trim(uin).length){
		url += '&uin='+$.trim(uin);
	}
	var documentType = element.parent().find('input.document-type').val();
	if ($.trim(documentType).length){
		url += '&type='+$.trim(documentType);
	}
	var returnValue;
	if (check) {
		//IE and firefox
		returnValue = window.showModalDialog(url, window.self, "dialogWidth:1200px;dialogHeight:690px;scroll:yes;resizable:no;center:yes");
	} else {
		//Chrome, don't support upload attachment
		window.open(url, windowName, 'left=((screen.width) ? (screen.width-w)/2 : 0), top=dialogTop:((screen.height) ? (screen.height-h)/2 : 0), width=1000,height=690,scrollbars=yes,menubar=no,resizable=no,toolbar=no,location=no');
	}
	
	if (returnValue){
		//Refer to attachmentPopupClose.jsp
		var parentDiv= element.parent();		
		//specific to multiple attachments
		var trimReferenceValue= returnValue[0];
		var documentNameValue= returnValue[1];		
	//	replaceDuplicateName(parentDiv, documentNameValue);  //order of this method call matters
		cloneElements(parentDiv, trimReferenceValue, documentNameValue);
		 resetIndex();
		displayTrimLinkAndRemoveLink(parentDiv);
	}
}

function resetIndex(){
	var parentDiv= $('.upload-attachment').parent();
	var index= getIndex(parentDiv);

	var index = 0;
	parentDiv.find('input.trim-reference').each(function(){
	
		if(!$(this).val()){
			var id= $(this).attr('index');
			var docId= 'evidences['+ id + '].documentName';
			var typeId= 'evidences['+ id + '].type';
			parentDiv.find('input[name="'+docId+ '"]').remove();
			parentDiv.find('input[name="'+typeId+ '"]').remove();
			$(this).remove();
		}else{
			var trimAttribute= 'evidences['+index+'].trimReference';  
			$(this).attr('index', index).attr('name', trimAttribute).attr('id', trimAttribute); 
			index= index + 1 ;
		}
	})	
	
	index=0;
	parentDiv.find('input.document-name').each(function(){ 
			
			var nameAttribute= 'evidences['+index+'].documentName'; 
			$(this).attr('index', index).attr('name', nameAttribute).attr('id', nameAttribute);;
			index= index + 1 ;
		
	})
	
	index=0;
	parentDiv.find('input.document-type').each(function(){ 
		
		
			var typeAttribute= 'evidences['+index+'].type'; 
			$(this).attr('index', index).attr('name', typeAttribute).attr('id', typeAttribute);;
			index= index + 1 ;
		
	})
	
	index=0;
	parentDiv.find('a.trim-link').each(function(){ 
		$(this).attr('index', index);
		index= index + 1 ;
		
	})
	
	index=0;
	parentDiv.find('a.remove-attachment').each(function(){ 
		$(this).attr('href', index);
		index= index + 1 ;
	})
	
}

function cloneElements(parentDiv, trimReferenceValue, documentNameValue){
	var index= getIndex(parentDiv);
	var trimReference= parentDiv.find('input.trim-reference[index="0"]');
	var documentName= parentDiv.find('input.document-name[index="0"]');
	var documentType= parentDiv.find('input.document-type[index="0"]');
	
	trimReference.attr('latestIdx', index);
				
	parentDiv.append(cloneElement(trimReference, 'evidences['+ index + '].trimReference', index, trimReferenceValue));
	parentDiv.append(cloneElement(documentName, 'evidences['+ index + '].documentName', index, documentNameValue));
	parentDiv.append(cloneElement(documentType, 'evidences['+ index + '].type', index, documentType.val()));
	
}

function cloneElement(element, attributeName, index, value){
	var clonedElement= element.clone(true).attr(
			{
				'name': attributeName,
				'id' : attributeName,
				'index' : index
				
			}				
	).val(value);	
	return clonedElement;
}

function replaceDuplicateName(parentDiv, documentNameValue){
	var count = getIndex(parentDiv);
	for (var i = 0; i < count; i++) { 
		var existingDocReference= parentDiv.find('input.document-name[index='+i +']');   //evidences[3].documentName
		var existingDocumentReferenceValue= existingDocReference.val();
		if(documentNameValue == existingDocumentReferenceValue){
			var deleteLink= parentDiv.find('a[href='+i+']');
			deleteLink.click();
			deleteLink.remove();
		}	
	}	
}

function getIndex(parentDiv){
	var index= 0;
	parentDiv.find('input.trim-reference').each(function(){
		index= index + 1;
	})
	return index;
}

function getAttachmentsNumber(parentDiv){
	var index= 0;
	var trimLinks= parentDiv.find('a.trim-link')
	
	trimLinks.each(function(){
			index= index + 1;
		
	})
	return index;
}

function displayTrimLinkAndRemoveLink(element){
	if(element){
		element.find('.trim-link, .remove-attachment').remove(); //remove all links first
		var trimInputElements = element.find('input.trim-reference');
	
			
	trimInputElements.each(function(){
			var id= $(this).attr('name');
			var index= id.substr(10,1) //evidences[0].trimReference
			var evidenceNameAttribute= 'evidences['+ index + '].trimReference';
			var documentNameAttribute= 'evidences['+ index + '].documentName';
			var inputTrimReference= $('input[name="'+evidenceNameAttribute+'"]');
			var inputDocumentName= $('input[name="'+documentNameAttribute+'"]');
			var trimReference= inputTrimReference.val();
			inputTrimReference.attr('tid', trimReference);
			inputDocumentName.attr('tid', trimReference);
			var documentName= inputDocumentName.val();
			$(this).attr('tid', trimReference);
			if ($.trim(trimReference).length > 0){
				var trimLink = 'trim://'+trimReference+'/?view';
				element.append($('<a class="trim-link" target="_blank" />').attr('href', trimLink).attr('tid', trimReference).attr('index', index).html(documentName));
				element.append($('<a href="'+index+'" class="spc-md no-text-decoration remove-attachment" title="Remove this attachment"><span class="glyphicon glyphicon-remove-sign"></span></a>'));		
		
			}		
		});
	
		hideOrShowUploadLink();
	}
}
function hideOrShowUploadLink(){
	var uploadLink= $('a.upload-attachment');
	var parentDiv= uploadLink.parent();
	var totalAttachments = 10;
	var documentType = parentDiv.find('input.document-type').val();
	
	
	
	var currentAttachments = getAttachmentsNumber(parentDiv);
	if(currentAttachments == totalAttachments){
		uploadLink.hide();
	}else{
		uploadLink.show();
	}	
}


function removeAttachment(element){
	var index= element.attr('href');
	var attachmentDiv = element.parent();
	var trimLink= attachmentDiv.find('a[index='+index+']');
	var trimElement= attachmentDiv.find('input[index='+index+']');
	var indexAttribute= trimElement.attr('index');
	
	var documentElement= attachmentDiv.find('input[index='+index+']');
	var documentName= attachmentDiv.find('input.document-name[index='+indexAttribute+']');
	var type= attachmentDiv.find('input.document-type[index='+indexAttribute+']');
	var documentTypeValue= type.val();
	trimLink.val('');
	
	if(indexAttribute == 0){
		trimElement.val('');
		documentName.val('');
		type.val(documentTypeValue);
	}else{
		trimElement.remove();
		documentElement.remove();
		documentName.remove();
	}
	displayTrimLinkAndRemoveLink(attachmentDiv);
}


function clearDivHiddenFields(element){
	if (element.is(':hidden')){
		element.find('input, select, textarea').each(function(){
			if ($(this).is(":radio")){
				clearRadioButtonField($(this).attr('name'));
			}else if ($(this).is(":checkbox")){
				$(this).prop("checked", false);
			}else{
				var name = $(this).attr('name');
				var defaultValue = $(this).attr('data-default');
				if (defaultValue != null){
					$(this).val(defaultValue);
				}else{
					$(this).val('');
				}
			}
		});
	}
}



function setCommonVars() {
	taskId = $('#taskId').val();
	taskClaimId = $('#taskClaimId').val();

	var $caseId = $('#caseId');
	if ($caseId) {
		caseId = $('#caseId').val();
	}
}
function isAddLayout(){
	return  $("#layoutMode").val() =='ADD_NEW_RECIPIENT'
}

function isRecipientSelected(){
	var recipientId= $('#recipientId').val() != 0;
	
	var encryptedRecipientId= $('#encryptedRecipientId').val();
	var email=$("#destEmail").val();
	return encryptedRecipientId ||email  || recipientId;
}

function showHideUpdateRecipient() {
	
	if(isRecipientSelected()){
		 $('#updateRecipient').show();
		
	}else{
		$('#updateRecipient').hide();
	}
}


function showHideEmailToBox() {
		
	if( isRecipientSelected() || isAddLayout()){
		$('#emailExtraInfoBox').show();
	}else{
		$('#emailExtraInfoBox').hide();
	}
}

Page.prototype.cancel= function cancel(){
	var that= this;
	var caseId = $('#caseId').val();
	$(location).attr('href', 'view.html' + that.getCommonUrl());
}

Page.prototype.clearSelect = function(element){	
	element.prop("selected", false);
	element.val('');
}


Page.prototype.getCommonUrl = function(){
	var url = "?taskId=" + taskId + "&taskClaimId=" + taskClaimId;
	if(caseId){
		url += "&caseId=" +caseId;
	}
	return url;
}


var ISHRC = ISHRC || {};
ISHRC.COMMUNICATION = (function() {
	"use strict";
	
	var searchRecipientURL = "searchRecipients" ;
	var searchRecipientDetailsById = "searchRecipientDetails" ;
	
	return {
		searchRecipientByName : function(name, done) {
			if (name && name.length > 0) {
				var self = this;
			
				var taskId = $('#taskId').val();
				var taskClaimId = $('#taskClaimId').val();
				
				var requestData = {
					recipientName : name,
					taskId: taskId,
					taskClaimId: taskClaimId
				};

				var autoComplete = $.post({			
					'url' : searchRecipientURL ,
					'data' :  requestData,
					'dataType': 'json'
				});

				autoComplete.done(function(data) {
					ISHRC.Logger.log(data, true);

					var resonse = {
						suggestions : $.map(data, function(key, value) {
							return {
								value : key,
								data : value
							};
						})
					};

					done(resonse);
				});
			}
		},
		
		searchRecipientDetailsById: function(recipientId, done){
			var self = this;
			var taskClaimId = $("#taskClaimId").val();
			var taskId = $('#taskId').val();
			var requestData = {
				recipientId : recipientId,
				taskClaimId: taskClaimId,
				taskId: taskId
			};

			var searchProviderDetails = $.post({			
				'url' : searchRecipientDetailsById,
				'data' :  requestData,
				'dataType': 'json'
			});

			searchProviderDetails.done(function(data) {
				ISHRC.Logger.log(data, true);
				if(data.sameRecipient != true){
					$("#editSaveAssessment").attr("disabled","disabled");
				}else{
					$("#editSaveAssessment").removeAttr("disabled");
				}
				$("#recipientName").val('');
				$("#destEmail").val(data.destEmail);
				showHideEmailToBox();
				showHideUpdateRecipient();
				//$("#emailExtraInfoBox").show();
				done(data);
			});
		}
	
	
	}
})();

