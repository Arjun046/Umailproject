// uMail JS File for Home Page

function highlightSignupBtn() {
	document.getElementById('signupBtn').style.color='#03396c';
}
function resetSignupBtn() {
	document.getElementById('signupBtn').style.color='#008080';
}

function highlightLoginBtn() {
	document.getElementById('loginBtn').style.color='yellow';
	document.getElementById('loginBtn').style.width='5em';
	document.getElementById('loginBtn').style.height='2em';
}
function resetLoginBtn() {
	document.getElementById('loginBtn').style.color='#dae4ff';
	document.getElementById('loginBtn').style.width='auto';
	document.getElementById('loginBtn').style.height='auto';
}

function highlightLoginUserId() {
	document.getElementById('userid').style.backgroundColor='#cde3fb';
	document.getElementById('userid').style.fontSize='1.5em';
}
function resetLoginUserId() {
	document.getElementById('userid').style.backgroundColor='white';
	document.getElementById('userid').style.fontSize='1.1em';
}

function highlightLoginPwd() {
	document.getElementById('password').style.backgroundColor='#cde3fb';
	document.getElementById('password').style.fontSize='1.5em';
}
function resetLoginPwd() {
	document.getElementById('password').style.backgroundColor='white';
	document.getElementById('password').style.fontSize='1.1em';
}