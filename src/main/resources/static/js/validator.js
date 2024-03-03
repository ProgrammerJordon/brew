
const validator = function () {};


/**
 * 필수값 검증
 * @param {HTMLFormElement} form - 검사할 HTML 폼 요소
 * @param {Array} required 		 - 필수값 체크를 수행할 필드 목록 ex) ['code', 'name']
 * @return {boolean}			 - 필수값 체크 결과값(true, false)
 */
validator.prototype.validateRequired = function(form, required) {
    var isValid = true;
    var checkIdx = 0;
    var fields = new Array();
    const txt = '은(는) 필수값입니다.';

    for (var i=0; i<form.length; i++) {

        var field = form[i];

        if ((field.type == 'hidden' ||
            field.type == 'text' ||
            field.type == 'textarea' ||
            field.type == 'file' ||
            field.type == 'radio' ||
            field.type == 'checkbox' ||
            field.type == 'select-one' ||
            field.type == 'password')) {

            var value = '';
            // get field's value
            if (field.type == "select-one") {
                var si = field.selectedIndex;
                if (si >= 0) {
                    value = field.options[si].value;
                }
            } else if (field.type == 'radio' || field.type == 'checkbox') {
                if (field.checked) {
                    value = field.value;
                }
            } else {
                value = field.value;
            }
            if (trim(value).length == 0) {
                if(required.includes(field.name)){
                    fields[checkIdx++] = field.title+txt;
                    isValid = false;
                }
            }
        } else if (field.type == "select-multiple") {
            var numOptions = field.options.length;
            lastSelected=-1;
            for(loop=numOptions-1;loop>=0;loop--) {
                if(field.options[loop].selected) {
                    lastSelected = loop;
                    value = field.options[loop].value;
                    break;
                }
            }
            if(lastSelected < 0 || trim(value).length == 0) {

                if(required.includes(field.name)){
                    fields[checkIdx++] = field.title+txt;
                    isValid=false;
                }
            }
        } else if ((field.length > 0) && (field[0].type == 'radio' || field[0].type == 'checkbox')) {
            isChecked=-1;
            for (loop=0;loop < field.length;loop++) {
                if (field[loop].checked) {
                    isChecked=loop;
                    break;
                }
            }

            if (isChecked < 0) {
                if(required.includes(field.name)){
                    fields[checkIdx++] = field.title+txt;
                    isValid=false;
                }
            }
        }
    }
    if (fields.length > 0) {
        alert(fields.join('\n'));
    }
    return isValid;
};
//공백제거
function trim(s) {
    return s.replace( /^\s*/, "" ).replace( /\s*$/, "" );
};



/**
 * 숫자만 입력시
 * @param {String} inputValue	 - 입력값
 * @return {String} 	 		 - 정규식 처리된 값
 */
validator.prototype.validateNumber = function(inputValue){

    var regexPattern = /[^0-9.]/g;
    var outputValue = inputValue.replace(regexPattern, '');
    return outputValue;
};



/**
 * 입력 유효성 검사 및 경고 메시지 표시
 * @param {jQuery} $this - jQuery 객체로서, 현재 요소를 나타냅니다.
 * @param {String} type	 		 - 검증 정보 타입
 * @return {boolean} 	 		 - 체크 결과값(true, false)
 */
validator.prototype.isValiCheck = function(element, type){

    var regex;
    var msg;
    var inputValue = element.val();

    switch(type){
        case 'email':					//이메일
            regex =  /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
            msg = '* 이메일 형식을 확인해주세요. *';
            break;

        case 'url':						//url
            regex =  /^(https?|ftp):\/\/[^\s/$.?#].[^\s]*$/;
            msg = '* URL 형식을 확인해주세요. *';
            break;

        case 'password':				//비밀번호 복잡도
            regex =  /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$/;
            msg = '* 비밀번호 복잡도가 부족합니다. *';
            break;

        case 'residentNumber':			//주민번호
            regex =  /^\d{6}-\d{7}$/;
            msg = '* 주민등록번호를 확인해주세요. *';
            break;

        case 'date':					//날짜 형식
            regex =  /^\d{4}-\d{2}-\d{2}$/;
            msg = '* 입력 날짜를 확인해주세요. *';
            break;

        default :
            throw new Error('type notfound');
    }

    if(regex.test(inputValue)){
        //기존 메시지 삭제
        if(element.next('.warning-message').length > 0){
            element.next('.warning-message').remove();
        }
    }else{
        // 메시지를 담을 새로운 div 요소 생성
        if(element.next('.warning-message').length == 0){
            var warningMessage = $('<div class="warning-message" style="color:red;">'+msg+'</div>');
            element.after(warningMessage);
        }
    }
};



/**
 * 시작 날짜와 종료 날짜의 유효성을 체크합니다.
 * @param {String} startDate - 시작 날짜 문자열 (YYYY-MM-DD)
 * @param {String} endDate - 종료 날짜 문자열 (YYYY-MM-DD)
 * @return {boolean} 	 		 - 체크 결과값(true, false)
 */
validator.prototype.validateDateRange = function(startDate, endDate) {

    if (startDate !== '' || endDate !== '') {
        // 날짜 형식 체크
        var datePattern = /^\d{4}-\d{2}-\d{2}$/;
        if (!datePattern.test(startDate) || !datePattern.test(endDate)) {
            alert('날짜 형식이 올바르지 않습니다.');
            return false;
        }

        // Date 객체 생성
        var start = new Date(startDate);
        var end = new Date(endDate);

        // 시작 날짜가 종료 날짜보다 이전인지 체크
        if (start >= end) {
            alert('시작 날짜는 종료 날짜보다 이전이어야 합니다.');
            return false;
        }
    }

    return true;
};
