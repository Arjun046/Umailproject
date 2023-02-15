// uMail JS File for Client's Home Page

function logMeOut() {
	document.location.href='Logout?';
}

function selectAllMails() {
	var mailArr = document.querySelectorAll('#check');
	if(document.querySelector('#selectAll').checked){
		for(var i=0; i<mailArr.length; i++) {
			mailArr[i].checked = true;
		}
		setToolbarBtnState('inline');
	} else {
		for(var i=0; i<mailArr.length; i++) {
			mailArr[i].checked = false;
		}
		setToolbarBtnState('none');
	}
}

function showMailDetail(id) {
	document.querySelector('#mailsDiv').style.display = 'none';
	document.querySelector('#mailDetailDiv').style.display = 'block';
	
	var mailDetail = document.getElementsByName(id);
	
	document.getElementById('from').textContent = mailDetail[0].textContent;
	document.getElementById('to').textContent = mailDetail[1].textContent;
	document.getElementById('subject').textContent = mailDetail[2].textContent;
	document.getElementById('msgContent').value = mailDetail[4].value;
}

function hideMailDetail() {
	document.querySelector('#mailDetailDiv').style.display = 'none';
	document.querySelector('#mailsDiv').style.display = 'block';
}

function showComposeDiv() {
	document.querySelector('#composeMailRootDiv').style.display = '-webkit-box';
}

function closeComposeMail() {
	document.querySelector('#composeMailRootDiv').style.display = 'none';
	var fields = document.querySelectorAll('input[type="text"], input[type="email"]');
	for(var i=0; i<fields.length; i++) {
		fields[i].value = '';
	}
	document.querySelector('#composeContent').value = '';
}

function outerClicked(event){
	if(event.target == event.currentTarget)
		closeComposeMail();
}

// function enableToggleToolbarButton() {
	// var checks = document.querySelectorAll('input[name="check"]');
	// for(var i=0; i<checks.length; i++) {
		// checks[i].onchange = checkStateToDisplayToolbarButton;
	// }
// }

function checkStateToDisplayToolbarButton() {
	var checks = document.querySelectorAll('input[name="check"]');
	var i=0;
	var displayType = 'inline';
	for(i; i<checks.length; i++) {
		if(checks[i].checked) {
			break;
		}
	}
	if(i==checks.length) {
		displayType = 'none';
		document.querySelector('#selectAll').checked = false;
	}
	setToolbarBtnState(displayType);
}

function setToolbarBtnState(displayType) {
	var toolbarBtn = document.querySelectorAll('input[form="mailForm"]');
	for(var j=0; j<toolbarBtn.length; j++) {
		toolbarBtn[j].style.display = displayType;
	}
}

/* function enableOptionClick() {
	var options = document.querySelectorAll('li[name="option"]');
	for (var i=0; i<options.length; i++) {
		options[i].onclick = optionClicked(options[i]);
	}
} */

function optionClicked(option) {
	var options = document.querySelectorAll('li');
	for (var i=0; i<options.length; i++) {
		options[i].style.color = '#fd7214';
		options[i].style.fontWeight = 'normal';
	}
	var selected = document.querySelector('li[value=' +option+ ']');
	selected.style.color = 'black';
	selected.style.fontWeight = 'bold';
	document.getElementById('selectedOption').value = option;
	
	var request = new XMLHttpRequest();
	
	request.onreadystatechange = function (){
		if(request.readyState == 0 && request.status==200){
			document.getElementById('mailsDiv').innerHTML = '<center><h1>Connecting To Server</h1><center>';
		} else if(request.readyState == 1 && request.status==200 ){
			document.getElementById('mailsDiv').innerHTML = '<center><h1>Connection Established</h1><center>';
		} else if(request.readyState == 2 && request.status==200){
			document.getElementById('mailsDiv').innerHTML = '<center><h1>Retrieving Data</h1><center>';
		} else if(request.readyState == 3 && request.status==200){
			document.getElementById('mailsDiv').innerHTML = '<center><h1>Processing Data</h1><center>';
		} else if(request.readyState == 4 && request.status==200){
			document.getElementById('mailsDiv').innerHTML = request.responseText;
		} else if(request.status == 500) {
			document.getElementById('mailsDiv').innerHTML = '<center><h1>Server Error</h1><center>';
		} else {
			document.getElementById('mailsDiv').innerHTML = '<center><p>Loading...<p><center>';
		}
	};
	request.open('get','LoadMails?option=' +option ,true);
	request.send(null);
	hideMailDetail();
}

function replyToMail() {
	document.getElementById('composeTo').value = document.getElementById('from').textContent;
	document.getElementById('composeSubject').value = document.getElementById('subject').textContent;
	showComposeDiv();
}

function forwardMail() {
	document.getElementById('composeSubject').value = document.getElementById('subject').textContent;
	document.getElementById('composeContent').value = document.getElementById('msgContent').value;
//	document.getElementById('composeContent').value = document.getElementById('msgContent').value;
	showComposeDiv();
}
function blurBackground() {
	document.querySelector('#composeMailRootDiv').style.backgroundColor = 'rgba(0,0,0,0.7)';
	//alert('here');
}

function darkenBackground() {
	document.querySelector('#composeMailRootDiv').style.backgroundColor = 'rgba(0,0,0,0.86)';
	//alert('here');
}
