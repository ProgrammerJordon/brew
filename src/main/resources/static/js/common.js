/**
 * RequestUrl(
 *      // url
 *     "https://api.example.com/data",
 *     // method
 *     "GET",
 *     {
 *         // DTO JSON 파라미터
 *         param1: "value1",
 *         param2: "value2"
 *     },
 *     // callback 함수
 *     (response) => {
 *         // 성공 시 처리
 *         console.log("성공:", response);
 *     }
 * );
 * @param url
 * @param method
 * @param data
 * @param successCallback
 * @constructor
 */
function RequestUrl(url, method, data, successCallback) {
    try {
        $.ajax({
            url: url,
            type: method,
            data: JSON.stringify(data),
            contentType: 'application/json',
            success: function (result) {
                if (successCallback && typeof successCallback === 'function') {
                    successCallback(result);
                }
            }
        });
    } catch (error) {
        //console.error("에러 발생:", error);
    }
}

function RequestJsonUrl(url, method, data, successCallback) {
    try {
        $.ajax({
            url: url,
            type: method,
            data: data,
            contentType: 'application/json',
            success: function (result) {
                if (successCallback && typeof successCallback === 'function') {
                    successCallback(result);
                }
            }
        });
    } catch (error) {
        //console.error("에러 발생:", error);
    }
}
const xlsx = function () {
};

/**
 * Excel js
 * @param excelHeader
 * @param searchList
 * @param excelName
 * @param title
 * @returns {xlsx}
 */
xlsx.prototype.init = function (excelHeader, searchList, excelName, title) {
    this.excelHeader = excelHeader;
    this.searchList = searchList;
    this.excelName = excelName;
    this.title = title;
    return this;
};

