const Popup = function () {
};

Popup.prototype.init = function (id, url, parameters, data, callbackFunction ) {
    this.id = id;
    this.url = url;
    this.parameters = parameters;
    this.callbackFunction = callbackFunction;
    this.data = data;
    return this;
};

Popup.prototype.open = function () {
/*
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
*/
};

Popup.prototype.close = function () {
    if (this.callbackFunction && this.callbackFunction instanceof Function) {
        this.callbackFunction(this.data);
    }

    $(`[data-popup="${this.id}"]`).remove();
    PopupUtil.popupList = PopupUtil.popupList.filter(popup => popup.id !== this.id);
};

PopupUtil = {
    popupList: [],
    modalList: [],
    openPopup: function (url, parameters, data, callbackFunction) {
        
        if (this.popupList.filter(pop => pop.id === id).length > 0) {
            $(`#${id}`).remove();
            PopupUtil.popupList = PopupUtil.popupList.filter(popup => popup.id !== id);
        }
        
        var popup = new NewPopup().init('', url, parameters, data, callbackFunction);
        this.popupList.push(popup);
        
        popup.open();
        
        return popup;
    },
    closePopup: function (id) {
        var popup = this.getPopup(id);

        if (popup) {
            popup.close();
        }
    },
    getPopup: function (id) {
        return this.popupList.find(pop => pop.id === id);
    },

    openModal: function (id) {
	    const modal = document.getElementById(id);
		const overlay = document.getElementById('modalOverlay');
	    if (modal) {
	        modal.style.display = 'block';
	        overlay.style.display = 'block';
        }
    },
	openModalFile: function (modalId, jspPath, title, callback) {
		var fullPath = Util.getRequestUrl(jspPath);

        const popup = new Popup().init(modalId, jspPath, null, null, callback);
        this.popupList.push(popup);
		
        const func = function(id, innerHtml, title) {
            const html = `
                <div id="modalOverlay" class="popup__window add__table" data-popup="${id}" style="display: block;">
                    <div id="${id}" class="modal-popup">
                        <div class="popup__header">
                            <p class="title">${title}</p>
                            <button class="btn__close" data-popup-close="${id}"><span class="hidden">닫기</span></button>
                        </div>
                        <div class="popup__body">
                            
                        </div>   
                    </div>
                </div>
            `;
            document.body.insertAdjacentHTML('beforeend', html);
            $('#modalOverlay .popup__body').html(innerHtml);

            $(`[data-popup="${id}"]`).on('mouseover', function() {
                this.style.width = `${this.clientWidth}px`;
                $(this).draggable({
                    containment: [0, 0, window.innerWidth - 100, window.innerHeight - 100],
                    handle: ".popup__header"
                });
            });

            $(`[data-popup-close="${id}"]`).on('click', () => {PopupUtil.closePopup(id)});
        }

        this.loadPopup(modalId, title, fullPath, func);
    },
    loadPopup: function (id, title, url, callback) {
        $.ajax({
            beforeSend: function (xhr) {
                xhr.setRequestHeader("AJAX", true);
                xhr.setRequestHeader("POPUPID", id);
            },
            url: url, // 서버 사이드 JSP 파일의 경로
            type: "GET", // GET 요청
            cache: false,
            async: false,
            dataType: 'text',
            contentType: 'application/x-www-form-urlencoded',
            traditional: true,
            success: (response) => { // Arrow function 사용
                if (callback && callback instanceof Function) {
                    callback(id, response, title);
                }
            },
            error: function(xhr, status, error) {
                console.error("Error occurred: " + error);
            }
        });
    },
	openOzPopup: function (ozInfo, data, formParam, width, height) {
		var queryString = "?ozInfo=" + encodeURIComponent(JSON.stringify(ozInfo)) +
						"&data=" + encodeURIComponent(JSON.stringify(data)) +
						"&formParam=" + encodeURIComponent(JSON.stringify(formParam));
        var fullPath = Util.getRequestUrl("/oz/callOzReport.do") + queryString;
//		var fullPath = Util.getRequestUrl("/oz/callOzReport.do");


		window.open(fullPath, "popupWindow", "width=" + width +",height="+ height + ",scrollbars=yes")
    },
    closeModal: function (id) {
        var modal = document.getElementById(id);
		const overlay = document.getElementById('modalOverlay');
		
	    if (modal) {
	        modal.style.display = 'none';
			overlay.style.display = 'none';
	    }
    }
};
