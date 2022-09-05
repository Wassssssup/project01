/**reg_frm 이름일 떄, 필수항목 체크해주는 함수 */
function infoConfirm() {
	if (document.reg_frm.id.value.length == 0) {
		alert("아이디는 필수가입 항목입니다.");
		reg_frm.id.focus();

		return;
	}

	if (document.reg_frm.id.value.length < 4) {
		alert("아이디는 4글자 이상이어야합니다.");
		reg_frm.id.focus();

		return;
	}

	if (document.reg_frm.pw.value != document.reg_frm.pw_check.value) {
		alert("비밀번호가 일치하지 않습니다.");
		reg_frm.pw.focus();

		return;
	}

	if (document.reg_frm.name.value.length == 0) {
		alert("이름은 필수가입 항목입니다.")
		reg_frm.name.focus();

		return;
	}

	if (document.reg_frm.email.value.length == 0) {
		alert("이메일은 필수기입 항목입니다.")
		reg_frm.email.focus();

		return;
	}
	document.reg_frm.submit();
}
/**업데이트시 비밀번호 확인 및 필수항목 체크해주는 합수 */
function updateInfoConfirm(){
	
	if(document.reg_frm.pw.value == ""){
		alert("비밀번호를 입력하세요.")
		reg_frm.pw.focus();
		
		return;
	}
	
	if(document.reg_frm.pw.value != document.reg_frm.pw_check.value){
		alert("비밀번호가 일치하지 않습니다.");
		reg_frm.pw.focus();
		
		return;
	}
	
	if(document.reg_frm.email.value.length == 0){
		alert("이메일은 필수기입 항목입니다.")
		reg_frm.email.focus();
		
		return;
	}
	
	document.reg_frm.submit();
}

function emailCheck() {
	if (document.reg_frm.email.value.includes('@')) {
		alert(values.split('@')[1].split('.')[0]);
	} else {
		alert('이메일형식이 아닙니다.')
	}
}