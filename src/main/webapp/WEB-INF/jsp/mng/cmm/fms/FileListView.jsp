<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<style>
    #file-upload-btn {
        cursor: pointer;
        margin-top: 5px;
    }
    #file-list {
        width: auto;
        height: 100px;
        border: 1px solid #ccc;
        overflow-y: auto;
        padding: 5px;
        background-color: #fff;
        font-family: monospace;
    }
    .file-list-item {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 5px;
    }
    .remove-button {
        cursor: pointer;
        background-color: #f44336;
        color: white;
        border: none;
        padding: 2px 6px;
        border-radius: 50%;
        font-weight: bold;
    }
    .hidden, .detail-view .remove-button, .detail-view #file-upload-btn {
        display: none;
    }
    .detail-view .file-list-item {
        cursor: pointer;
    }
    .file-span {
        font-size: 12px;
        text-align: left;
        margin-left: 5px;
    }
</style>



<!-- <form name="fileForm" action="" method="post" >  -->
<input type="hidden" name="atchFileId" id ="atchFileId" value="<c:out value='${atchFileId}'/>">
<input type="hidden" name="fileSn" >
<input type="hidden" name="fileListCnt" id="fileListCnt" value="<c:out value='${fileListCnt}'/>">
<input type="hidden" name="filePath" id ="filePath" value="<c:out value='${filePath}'/>">
<input type="hidden" name="fileKey" id ="fileKey" value="<c:out value='${fileKey}'/>">
<c:set var="fileCount" value="${fn:length(fileList) }" />
<!-- </form>  -->

<div id="file-container">
    <div id="file-list" class="file-list"></div>
    <c:if test="${updateFlag == 'Y'}"> <!-- 등록/수정에서만 info 보이게 -->
        <button id="file-upload-btn" type="button" class="btn__black__line">파일 찾기</button><span class="file-span" id="fileUploadInfo"></span>
        <input type="file" id="file-upload" class="hidden" multiple onchange="addFilesToList(event)" />
        <label for="file-upload"></label>
    </c:if>
</div>