xlsx.prototype.exportToExcel = async function () {

    const date = new Date();
    const workbook = new ExcelJS.Workbook();
    const formattedDate = `${date.getFullYear()}${(date.getMonth() + 1).toString().padStart(2, '0')}${date.getDate().toString().padStart(2, '0')}`;
    const worksheet = workbook.addWorksheet(this.excelName + '_' + formattedDate);
    //타이틀이 null인경우 1번로직, 아니면 2번 로직
    if (this.title === null || this.title === undefined || this.title === '') {
        //헤더 스타일 적용
        const headerStyle = {
            alignment: { horizontal: 'center' },
            font: { bold: true },
            fill: {
                type: 'pattern',
                pattern: 'solid',
                fgColor: { argb: 'ffcccccc' },
            },
        };

        //헤더데이터 입력
        this.excelHeader.forEach((header, index) => {
            worksheet.getCell(1, index + 1).value = header.headerName;
            worksheet.getCell(1, index + 1).alignment = headerStyle.alignment;
            worksheet.getCell(1, index + 1).font = headerStyle.font;
            worksheet.getCell(1, index + 1).fill = headerStyle.fill;
        });

        // 리스트 데이터 입력
        this.searchList.forEach((data, rowIndex) => {
            this.excelHeader.forEach((header, colIndex) => {
                const key = header.field;
                const value = data[key];
                worksheet.getCell(rowIndex + 2, colIndex + 1).value = value;
                worksheet.getCell(rowIndex + 2, colIndex + 1).alignment = header.alignment;

                //value가 null아니고 value 타입이 number인 경우 혹은 numberFormatYn값이 false가 아닌경우
                if ((value != null && typeof value === 'number') || (!this.excelHeader.numberFormatYn === false)) {
                    let numValue = parseFloat(value);
                    //const column = worksheet.getColumn(colIndex + 1);
                    const cell = worksheet.getCell(rowIndex + 2, colIndex + 1);
                    if (numValue % 1 !== 0) {
                        // 소수점 이하 2자리 까지 표시
                        cell.numFmt = '#,##0.##'; // 숫자 포맷 지정
                    } else if (numValue === 0) {
                        // 0인 경우 0으로 표시
                        cell.numFmt = '0'; // 숫자 포맷 지정
                    } else {
                        // 소수점 이하 숫자가 없는 경우 #,###포맷으로 지정
                        cell.numFmt = '#,###'; // 숫자 포맷 지정
                    }
                }
            });
        });
    } else {
        //엑셀 타이틀 작성 및 적용
        //타이틀 머지
        worksheet.mergeCells(1, 1, 1, this.excelHeader.length);

        // 타이틀 작성
        worksheet.getCell('A1').value = this.title;
        worksheet.getCell('A1').alignment = { horizontal: 'center' };

        //헤더 스타일 적용
        const headerStyle = {
            alignment: { horizontal: 'center' },
            font: { bold: true },
            fill: {
                type: 'pattern',
                pattern: 'solid',
                fgColor: { argb: 'ffcccccc' },
            },
        };

        //헤더데이터 입력
        this.excelHeader.forEach((header, index) => {
            worksheet.getCell(2, index + 1).value = header.headerName;
            worksheet.getCell(2, index + 1).alignment = headerStyle.alignment;
            worksheet.getCell(2, index + 1).font = headerStyle.font;
            worksheet.getCell(2, index + 1).fill = headerStyle.fill;
        });

        // 리스트 데이터 입력
        this.searchList.forEach((data, rowIndex) => {
            this.excelHeader.forEach((header, colIndex) => {
                const key = header.field;
                const value = data[key];
                worksheet.getCell(rowIndex + 3, colIndex + 1).value = value;
                worksheet.getCell(rowIndex + 3, colIndex + 1).alignment = header.alignment;

                //value가 null아니고 value 타입이 number인 경우 혹은 numberFormatYn값이 true인 경우
                if ((value != null && typeof value === 'number') || this.excelHeader.numberFormatYn) {
                    let numValue = parseFloat(value);
                    const column = worksheet.getColumn(colIndex + 1);
                    if (numValue % 1 !== 0) {
                        // 소수점 이하 2자리 까지 표시
                        column.numFmt = '#,###.#0'; // 숫자 포맷 지정
                    } else if (numValue === 0) {
                        // 0인 경우 0으로 표시
                        column.numFmt = '0'; // 숫자 포맷 지정
                    } else {
                        // 소수점 이하 숫자가 없는 경우 #,###포맷으로 지정
                        column.numFmt = '#,###'; // 숫자 포맷 지정
                    }
                }
            });
        });
    }

    // 컬럼 너비 자동 맞춤
    const dataWidths = this.excelHeader.map((header, colIndex) => {
        const values = this.searchList.map((data) => data[header.field]);

        const maxLength =
            Math.max(
                this.measureTextWidth(header.headerName),
                ...values.map((value) => (value ? this.measureTextWidth(value) : 0))
            ) + 5;
        // const maxLength = Math.max(header.headerName.length, ...values.map((value) => (value ? value.toString().length : 0))) + 5;

        const width = Math.ceil(maxLength * 0.165);
        return width;
        //  return maxLength;
    });

    worksheet.columns.forEach((column, colIndex) => {
        column.width = dataWidths[colIndex];
    });

    // Excel파일 저장
    const buffer = await workbook.xlsx.writeBuffer();
    const blob = new Blob([buffer], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
    const link = document.createElement('a');
    link.href = window.URL.createObjectURL(blob);
    link.download = this.excelName + '_' + formattedDate;
    link.click();

};

xlsx.prototype.measureTextWidth = function (text, font = '12px sans -serif') {
    const canvas = document.createElement('canvas');
    const context = canvas.getContext('2d');
    context.font = font;
    const metrics = context.measureText(text);
    return metrics.width;
};



/*********************************************************
 * 엑셀 다운로드 함수
 ******************************************************** */
function fn_exportToExcel(){
    // `xlsx` 객체 생성 및 초기화
    this.excelHeaders = [
        { headerName: '분류코드명', field: 'clCodeNm',  alignment: { horizontal: 'center' } },
        { headerName: '코드ID', field: 'codeId',  alignment: { horizontal: 'center' } },
        { headerName: '코드ID명', field: 'codeIdNm' },
        { headerName: '사용여부', field: 'useAt' },
    ];

    var fileName = '테스트';
    var title = "엑셀 제목";
    debugger;

    this.searchList =  JSON.parse('${jsonList}');
    var myXlsx = new xlsx().init(excelHeaders, searchList, fileName , title);

    // 엑셀 파일 생성 및 다운로드
    myXlsx.exportToExcel();
}

/*********************************************************
 * 엑셀 업로드 함수
 ******************************************************** */
function fn_excelUpload(){
    alert('엑셀 파일을 업로드하세요.');
    document.getElementById('uploadBtn').addEventListener('change', fn_uploadExcel);
    document.getElementById('uploadBtn').click();
}

function fn_uploadExcel(event) {
    const file = event.target.files[0];
    const headerIndex = 1;
    debugger;

    var formData = new FormData();
    formData.append("file", file);
    formData.append("headerIndex", headerIndex);
    fetch('<c:url value="/sym/ccm/cca/ExcelUploadCmmnCode.do"/>', {
        method: 'POST',
        body: formData
    });

}

function bsopHourGb(param) {
    if(param == "1") {
        return "09:30"
    }
    if(param == "2") {
        return "10:00"
    }
    if(param == "3") {
        return "11:20"
    }
    if(param == "4") {
        return "12:30"
    }
    if(param == "5") {
        return "14:30"
    }
}

function prdyVrssSign(param) {
    if(param == "1") {
        return "상한가"
    }
    if(param == "2") {
        return "상승"
    }
    if(param == "3") {
        return "보합"
    }
    if(param == "4") {
        return "하한가"
    }
    if(param == "5") {
        return "하락"
    }
}

/**
 * 시장경고코드
 * @param param
 * @returns {string}
 * 00 : 없음
 * 01 : 투자주의
 * 02 : 투자경고
 * 03 : 투자위험
 */
function mrktWarnClsCode(param) {
    if(param == "00") {
        return "양호"
    }
    if(param == "01") {
        return "투자주의"
    }
    if(param == "02") {
        return "투자경고"
    }
    if(param == "03") {
        return "투자위험"
    }
}

function sstsYn(param) {
    if(param == "Y") {
        return "가능"
    }
    if(param == "N") {
        return "불가능"
    }
}

/**
 * 요일구분코드
 * @param param
 * @returns {string}
 * 01:일요일, 02:월요일, 03:화요일, 04:수요일, 05:목요일, 06:금요일, 07:토요일
 */
function wdayDvsnCd(param) {
    if(param == "01") {
        return "일요일"
    }
    if(param == "02") {
        return "월요일"
    }
    if(param == "03") {
        return "화요일"
    }
    if(param == "04") {
        return "수요일"
    }
    if(param == "05") {
        return "목요일"
    }
    if(param == "06") {
        return "금요일"
    }
    if(param == "07") {
        return "토요일"
    }
}

function opndYn(param) {
    if(param == "Y") {
        return "개장"
    }
    if(param == "N") {
        return "휴장"
    }
}

function formatYmdDate(param) {
    let year = param.substring(0, 4);
    let month = param.substring(4, 6);
    let day = param.substring(6, 8);

    return year + '/' + month + '/' + day;
}

function formatHHMMSSTime(param) {
    let hours = param.substring(0, 2);
    let minutes = param.substring(2, 4);
    let seconds = param.substring(4, 6);

    return hours + ':' + minutes + ':' + seconds;
}

function SubstringDateMd (param) {
    let year = param.substring(0, 2); // 앞의 2자리
    let month = param.substring(2, 4); // 뒤의 2자리
    return year+"/"+month;
}

function thousandComma(param) {
    // 정규식을 사용하여 1000단위로 쉼표 추가
    let number = param.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    return number;
}
