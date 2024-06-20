/**
 * 일자선택 태그 모듈
 */
const DatePickerModule = function (id, type, dateFormat, bgnId, toId) {
    this.id = id;
    this.type = type;
    this.dateFormat = dateFormat ? dateFormat : 'yyyy-MM-dd'
    
    this.elementId = `#${this.id}`;
    this.fromElementId = `#${bgnId}`;
    this.toElementId = `#${toId}`;
};

DatePickerModule.prototype.create = function () {
    let option = {
        format: this.dateFormat
    };
    
    if (this.type == '1') {
        $(this.elementId).bootDatePicker(option);

        $(this.elementId).on('blur', () => {
            const length = $(this.elementId).val().length;

            if(length !== 0 && length !== this.dateFormat.length) {
                alert('잘못된 날짜형식입니다.');
                $(this.elementId).setValue('');
            }
        });
    } else {
        $(this.fromElementId).bootDatePicker(option);
        $(this.toElementId).bootDatePicker(option);
        
        $(this.fromElementId).on('change', () => {
            $(this.toElementId).bootDatePicker('setStartDate', $(this.fromElementId).val());
        });

        $(this.toElementId).on('change', () => {
            $(this.fromElementId).bootDatePicker('setEndDate', $(this.toElementId).val());
        });

        $(this.fromElementId).on('blur', () => {
            const length = $(this.fromElementId).val().length;

            if(length !== 0 && length !== this.dateFormat.length) {
                alert('잘못된 날짜형식입니다.');
                $(this.fromElementId).setValue('');
            }
        });

        $(this.toElementId).on('blur', () => {
            const length = $(this.toElementId).val().length;

            if(length !== 0 && length !== this.dateFormat.length) {
                alert('잘못된 날짜형식입니다.');
                $(this.toElementId).setValue('');
            }
        });
    }
};

// Get Element
DatePickerModule.prototype.element = function () {
    return $(this.elementId);
};

DatePickerModule.prototype.fromElement = function () {
    return $(this.fromElementId);
};

DatePickerModule.prototype.toElement = function () {
    return $(this.toElementId);
};

// Get Value
DatePickerModule.prototype.getValue = function () {
    return $(this.elementId).val();
};

DatePickerModule.prototype.getFromValue = function () {
    return $(this.fromElementId).val();
};

DatePickerModule.prototype.getToValue = function () {
    return $(this.toElementId).val();
};

// Set Value
DatePickerModule.prototype.setValue = function (value) {
    $(this.elementId).bootDatePicker('setDate', value);
};

DatePickerModule.prototype.setFromValue = function (value) {
    $(this.fromElementId).bootDatePicker('setDate', value);
};

DatePickerModule.prototype.setToValue = function (value) {
    $(this.toElementId).bootDatePicker('setDate', value);
};
