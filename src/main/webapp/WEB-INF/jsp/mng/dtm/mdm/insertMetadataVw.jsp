<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template_top.jspf" %>

<script>
    let mdm = {
        init : () => {

        },
        selectMetadataListVw : () => {
            callModule.post(Util.getRequestUrl("/mng/dtm/mdm/selectMetadataListVw.do"), {}, 'post');
        },
        insertMetadata : () => {


            // var validationGroup = [
            //     {id: 'datasetNm', name: '데이터셋명', mandatory: true},
            //     {id: 'datasetEngNm', name: '데이터셋영문명', mandatory: true},
            // ];
            //
            // if (!Util.validateComponent(validationGroup)) return;

            MessageUtil.confirm("메타데이터를 등록하시겠습니까?", async (boolean) => {

                if(boolean) {
                    var fileResult = await fileUpdate();

                    if(fileResult) {
                        let param = {
                            datasetNm : $("#datasetNm").val(),
                            datasetEngNm : $("#datasetEngNm").val(),
                            datasetClsfCd : $("#datasetClsfCd").val(),
                            authCd : $("#authCd").val(),
                            sceLevel : $("#sceLevel").val(),
                            deptNm : $("#deptNm").val(),
                            explain : $("#explain").val(),
                            atchFileId : $("#atchFileId").val() || null,
                        }
                        callModule.call(Util.getRequestUrl("/mng/dtm/mdm/insertMetadata.do"), param, (result) => {

                            MessageUtil.alert(result.metadataVO.resultMessage, () => {
                                mdm.selectMetadataListVw();
                            });

                        })
                    }
                }

            }, "등록", "취소");
        }
    }

    $(() => {
        mdm.init();
        mmm.selectMenu("dtm", "메타데이터 등록")
    })
</script>

<div>
    <div class="table-box">
        <table>
            <caption class="hidden">메타데이터 등록 화면</caption>
            <colgroup>
                <col class="num" width="20%">
                <col class="num" width="30%">
                <col class="num" width="20%">
                <col class="num" width="30%">
            </colgroup>
            <thead></thead>
            <tbody>
            <tr>
                <th>데이터셋명</th>
                <td>
                    <input type="text" id="datasetNm" name="datasetNm" />
                </td>
                <th>데이터셋영문명</th>
                <td>
                    <input type="text" id="datasetEngNm" name="datasetEngNm" />
                </td>
            </tr>
            <tr>
                <th>분류</th>
                <td>
                    <select class="selectBox" id="datasetClsfCd">
                        <option value="99" selected>기타</option>
                        <option value="01">통계</option>
                    </select>
                </td>
                <th>권한</th>
                <td>
                    <select class="selectBox" id="authCd">
                        <option value="Z" selected>일반사용자</option>
                        <option value="A">관리자</option>
                    </select>
                </td>
            </tr>
            <tr>
                <th>보안레벨</th>
                <td>
                    <select class="selectBox" id="sceLevel">
                        <option value="01" selected>공개</option>
                        <option value="03">내부</option>
                        <option value="02">대외비</option>
                        <option value="04">기밀</option>
                    </select>
                </td>
                <th>관리부서</th>
                <td>
                    <div style="display: flex; justify-content: space-between; align-items: center;">
                        <div>
                            <input type="text" id="deptNm" name="deptNm" readonly style="width: 250px;"/>
                        </div>
                        <div class="btn__box">
                            <button class="btn__black__line">
                                <span>검색</span>
                            </button>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <th>설명</th>
                <td colspan="3">
                    <textarea id="explain" name="explain"></textarea>
                </td>
            </tr>
            <tr>
                <th><label for="file-upload">첨부파일</label></th>
                <td class="left" colspan="3">
                    <div>
                        <c:import url="/cmm/fms/selectFileInfsForUpdate.do" charEncoding="utf-8">
                            <c:param name="atchFileId" value="" />
                            <c:param name="ext" value="image/gif,image/jpeg,image/png,jpg,jpeg,png,xls,xlsx,hwp,pdf,brf,docx,pptx,csv,zip,txt" />
                            <c:param name="fileSize" value="100" />
                            <c:param name="fileCount" value="10" />
                            <c:param name="filePath" value="MDM/" />
                            <c:param name="fileKey" value="MDM_" />
                        </c:import>
                    </div>
                    <div>
                        <span style="font-size: 10px; color: red;">* 대용량의 파일은 ZIP파일로 압축 후 업로드하세요.</span>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="btn">
        <div class="btn__box">
            <div class="left">
                <button class="btn__gray" onclick="mdm.selectMetadataListVw();">
                    <span>목록</span>
                </button>
            </div>
            <div class="right">
                <button class="btn__bluegreen" onclick="mdm.insertMetadata();">
                    <span>등록</span>
                </button>
            </div>
        </div>
    </div>
</div>


<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template-bottom.jspf" %>