class MessageUtil {
    static alert(message, callback) {
        Swal.fire({
            html: message,
            icon: 'info',
            allowOutsideClick: false,
            didDestroy: () => {
                if (callback && callback instanceof Function) {
                    callback();
                }
            }
        });
    };
    
    static confirm(message, callback, confirmText, cancelText) {
        Swal.fire({
            html: message,
            icon: 'question',
            allowOutsideClick: false,
            showCancelButton: true,
            confirmButtonText: confirmText ? confirmText : '확인',
            cancelButtonText: cancelText ? cancelText : '취소'
        }).then((result) => {
            if (callback && callback instanceof Function) {
                callback(result.value === true ? true : false);
            }
        });
    };
    
    static confirmPromise(message, confirmText, cancelText) {
        return Swal.fire({
            html: message,
            icon: 'question',
            allowOutsideClick: false,
            showCancelButton: true,
            confirmButtonText: confirmText ? confirmText : '확인',
            cancelButtonText: cancelText ? cancelText : '취소'
        }).then((result) => {
            if (!result.value) reject();
        });
    };
    
    static success(message, callback, callbackValue) {
        Swal.fire({
            html: message,
            icon: 'success',
            allowOutsideClick: false
        }).then((result) => {
            if (callback && callback instanceof Function) {
                callback(callbackValue);
            }
        });
    };
    
    static error(message, callback) {
        Swal.fire({
            html: message,
            icon: 'error',
            allowOutsideClick: false
        }).then((result) => {
            if (callback && callback instanceof Function) {
                callback();
            }
        });
    };
    
    static toast(message, timer) {
        Swal.fire({
            html: message,
            toast: true,
            position: 'center',
            showConfirmButton: false,
            timer: (timer??1500),
            timerProgressBar: true,
            customClass: {
                container: 'swal2-toast-container',
                popup: 'swal2-toast-popup',
                htmlContainer: 'swal2-toast-html-container',
                timerProgressBar: 'swal2-toast-progress'
            },
            didOpen: (toast) => {
                toast.addEventListener('mouseenter', Swal.stopTimer);
                toast.addEventListener('mouseleave', Swal.resumeTimer);
                toast.addEventListener('click', Swal.closeToast);
            }
        });
    };
};
