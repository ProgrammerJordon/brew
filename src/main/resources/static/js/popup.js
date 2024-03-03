const Popup = function () {
};

Popup.prototype.init = function (id, url, parameters, fullScreenYn, callbackFunction, contentType) {
    this.id = id;
    this.url = url;
    this.parameters = parameters;
    this.fullScreenYn = fullScreenYn;
    this.callbackFunction = callbackFunction;
    this.data = {};
    this.contentType = contentType;
    return this;
};

Popup.prototype.open = function () {

    var _this = this;
    
    $(`#${this.id}`).remove();
    
    var popupElement = document.createElement('div');
    popupElement.id = this.id;
    popupElement.classList.add('popup-wrapper');
    popupElement.dataset.popup = this.id;

    if (this.fullScreenYn === 'Y') {
        popupElement.classList.add('popup-full-screen');

        var popupElement2 = document.createElement('div');
        popupElement2.classList.add('popup-full-screen-inner');
        popupElement.appendChild(popupElement2);
    }

    var func = function (result) {
        // document.querySelector('.map.wrap').prepend(popupElement);
        document.body.appendChild(popupElement);
        try {
            if (_this.fullScreenYn === 'Y') {
                $(`#${popupElement.id} .popup-full-screen-inner`).html(result);
            } else {
                $(`#${popupElement.id}`).html(result);
            }
        } catch (e) {
            console.error(e);
        }

        $(`#${popupElement.id}`).find('.modal-popup').draggable(
            {containment: [0, 0, window.innerWidth - 100, window.innerHeight - 100]},
            {handle: ".modal-title"}
        );

        $(`#${popupElement.id} .modal-title .btn.close[data-popup-close]`).on('click', function (e) {
            $(`#${popupElement.id}`).remove();
            PopupUtil.popupList = PopupUtil.popupList.filter(popup => popup.id !== popupElement.id);
            e.preventDefault();
        });
        $('input[type="text"]:not([disabled]),textarea').attr('spellcheck', false);
    };
    
    PopupUtil.load(this.id, this.url, this.parameters, func, this.contentType);
    
    return this;
};

Popup.prototype.close = function () {
    if (this.callbackFunction && this.callbackFunction instanceof Function) {
        this.callbackFunction(this.data);
    }
    
    $(`#${this.id}`).remove();
    PopupUtil.popupList = PopupUtil.popupList.filter(popup => popup.id !== this.id);
};

PopupUtil = {
    popupList: [],
    modalList: [],
    openPopup: function (id, url, data, fullScreenYn, callbackFunction, contentType) {
        
        if (this.popupList.filter(pop => pop.id === id).length > 0) {
            $(`#${id}`).remove();
            PopupUtil.popupList = PopupUtil.popupList.filter(popup => popup.id !== id);
        }
        
        var popup = new Popup().init(id, url, data, fullScreenYn, callbackFunction, contentType);
        this.popupList.push(popup);
        
        popup.open();
        
        return popup;
    },
    closePopup: function (id, resultData) {
        var popup = this.getPopup(id);
        
        if (popup) {
            popup.data(resultData).close();
        }
    },
    getPopup: function (id) {
        return this.popupList.find(pop => pop.id === id);
    },
    openModal: function (id, html, callBackFunction) {
        let modal = new tingle.modal({
            footer: false,
            stickyFooter: false,
            onClose: function () {
                PopupUtil.modalList = PopupUtil.modalList.filter(modal => modal.id !== this.id);
                this.destroy();
                if (!callBackFunction && typeof callBackFunction === 'function') {
                    callBackFunction();
                }
            }
        });
        
        modal.setContent(html);
        modal.open();
        
        modal.id = id;
        this.modalList.push(modal);
    },
    openModalWrap: function (id, innerHtml, width = 700) {
        const html = `
            <div id="${id}" class="popup active" style="width: ${width}px;">
                <div class="cont">
                    <div class="inner">${innerHtml}</div>
                </div>
            </div>
        `;
        
        this.openModal(id, html);
    },
    closeModal: function (id) {
        let modal = this.modalList.find(modal => modal.id === id);
        
        if (modal) {
            modal.close();
        }
    },
    load: function (id, url, data, callback, contentType) {

        if (contentType === undefined) {
            contentType = 'application/x-www-form-urlencoded'
        }

        $.ajax({
            beforeSend: function (xhr) {
                xhr.setRequestHeader("AJAX", true);
                xhr.setRequestHeader("POPUPID", id);
            },
            cache: false,
            async: false,
            type: 'POST',
            dataType: 'text',
            url: url,
            data: data,
            contentType: contentType,
            traditional: true,
            success: function (result) {
                if (callback && callback instanceof Function) {
                    callback(result);
                }
            },
            error: function (xhr, status, error) {
                
                console.log(xhr);
                console.log(status);
                console.log(error);
                
                if (xhr.status == 500 || xhr.status == 404 || xhr.status == 200) {
                    try {
                        document.clear();
                    } catch (e) {
                        console.log(e);
                    }
                }
                
                if (xhr && xhr.responseJSON && xhr.responseJSON.errorMessage) {
                    MessageUtil.error(xhr.responseJSON.errorMessage);
                } else{
                    MessageUtil.error("오류가 발생했습니다. 관리자에게 문의하세요..");
                }
            }
        });
    },
};