<script type="text/javascript">
    const MAX_FILE_SIZE = '${fileSize}' * 1024 * 1024; // 10MB
    const MAX_FILE_COUNT = '${fileCnt}';
    const ALLOWED_EXTENSIONS = '${ext}'.split(',');
    const fileSize = '${fileSize}';
    const updateFlag =  '${updateFlag}';
    let fileLists = JSON.parse('${fileListJson}'); // 백엔드에서 전달받은 파일 리스트
    let anoFileLists = JSON.parse('${fileListJson}'); // 백엔드에서 전달받은 파일 리스트
    document.addEventListener('DOMContentLoaded', function() {



        // 파일 리스트를 화면에 표시하는 함수
        function displayFiles(fileList) {
            const fileListDiv = document.getElementById('file-list');
            const infoDiv = document.getElementById('fileUploadInfo');
            if(updateFlag == 'Y') {
                infoDiv.textContent = '최대' + fileSize + 'MB, 파일개수 ' + MAX_FILE_COUNT + '개';
            }
            fileList.forEach(file => {
                const fileDiv = document.createElement('div');
                fileDiv.classList.add('file-list-item');
                fileDiv.setAttribute('data-filename', file.name);
                fileDiv.textContent = file.orignlFileNm;
                if (updateFlag !== 'N') {
                    const removeButton = document.createElement('button');
                    removeButton.textContent = 'x';
                    removeButton.classList.add('remove-button');
                    removeButton.type = 'button';
                    removeButton.onclick = function() {
                        deleteFileToList(file);
                        fileListDiv.removeChild(fileDiv);
                    };
                    fileDiv.appendChild(removeButton);
                } else {
                    // 상세 조회 모드일 때 파일 클릭 이벤트 처리 (다운로드 등)
                    fileDiv.onclick = function() {
                        window.open("<c:url value='/cmm/fms/FileDown.do?atchFileId="+file.atchFileId+"&fileSn="+file.fileSn+"'/>");
                    };
                }
                fileListDiv.appendChild(fileDiv);
            });
        }

        // 모드에 따라 클래스 추가
        const container = document.getElementById('file-container');
        if (updateFlag === 'N') {
            container.classList.add('detail-view');
        }

        displayFiles(fileLists);

        if (updateFlag !== 'N') {
            document.getElementById('file-upload-btn').addEventListener('click', function() {
                document.getElementById('file-upload').click();
            });
        }
    });

    function fn_egov_downFile(atchFileId, fileSn){
        window.open("<c:url value='/cmm/fms/FileDown.do?atchFileId="+atchFileId+"&fileSn="+fileSn+"'/>");
    }

    let fileList = [];
    function addFilesToList(event) {
        const files = event.target.files;
        const fileListDiv = document.getElementById('file-list'); // 파일 목록을 담고 있는 div

        // 삭제된 파일 개수 세기
        let deletedFileCount = 0;
        for (let i = 0; i < anoFileLists.length; i++) {
            if (anoFileLists[i].state === "delete") {
                deletedFileCount++;
            }
        }
        // 새로운 파일을 기존 파일 리스트에 추가
        fileList = anoFileLists.concat(Array.from(files));
        // 파일의 총 개수 확인
        const totalCount = fileList.length - deletedFileCount; // 삭제된 파일 제외
        if (totalCount > MAX_FILE_COUNT) {
            MessageUtil.alert('최대 '+ MAX_FILE_COUNT+'개의 파일만 업로드할 수 있습니다.');
            fileList = [];
            return;
        }

        for (let i = 0; i < files.length; i++) {
            let file = files[i];
            if (file.size > MAX_FILE_SIZE) {
                MessageUtil.alert(`파일의 최대 용량은 <c:out value="${fileSize}"/>MB 입니다.`);
                fileList = [];
                return;
            }

            const extension = file.name.split('.').pop().toLowerCase();
            if (!ALLOWED_EXTENSIONS.includes(extension)) {
                MessageUtil.alert('업로드한 파일은 허용되지 않는 파일 형식입니다.');
                fileList = [];
                return;
            }


            anoFileLists.filter(item => item.state === "delete");

            let duplicateIndex = anoFileLists.findIndex(anoFile => {
                return (anoFile.orignlFileNm === file.name || anoFile.name === file.name)&&
                    (parseInt(anoFile.fileMg, 10) === file.size || anoFile.size === file.size);
            });

            if (duplicateIndex !== -1) {
                // 조건에 맞는 파일이 있는 경우
                if (anoFileLists[duplicateIndex].state === "delete") {
                    // 파일 상태가 'delete'이면 'insert'로 변경
                    anoFileLists[duplicateIndex].state = "insert";
                } else {
                    // 그렇지 않은 경우, 경고 메시지 출력 후 함수 종료
                    MessageUtil.alert('동일한 파일이 업로드 되어있습니다.');
                    fileList = [];
                    return;
                }
            }else {
                // 중복되지 않은 경우, 새 파일을 배열에 추가하거나 다른 작업 수행
                file.state = 'insert';
                anoFileLists.push(file);

            }

            const fileDiv = document.createElement('div');
            fileDiv.classList.add('file-list-item');
            fileDiv.setAttribute('data-filename', file.name);
            fileDiv.textContent = file.name; // file.orignlFileNm을 사용해도 됩니다.

            const removeButton = document.createElement('button');
            removeButton.textContent = 'x';
            removeButton.type = 'button';
            removeButton.classList.add('remove-button');
            removeButton.onclick = function(fileName) {
                deleteFileToList(file);
                fileListDiv.removeChild(fileDiv);
            };
            fileDiv.appendChild(removeButton);

            fileListDiv.appendChild(fileDiv);


        };
        event.target.value = '';
    }

    function deleteFileToList(data) {

        for (let i = 0; i < anoFileLists.length; i++) {
            //동일한 파일 존재시 state를 delete로 바꿈, 단 insert인 경우에는 서버에 저장을 하지 않아도 되기때문에 state를 바꾸지 않
            if(isEquivalent(anoFileLists[i],data)) {
                if(anoFileLists[i].state === "insert") {
                    //삭제
                    anoFileLists.splice(i, 1);
                    break;
                }else {
                    anoFileLists[i].state = "delete";
                }
            }
        }

        const fileListDiv = document.getElementById('file-list'); // 파일 목록을 담고 있는 div
        var formData = new FormData();
        data.atchFileId = document.getElementById('atchFileId').value;
        var jsonData = JSON.stringify(data);

    }

    async function fileUpdate() {
        var insertArray = anoFileLists.filter(item => item.state === "insert");
        var deleteArray = anoFileLists.filter(item => item.state === "delete");

        // 파일 등록 및 삭제를 병렬로 처리
        const insertPromise = insertFiles(insertArray);
        const deletePromise = deleteFiles(deleteArray);

        // 파일 등록 및 삭제 작업이 모두 완료되었는지 확인
        const [insertSuccess, deleteSuccess] = await Promise.all([insertPromise, deletePromise]);

        // 모든 작업이 성공적으로 수행되었는지 확인 후 결과 반환
        return insertSuccess && deleteSuccess;
    }

    // 파일 업로드 - 파일 등록
    async function insertFiles(insertArray) {

        if (insertArray.length === 0) {
            return true; // 등록할 파일이 없으면 성공으로 간주
        }

        var formData = new FormData();
        for (var i = 0; i < insertArray.length; i++) {
            formData.append('file', insertArray[i]);
        }

        formData.append("atchFileId", document.getElementById('atchFileId').value);
        formData.append("filePath", document.getElementById('filePath').value);
        formData.append("fileKey", document.getElementById('fileKey').value);

        try {
            const response = await fetch('<c:url value="/cmm/fms/insertFileInfs.do" />', {
                method: 'POST',
                body: formData,
                processData: false,
                contentType: false,
            });
            const result = await response.json();
            if (result.status === "success") {
                var fileId = document.getElementById('atchFileId').value;
                if (fileId === '' || fileId === undefined) {
                    document.getElementById('atchFileId').value = result.atchFileId;
                }
                return true;
            } else {
                if (result.status === "bannerError") {
                    MessageUtil.alert('배너 크기는 1920x570 이어야 합니다.');
                }
                return false;
            }
        } catch (error) {
            console.error("Error during file upload:", error);
            return false;
        }
    }

    // 파일 업로드 - 파일 삭제
    async function deleteFiles(deleteArray) {

        const fileListDiv = document.getElementById('file-list').querySelectorAll('.file-list-item').length // 파일 목록을 담고 있는 div

        if(document.getElementById('filePath').value == 'banner/'){
            if (fileListDiv < 1) {
                MessageUtil.alert('배너 첨부파일은 필수로 등록해야 합니다.');
                return false;
            }
        }

        if (deleteArray.length === 0) {
            return true; // 삭제할 파일이 없으면 성공으로 간주
        }

        for (var i = 0; i < deleteArray.length; i++) {
            deleteArray[i].atchFileId = document.getElementById('atchFileId').value;
        }

        var jsonData = JSON.stringify(deleteArray);

        try {
            const response = await fetch('<c:url value="/cmm/fms/deleteFileInfModule.do" />', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: jsonData,
            });
            const result = await response.json();
            return result.status === "success";
        } catch (error) {
            console.error("Error during file deletion:", error);
            return false;
        }
    }


    function isEquivalent(obj1, obj2) {
        // 프로퍼티 이름들을 추출
        const obj1Props = Object.getOwnPropertyNames(obj1);
        const obj2Props = Object.getOwnPropertyNames(obj2);

        // 프로퍼티 개수가 다르면, 두 객체는 다릅니다.
        if (obj1Props.length !== obj2Props.length) {
            return false;
        }

        // 각 프로퍼티의 값들을 비교
        for (let i = 0; i < obj1Props.length; i++) {
            const propName = obj1Props[i];
            if (obj1[propName] !== obj2[propName]) {
                return false;
            }
        }

        return true;
    }
</script>
