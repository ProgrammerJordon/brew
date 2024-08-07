(function () {
    window.Util = {
        getRequestUrl: function (uri) {
            
            if (!window.commonData.contextPath) {
                return uri;
            }
            
            if (!uri) {
                return window.commonData.contextPath;
            }
            
            var requestUri = uri.startsWith('/') ? uri.substring(1) : uri;
            
            if (window.commonData.contextPath.endsWith('/') === true) {
                return window.commonData.contextPath + requestUri;
            } else {
                return window.commonData.contextPath + '/' + requestUri;
            }
        },
        //<%-- Function : 빈 문자열 체크 --%>
        isEmpty: function (str) {
            return (typeof str === 'undefined' || str === null || str === '' || (str !== null && typeof str === "object" && !Object.keys(
                str).length));
        },
        //<%-- Function : NVL --%>
        nvl: function (str, defaultStr) {
            return this.isEmpty(str) ? defaultStr : str;
        },
        //<%-- Function : 유효일자 체크 --%>
        isDate: function (date) {
            if (this.isEmpty(date)) {
                return false;
            }
            
            var dateStr = date.toString().replace(/[/.-]/g, "");
            
            if (this.isEmpty(dateStr)) {
                return false;
            }
            
            if (dateStr.length != 8) {
                return false;
            }
            if (!$.isNumeric(dateStr)) {
                return false;
            }
            
            var dtYear = dateStr.substring(0, 4);
            var dtMonth = dateStr.substring(4, 6);
            var dtDay = dateStr.substring(6, 8);
            
            if (dtMonth < 1 || dtMonth > 12) {
                return false;
            } else if (dtDay < 1 || dtDay > 31) {
                return false;
            } else if ((dtMonth == 4 || dtMonth == 6 || dtMonth == 9 || dtMonth == 11) && dtDay == 31) {
                return false;
            } else if (dtMonth == 2) {
                var isleap = (dtYear % 4 == 0 && (dtYear % 100 != 0 || dtYear % 400 == 0));
                if (dtDay > 29 || (dtDay == 29 && !isleap)) {
                    return false;
                }
            }
            
            return true;
        },
        //<%-- Function : 한글 입력값의 조사를 붙여 리턴 --%>
        /**
         * str  (문자열)    : 조사를 붙일 한글 문자열
         * type (숫자)    : 조사 유형
         * 1 : (은, 는)
         * 2 : (이, 가)
         * 3 : (을, 를)
         *
         */
        attachPosition: function (str, type) {
            if (this.isEmpty(str) === true || str.length < 1) {
                return str;
            }
            
            var lastWord = str.charAt(str.length - 1);
            
            //유니코드로 한글 체크
            var pattern = /^[\uac00-\ud7a3+]*$/;
            if (!pattern.test(lastWord)) {
                return str;
            }
            
            //받침 유무 확인  0이면 받침 없음, 1이상이면 받침 있음
            var lastConsonant = (lastWord.charCodeAt(0) - 44032) % 28;
            var addValue = "";
            
            if (lastConsonant === 0) {
                if (type === 1) {
                    addValue = "는"
                } else if (type === 2) {
                    addValue = "가"
                } else if (type === 3) {
                    addValue = "를"
                }
            } else {
                if (type === 1) {
                    addValue = "은"
                } else if (type === 2) {
                    addValue = "이"
                } else if (type === 3) {
                    addValue = "을"
                }
            }
            
            return str + addValue;
        },
        /**
         * <%-- Function : 컴포넌트 유효성 체크 --%>
         *
         * -validationGroup-
         * id                                                : 컴포넌트 id
         * name                                              : 컴포넌트 명
         * mandatory  (boolean)                              : 필수 값 체크
         * isNumeric  (boolean)                              : 숫자 형식 체크
         * commaAble  (boolean)                              : 숫자 형식의 경우 콤마를 허용할지 체크
         * isDate      (boolean)                             : 유효날짜 체크
         * minValue   (number Type 또는 id)                    : 최소값 체크 (minValue <= value -> true)
         * maxValue   (number Type 또는 id)                    : 최대값 체크 (value <= MaxValue -> true)
         * underValue (number Type 또는 id)                    : 미만값 체크 (underValue < value -> true)
         * overValue  (number Type 또는 id)                    : 초과값 체크 (value < MaxValue -> true)
         * minLength  (number Type)                            : 최소입력 글자 수 (minLength <= value.length -> true)
         * maxLength  (number Type)                            : 최대입력 글자 수 (value.length <= maxLength -> true)
         * equalLength(number Type)                            : 입력 글자 수 (equalLength == value.length -> true)
         * minDate    (날짜(yyyy-mm-dd, yyyyMMdd) 또는 id)      : 최소날짜 (minDate <= value -> true)
         * maxDate    (날짜(yyyy-mm-dd, yyyyMMdd) 또는 id)      : 최대날짜 (value <= maxDate -> true)
         * regEmail      (boolean)                                   : 이메일 정규식 체크
         * password  (boolean)                               : 비밀번호 체크
		 *
         * ex) var validationGroup = [
         *        {id : "cr30instt_hq", name : "본부", mandatory : true} ,
         *        {id : "df_RECT_DE_1", name : "접수시작일자", mandatory : true, isDate : true, maxDate : "df_RECT_DE_2"} ,
         *        {id : "df_RECT_DE_2", name : "접수종료일자", mandatory : true, isDate : true, minDate : "df_RECT_DE_1"} ,
         *        {id : "ti_ACCNUT_YY", name : "회계년도", mandatory : true, isNumeric : true}
         *    ];
         *
         * -frmName : 유효성을 체크할 Form name, 없을 경우 컴포넌트 id로만 체크
         */
        validateComponent: function (validationGroup, frmName) {
            if (!Array.isArray(validationGroup)) {
                return false;
            }
            
            var isEmpty = (str) => this.isEmpty(str);
            var isDate = (date) => this.isDate(date);
            var attachPosition = (str, type) => this.attachPosition(str, type);
            
            for (idx in validationGroup) {
                var validation = validationGroup[idx];
                var validationId = validation["id"];
                var component = Util.isEmpty(frmName) ? $(`#${validationId}`) : $(`#${frmName} [id="${validationId}"]`);
                var value = component.data('format') === 'number' ? component.getNumber() || component.val() : component.val();
                var name = validation["name"];
                
                /* 컴포넌트 포커스 */
                var focus = () => component.focus();
                
                for (key in validation) {
                    switch (key) {
                        case "mandatory":
                            if (validation[key] === true && isEmpty(value)) {
                                MessageUtil.alert(`${attachPosition(name, 1)} 필수 입력입니다.`, focus);
                                return false;
                            }
                            break;
                        
                        case "isNumeric":
                            if (isEmpty(value)) {
                                break;
                            }
                            
                            if (validation[key] === true) {
                                if (!$.isNumeric(value)) {
                                    MessageUtil.alert(`${attachPosition(name, 1)} 숫자 형식이어야 합니다.`, focus);
                                    return false;
                                }
                            }
                            break;
                        
                        case "isDate":
                            if (isEmpty(value)) {
                                break;
                            }
                            
                            if (validation[key] === true) {
                                if (!isDate(value)) {
                                    MessageUtil.alert(`${attachPosition(name, 1)} 유효한 날짜가 아닙니다.`, focus);
                                    return false;
                                }
                            }
                            break;
                        
                        case "minValue":
                            if (isEmpty(value)) {
                                break;
                            }
                            var minValue;
                            
                            if (typeof validation[key] === "number") {			/* minValue가 number */
                                minValue = validation[key];
                            } else {											/* minValue가 id */
                                if (isEmpty($(`#${validation[key]}`).val())) {
                                    break;
                                }
                                
                                minValue = $(`#${validation[key]}`).data('format') === 'number' ? $(`#${validation[key]}`).getNumber() : Number(
                                    $(`#${validation[key]}`).val());
                            }
                            
                            if (!$.isNumeric(minValue)) {
                                break;
                            }
                             
                            if (!(minValue <= Number(value))) {
                                MessageUtil.alert(`${attachPosition(name, 1)} ${minValue} 보다 작을 수 없습니다.`, focus);
                                return false;
                            }
                            break;
                        
							
                        case "maxValue":
                            if (isEmpty(value)) {
                                break;
                            }
                            var maxValue;
                            
                            if (typeof validation[key] === "number") {			/* maxValue가 number */
                                maxValue = validation[key];
                            } else {											/* maxValue가 id */
                                if (isEmpty($(`#${validation[key]}`).val())) {
                                    break;
                                }
                                
                                maxValue = $(`#${validation[key]}`).data('format') === 'number' ? $(`#${validation[key]}`).getNumber() : Number(
                                    $(`#${validation[key]}`).val());
                            }
                            
                            if (!$.isNumeric(maxValue)) {
                                break;
                            }
                            
                            if (!(Number(value) <= maxValue)) {
                                MessageUtil.alert(`${attachPosition(name, 1)} ${maxValue} 보다 클 수 없습니다.`, focus);
                                return false;
                            }
                            break;
                        
                        case "underValue":
                            if (isEmpty(value)) {
                                break;
                            }
                            var underValue;
                            
                            if (typeof validation[key] === "number") {			/* underValue가 number */
                                underValue = validation[key];
                            } else {											/* underValue가 id */
                                if (isEmpty($(`#${validation[key]}`).val())) {
                                    break;
                                }
                                
                                underValue = $(`#${validation[key]}`).data('format') === 'number' ? $(`#${validation[key]}`).getNumber() : Number(
                                    $(`#${validation[key]}`).val());
                            }
                            
                            if (!$.isNumeric(underValue)) {
                                break;
                            }
                            
                            if (!(underValue < Number(value))) {
                                MessageUtil.alert(`${attachPosition(name, 1)} ${underValue} 보다 작거나 같을 수 없습니다.`, focus);
                                return false;
                            }
                            break;
                        
                        case "overValue":
                            if (isEmpty(value)) {
                                break;
                            }
                            var overValue;
                            
                            if (typeof validation[key] === "number") {			/* overValue가 number */
                                overValue = validation[key];
                            } else {											/* overValue가 id */
                                if (isEmpty($(`#${validation[key]}`).val())) {
                                    break;
                                }
                                
                                overValue = $(`#${validation[key]}`).data('format') === 'number' ? $(`#${validation[key]}`).getNumber() : Number(
                                    $(`#${validation[key]}`).val());
                            }
                            
                            if (!$.isNumeric(overValue)) {
                                break;
                            }
                            
                            if (!(Number(value) < overValue)) {
                                MessageUtil.alert(`${attachPosition(name, 1)} ${overValue} 보다 크거나 같을 수 없습니다.`, focus);
                                return false;
                            }
                            break;
                        
                        case "minLength":
                            if (isEmpty(value)) {
                                break;
                            }
                            var minLength = validation[key];
                            
                            if (!(minLength <= value.length)) {
                                MessageUtil.alert(`${attachPosition(name, 1)} ${minLength}자 이상 입력해야합니다.`, focus);
                                return false;
                            }
                            break;
                        
                        case "maxLength":
                            if (isEmpty(value)) {
                                break;
                            }
                            var maxLength = validation[key];
                            
                            if (!(value.length <= maxLength)) {
                                MessageUtil.alert(`${attachPosition(name, 1)} ${maxLength}자를 넘을 수 없습니다.`, focus);
                                return false;
                            }
                            break;
                        
                        case "equalLength":
                            if (isEmpty(value)) {
                                break;
                            }
                            var equalLength = validation[key];
                            
                            if (value.length != equalLength) {
                                MessageUtil.alert(`${attachPosition(name, 1)} ${equalLength}자리이어야 합니다.`, focus);
                                return false;
                            }
                            break;
                        
                        case "maxBytesLength":
                            if (isEmpty(value)) {
                                break;
                            }
                            var maxBytesLength = validation[key];
                            var bytesLength = Util.getBytesLength(value);
                            
                            if (!(bytesLength <= maxBytesLength)) {
                                MessageUtil.alert(`${attachPosition(name, 1)} ${maxBytesLength} Byte를 넘을 수 없습니다.`, focus);
                                return false;
                            }
                            break;
                        
                        case "minDate":
                            if (isEmpty(value)) {
                                break;
                            }
                            value = value.toString().replace(/[/.-]/g, "");
                            var minDate;
                            var dateFormat;
                            
                            if (isDate(validation[key])) {						/* minDate가 날짜 */
                                minDate = validation[key].toString().replace(/[/.-]/g, "");
                                dateFormat = `${minDate.substring(0, 4)}-${minDate.substring(4, 6)}-${minDate.substring(6, 8)}`;
                            } else {											/* minDate가 id */
                                minDate = $(`#${validation[key]}`).val().toString().replace(/[/.-]/g, "");
                                
                                if (!isDate(minDate)) {
                                    break;
                                }
                                
                                dateFormat = `${minDate.substring(0, 4)}-${minDate.substring(4, 6)}-${minDate.substring(6, 8)}`;
                            }
                            
                            if (!(Number(minDate) <= Number(value))) {
                                MessageUtil.alert(`${attachPosition(name, 1)} ${dateFormat} 보다 이전일 수 없습니다.`, focus);
                                return false;
                            }
                            break;
                        
                        case "maxDate":
                            if (isEmpty(value)) {
                                break;
                            }
                            value = value.toString().replace(/[/.-]/g, "");
                            var maxDate;
                            var dateFormat;
                            
                            if (isDate(validation[key])) {						/* maxDate가 날짜 */
                                maxDate = validation[key].toString().replace(/[/.-]/g, "");
                                dateFormat = `${maxDate.substring(0, 4)}-${maxDate.substring(4, 6)}-${maxDate.substring(6, 8)}`;
                            } else {											/* maxDate가 id */
                                maxDate = $(`#${validation[key]}`).val().toString().replace(/[/.-]/g, "");
                                
                                if (!isDate(maxDate)) {
                                    break;
                                }
                                
                                dateFormat = `${maxDate.substring(0, 4)}-${maxDate.substring(4, 6)}-${maxDate.substring(6, 8)}`;
                            }
                            
                            if (!(Number(value) <= Number(maxDate))) {
                                MessageUtil.alert(`${name}는 ${dateFormat} 보다 이후일 수 없습니다.`, focus);
                                return false;
                            }
                            break;
                        
                        case "regEmail":
                            if (isEmpty(value)) {
                                break;
                            }
                            
                            if (validation[key] === true) {
                                var reg = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
                                
                                if (!reg.test(value)) {
                                    MessageUtil.alert(`${attachPosition(name, 2)} 형식에 맞지 않습니다.`, focus);
                                    return false;
                                }
                            }
                            break;
                        
                        case "regTelno":
                            if (isEmpty(value)) {
                                break;
                            }
                            
                            if (validation[key] === true) {
                                var reg = /(^(01[016789]{1}|02|0[3-9]{1}[0-9]{1})-[0-9]{3,4}-[0-9]{4})$/;
                                var reg2 = /^(15|16|18)[0-9]{2}-?[0-9]{4}$/;
                                
                                if (!reg.test(value) && !reg2.test(value)) {
                                    MessageUtil.alert(`${attachPosition(name, 2)} 형식에 맞지 않습니다.`, focus);
                                    return false;
                                }
                            }
                            break;

						case "url" :						//url
				
							if(!regex.test(value)) {
								MessageUtil.alert(`${attachPosition(name, 1)} URL 형식을 확인해주세요.`, focus);
                                return false;
							}
							break;
						case "password" : 
							if (isEmpty(value)) {
                                break;
                            }
 							if (validation[key] === true) {
								var reg = /^(?=.*[a-z])(?=.*\d)(?=.*[~!@#$%^&*?])[A-Za-z\d~!@#$%^&*?]{8,20}$/;
								
								if (!reg.test(value)) {
									MessageUtil.alert(`${attachPosition(name, 2)} 형식에 맞지 않습니다.`, focus)
							        return false; // 기본 조건을 만족하지 않음
							    }
								
								for (let i = 0; i < value.length - 2; i++) {
							        let current = value.charCodeAt(i);
							        let next = value.charCodeAt(i + 1);
							        let nextnext = value.charCodeAt(i + 2);
							
							        // 3자리 이상 반복되는 문자 검사
							        if (current === next && next === nextnext) {
										MessageUtil.alert(`${attachPosition(name, 2)} 3자리 이상 반복 할 수 없습니다.`, focus)
							            return false;
							        }
							
							        // 3자리 연속된 문자 검사
							        if (current + 1 === next && next + 1 === nextnext ||
							            current - 1 === next && next - 1 === nextnext) {
										MessageUtil.alert(`${attachPosition(name, 2)} 3자리 이상 반복 할 수 없습니다.`, focus)
							            return false;
							        }
							    }

							}
							break;

                    } // end switch
                } // end validation
            } // end validationGroup
            return true;
        },
        getTelNumber: function (telNumber) {
            
            if (!telNumber) {
                return '';
            }
            
            var fmtTelNumber = telNumber.replaceAll(/[^0-9]/g, '');
            var result = '';
            var length = fmtTelNumber.length;
            
            if (length === 8) {
                result = fmtTelNumber.replace(/(\d{4})(\d{4})/, '$1-$2');
            } else if (fmtTelNumber.startsWith('02') && (length === 9 || length === 10)) {
                result = fmtTelNumber.replace(/(\d{2})(\d{3,4})(\d{4})/, '$1-$2-$3');
            } else if (length === 10 || length === 11) {
                result = fmtTelNumber.replace(/(\d{3})(\d{3,4})(\d{4})/, '$1-$2-$3');
            } else {
                result = telNumber;
            }
            
            return result;
        },
        getBytesLength: function (value) {
            
            if (Util.isEmpty(value) === true) {
                return 0;
            }
            
            return value
            .split('')
            .map(s => s.charCodeAt(0))
            .reduce((prev, c) => (prev + ((c === 10) ? 2 : ((c >> 7) ? 2 : 1))), 0);
        },
        /**
         * 금액 세자리마다 콤마
         *
         * @param {number} value
         * @return {string} 숫자 세자리마다 콤마
         * @example 100000000 => 1,000,000,000
         */
        numberComma: function (value) {
            if (!$.isNumeric(value)) {
                return '';
            }

            return Number(value).toLocaleString();
        },
        
        /**
         * 전화번호, 휴대폰번호 포맷
         * @param {string} value 전화번호, 휴대폰번호
         * @return 전화번호, 휴대폰번호 포맷
         */
        formatTelno: function (value) {
            
            if (Util.isEmpty(value)) {
                return "";
            }
            
            value = value.replace(/ /g, "");
            
            var tel1;
            var tel2;
            var tel3;
            
            if (value.length == 9) {
                tel1 = value.substring(0, 2);
                tel2 = value.substring(2, 5);
                tel3 = value.substring(5);
            } else if (value.length == 10) {
                if (value.substring(0, 2) == "02") {
                    tel1 = value.substring(0, 2);
                    tel2 = value.substring(2, 6);
                    tel3 = value.substring(6);
                } else {
                    tel1 = value.substring(0, 3);
                    tel2 = value.substring(3, 6);
                    tel3 = value.substring(6);
                }
            } else if (value.length == 11) {
                tel1 = value.substring(0, 3);
                tel2 = value.substring(3, 7);
                tel3 = value.substring(7);
            } else if (value.length == 12) {
                tel1 = value.substring(0, 4);
                tel2 = value.substring(4, 8);
                tel3 = value.substring(8);
            } else {
                return value;
            }
            
            return $.trim(tel1) + "-" + $.trim(tel2) + "-" + $.trim(tel3);
            
        },
        /**
         * 전화번호/휴대폰번호 유효성 검사
         * @param {string} tel 전화번호 or 휴대폰번호
         * @return {boolean} 유효한 전화번호 or 휴대폰번호 여부
         */
        isTelFormat: function (tel) {
            
            if (tel === "") {
                return true;
            }
            
            return /^(01[016789]{1}|02|0[3-9]{1}[0-9]{1})-?[0-9]{3,4}-?[0-9]{4}$/.test(tel);
        },
        /**
         * array 그룹화 함수
         *
         * @param {Array} 그룹화할 배열
         * @param {groupId} 그룹 기준 property명
         * @param {func} 그룹화한 배열 후 처리 함수
         *
         */
        fnGroupByArr: function (arr, groupId, func) {
            
            let group = arr.reduce((acc, curr) => {
                let value = curr[groupId];
                
                if (acc[value]) {
                    acc[value].push(curr)
                } else {
                    acc[value] = [curr];
                }
                
                return acc;
            }, {});
            
            if (func && func instanceof Function) {
                return func(group);
            } else {
                return group;
            }
        },
        /**
         * javascript Object의 key와 일치하는 element name의 value 바인딩
         * obj (Object {}): 오브젝트
         * formName (String): 바인딩할 element의 FormName (없는 경우 전역 element)
         * @param obj
         * @param formName
         */
        bindElementFromObject: function(obj = {}, formName) {
            Object.keys(obj).forEach(key => {
                let el;
                if (formName) {
                    el = document.querySelector(`[name='${formName}'] [name='${key}']`);
                } else {
                    el = document.querySelector(`[name='${key}']`);
                }

                if (el) {
                    el.value = obj[key];
                }
            });
        },
        /**
         * formId 안의 element name을 key로 하는 object 반환
         * formId (String) : formId 문자열
         * @param formId
         * @returns {*}
         */
        getObjectFromForm: function(formId) {
            const formData = $(`#${formId}`).serializeArray();
                        
            let objectData = formData.reduce((obj, el) => {
                obj[el.name] = el.value;
                return obj;
            }, {});
        
            return objectData;
        },
        /**
         * 좌표 -> 도, 분, 초 형식으로 변환
         * @param coordinate (string or number) -> 좌표값 필수
         * @param direction (string) -> 끝자리 표기 ex) direction = 'N' => %s° %s' %s"N
         */
        getCoordFormat: function(coordinate, suffix = '') {
            if (Util.isEmpty(coordinate)) return;

            const coord = Number(coordinate);

            const degrees = Math.floor(coord);              //도
            const minuteDecimal = (coord - degrees) * 60;   //분 (소수점 포함)
            const minutes = Math.floor(minuteDecimal);      //분
            const seconds = Math.round((minuteDecimal - minutes) * 60 * 10000)/10000; //초

            const minFormat = minutes.toString().padStart(2, '0');  //정수 한자리 0으로 채움
            const secFormat = seconds.toString().padStart(2, '0');  //정수 한자리 0으로 채움

            return `${degrees}° ${minFormat}' ${secFormat}"${suffix}`;
        }
    };
    
    window.DateUtil = {
        /**
         * datePicker 객체 생성
         *
         * elIdArr(String or Array) : datePicker 객체 생성할 element Id
         * format(String) : 날짜표기 포맷 : y: 연도, m: 연-월, d: 연-월-일 => default: 'd'
         * options(Object) : datePciker 생성 옵션
         * -options-
         * autoclose(boolean) : 날짜선택시, 달력 접힘 => default: true
         * language(String) : 표기언어 => default: 'ko'
         * readonly(boolean): 인풋박스 readonly 여부 => default: true
         * todayHighlight(boolean): 달력 오늘날짜 표시여부 => default: true
         * clearBtn: 날짜초기화 버튼 표시 여부 => default: true
         * todayBtn: 오늘선택 버튼 표시 여부 => default: true
         * initialDate(String or Array): 초기날짜 (포맷맞춰야함 ex : 2024-01-01), 배열로 전달할 경우 elIdArr과 index가 일치해야함
         * startDate: 최소일자(이전 날짜는 선택 불가 : 포맷 맞춰야함 ex : 2024-01-01)
         * endDate: 최대일자(이후 날짜는 선택 불가 : 포맷 맞춰야함 ex : 2024-01-01)
         * dateSeparator: 날짜구분 기호 => default: '-'
         */
        createDatePicker: function(elIdArr = [], format = 'd', options = {}) {
            const dateSeparator = options.dateSeparator || '-';
            let dateFormat = `yyyy${dateSeparator}mm${dateSeparator}dd`;
            let viewMode = 'days';

            if (format === 'y') {
                dateFormat = 'yyyy';
                viewMode = 'years';
            } else if (format === 'm') {
                dateFormat = `yyyy${dateSeparator}mm`;
                viewMode = 'months';
            }

            const defaultOptions = {
                format: dateFormat,
                autoclose: true,
                language : 'ko',
                viewMode: viewMode,
                minViewMode: viewMode,

                readonly: true,         /*라이브러리 옵션 enableOnReadonly 가 동작하지 않음input box readonly로 변경*/
                todayHighlight: true,
                clearBtn: true,
                todayBtn: true,

                /* 커스텀 옵션 */
                initialDate: '',
                startDate: '',
                endDate: ''
            };

            const datePickerOptions = {...defaultOptions, ...options};

            if (!Array.isArray(elIdArr)) {
                elIdArr = [elIdArr];
            }

            elIdArr.forEach((el, idx) => {
                const element = $(`#${el}`);
                element.attr('autocomplete', 'off');    // 인풋박스 자동완성 기능 해제
                element.attr('readonly', datePickerOptions.readonly);

                // initialDate 배열로 전달할 경우 elIdArr과 index가 일치해야 함
                if (Array.isArray(datePickerOptions.initialDate)) {
                    element.val(datePickerOptions.initialDate[idx] !== null && datePickerOptions.initialDate[idx] !== undefined ? datePickerOptions.initialDate[idx] : '');
                } else {
                    element.val(datePickerOptions.initialDate !== null && datePickerOptions.initialDate !== undefined ? datePickerOptions.initialDate : '');
                }
                element.datepicker(datePickerOptions);
            });
        },
        /*
         * 날짜 계산
         * @param : operand {계산 할 일수  (ex  0(당일), 1(하루 뒤), -1(어제) } default:0 }
         * @param : dt		{계산 할 기준일  'YYYY-MM-DD'로 입력받음} default : today}
         * @param : gb		{일자 사이 구분값 ('-'=> 'YYYY-MM-DD' '/' => 'YYYY/MM/DD'}
         * */
        calcDate: function (operand, dt, gb) {
            dt = (typeof dt == 'undefined' || dt == null || dt == '') ? new Date() : dt;
            
            const day = new Date(dt);
            if (!isNaN(Number(operand))) {
                day.setDate(day.getDate() + operand);
            }
            
            let year = day.getFullYear();
            let month = day.getMonth() + 1;
            let date = day.getDate();
            
            let returnDate;
            
            if (gb) {
                returnDate = year + gb + (month < 10 ? '0' + month : month) + gb + (date < 10 ? '0' + date : date);
            } else {
                returnDate = year + (month < 10 ? '0' + month : month) + (date < 10 ? '0' + date : date);
            }
            
            return returnDate;
        },
        /**
         * 연/월/일 사이에 "-"
         *
         * @param {string} date
         * @param {string} separator 날짜포맷 구분자
         * @return {string} 연/월/일 사이에 separator을 삽입하여 반환
         * @example YYYYMMDD => YYYY-MM-DD
         */
        dateFormat: function (date, separator = '-') {
            
            if (date == null) {
                return "";
            }

            const dateValue = date.replace(/[/.-]/g, '');

            if (dateValue.length != 8) {
                return date;
            }
            
            return dateValue.substr(0, 4) + separator + dateValue.substr(4, 2) + separator + dateValue.substr(6, 2);
        },
        //<%-- Function : 날짜간 일자 계산 --%>
        getDateDifference: function (src, dest) {
            if (!Util.isDate(src) || !Util.isDate(dest)) {
                return null;
            }
            
            const srcDate = new Date(DateUtil.dateFormat(src));
            const destDate = new Date(DateUtil.dateFormat(dest));
            
            return Math.abs((srcDate.getTime() - destDate.getTime()) / (1000 * 60 * 60 * 24));
        },
        getTodayDateFormat: function (format) {
            const date = new Date();
            
            const map = {
                mm: (date.getMonth() + 1).toString().padStart(2, '0'),
                dd: date.getDate().toString().padStart(2, '0'),
                yy: date.getFullYear().toString().slice(-2),
                yyyy: date.getFullYear()
            };
            
            return format.replace(/mm|dd|yyyy|yy/gi, matched => map[matched]);
        }
    };
    
    window.callModule = {
        post: function (path, params, method = 'post') {
            const form = document.createElement('form');
            form.method = method;
            form.action = path;
            for (const key in params) {
                if (params.hasOwnProperty(key)) {
                    const hiddenField = document.createElement('input');
                    hiddenField.type = 'hidden';
                    hiddenField.name = key;
                    hiddenField.value = params[key];
                    form.appendChild(hiddenField);
                }
            }
            document.body.appendChild(form);
            form.submit();
        },
        call: function (url, data, callback, async = true, method) {
            
            if (method === undefined) {
                method = 'POST';
            }
            
            $.ajax({
            	beforeSend: function (xhr) {
                    xhr.setRequestHeader("AJAX", true);
                    $("#loader-list").show();
                },
                complete: function() {
                	$("#loader-list").hide();
                },
                cache: false,
                async: async,
                type: method,
                dataType: 'json',
                url: url,
                data: JSON.stringify(data),
                contentType: "application/json; charset=UTF-8",
                traditional: true,
                success: function (result) {
                    callModule.successHandler(result, callback);
                },
                error: function (xhr, status, error) {
                    callModule.errorHandler(xhr, status, error);
                }
            });
        },
        noloadcall: function (url, data, callback, async = true, method) {

            if (method === undefined) {
                method = 'POST';
            }

            $.ajax({
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("AJAX", true);
                },
                complete: function() {
                },
                cache: false,
                async: async,
                type: method,
                dataType: 'json',
                url: url,
                data: JSON.stringify(data),
                contentType: "application/json; charset=UTF-8",
                traditional: true,
                success: function (result) {
                    callModule.successHandler(result, callback);
                },
                error: function (xhr, status, error) {
                    callModule.errorHandler(xhr, status, error);
                }
            });
        },
        callForm: function (frmId, url, dataType, callback, async = true) {
            
            $("#" + frmId).ajaxSubmit({
            	beforeSend: function (xhr) {
                    xhr.setRequestHeader("AJAX", true);
                    $("#loader-list").show();
                },
                complete: function() {
                	$("#loader-list").hide();
                },
                url: url,
                cache: false,
                async: async,
                type: 'POST',
                dataType: dataType,
                timeout: 100000000,
                success: function (result) {
                    callModule.successHandler(result, callback);
                },
                error: function (xhr, status, error) {
                    callModule.errorHandler(xhr, status, error);
                }
            });
        },
        callMultipart: function (url, data, callback, async = true) {
            
            $.ajax({
            	beforeSend: function (xhr) {
                    xhr.setRequestHeader("AJAX", true);
                    $("#loader-list").show();
                },
                complete: function() {
                	$("#loader-list").hide();
                },
                cache: false,
                async: async,
                type: 'POST',
                processData: false,
                contentType: false,
                encType: 'multipart/form-data',
                url: url,
                data: data,
                dataType: 'json',
                success: function (result) {
                    callModule.successHandler(result, callback);
                },
                error: function (xhr, status, error) {
                    callModule.errorHandler(xhr, status, error);
                }
            });
        },
        successHandler: function (resultData, callback) {
        	var result;
        	try {
        		result = JSON.parse(resultData); // HTML 형태인경우
        	} catch(error) {
        		result = resultData;
        	}
			if (result.redirect) {
				location.href = result.redirect;
			}
			
            if (result.errorMessage != undefined) {
                MessageUtil.error(result.errorMessage);
                return;
            }
            
            if (callback && callback instanceof Function) {
                callback(result);
            }
        },
        errorHandler: function (xhr, status, error) {
            console.log(`${xhr}\r\nstatus : ${status}\r\nerror : ${error}`);

            if (xhr && xhr.responseJSON && xhr.responseJSON.errorMessage) {
                MessageUtil.error(xhr.responseJSON.errorMessage);
            } else {
                MessageUtil.error("오류가 발생했습니다. 관리자에게 문의하세요..");
            }
        },
        simplCall: function (url, data, dataType, callback) {
            $.ajax({
                url: url,
                type:'post',
                data:data,
                dataType: dataType,
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("AJAX", true);
                    $("#loader-list").show();
                },
                complete: function() {
                	$("#loader-list").hide();
                },
                success: function (result) {
                    callModule.successHandler(result, callback);
                },
                error: function (xhr, status, error) {
                    callModule.errorHandler(xhr, status, error);
                }
            });
        }
    };

    window.gridModule = {

        getPageSize: function(list) {
            let pageSize = 0;
            pageSize = Math.floor((list.length - 1) / 10 + 1);

            return pageSize;
        },

        clear_grid: function(id) {
            var el = document.querySelector("#"+id);

            while (el.firstChild) {
                el.removeChild(el.firstChild);
            }
        },
    }
})();