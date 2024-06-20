/*
function fn_exportToExcel(){
	//excelHeader추가, headerName : 헤더에 나타나는 한글명 , field : 매핑되는 json key명 , alignment: { horizontal: 'center' } => 가운데정렬
     this.excelHeaders = [
       { headerName: '분류코드명', field: 'clCodeNm', alignment: { horizontal: 'center' } },
       { headerName: '코드ID', field: 'codeId',  alignment: { horizontal: 'center' } },
       { headerName: '코드ID명', field: 'codeIdNm' },
       { headerName: '사용여부', field: 'useAt', resizable: true },
     ];
	this.searchList =  JSON.parse('${jsonList}');
	var fileName = '테스트';
	var title = "엑셀 제목";
	//각각 1번째 데이터는 
	var myXlsx = new xlsx().init(excelHeaders, searchList, fileName , title);

    // 엑셀 파일 생성 및 다운로드
    myXlsx.exportToExcel();
}

*/
const xlsx = function () {
};


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

                //동적 로우 color 적용
                if (!Util.isEmpty(data['color'])) {
                    worksheet.getCell(rowIndex + 2, colIndex + 1).fill = {
                        type: 'pattern',
                        pattern: 'solid',
                        fgColor: { argb: data['color'] },
                    };
                }

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
                        cell.numFmt = "0"; // 숫자 포맷 지정
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

                //동적 로우 color 적용
                if (!Util.isEmpty(data['color'])) {
                    worksheet.getCell(rowIndex + 3, colIndex + 1).fill = {
                        type: 'pattern',
                        pattern: 'solid',
                        fgColor: { argb: data['color'] },
                    };
                }

                //value가 null아니고 value 타입이 number인 경우 혹은 numberFormatYn값이 true인 경우
                if ((value != null && typeof value === 'number') || this.excelHeader.numberFormatYn) {
                    let numValue = parseFloat(value);
                    //const column = worksheet.getColumn(colIndex + 1);
					const cell = worksheet.getCell(rowIndex + 3, colIndex + 1);
                    if (numValue % 1 !== 0) {
                        // 소수점 이하 2자리 까지 표시
                        cell.numFmt = '#,###.#0'; // 숫자 포맷 지정
                    } else if (numValue === 0) {
                        // 0인 경우 0으로 표시
                        cell.numFmt = "0"; // 숫자 포맷 지정
                    } else {
                        // 소수점 이하 숫자가 없는 경우 #,###포맷으로 지정
                        cell.numFmt = '#,###'; // 숫자 포맷 지정
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

        const width = Math.ceil(maxLength * 0.165) > 255 ? 255 : Math.ceil(maxLength * 0.165);
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
