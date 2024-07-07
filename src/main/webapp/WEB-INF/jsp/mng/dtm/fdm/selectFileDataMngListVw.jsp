<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template_top.jspf" %>

<script>
    const fdm = {

        searchParams : {},
        fdmList : [],

        init : () => {
            fdm.selectFileDataMngList();
        },
        downloadAtchFile : (atchFileId, fileSn) => {
            let param = {
                atchFileId : atchFileId,
                fileSn : fileSn
            }
            callModule.post(Util.getRequestUrl("/mng/dtm/fdm/downloadAtchFile.do"), param, 'post');
        },

        selectFileDataMngList : (pageIndex) => {
            let param = {
                searchKeyword : $("#searchKeyword").val(),
                pageIndex : pageIndex || '1'
            }
            callModule.call(Util.getRequestUrl("/mng/dtm/fdm/selectFileDataMngList.do"), param, (result) => {

                fdm.fdmList = result.fileDataMngVOList || [];

                $("#totCnt").text(fdm.fdmList.length.toLocaleString());

                gridModule.clear_grid("tbody");

                if(fdm.fdmList.length == 0) {
                    let html = `<tr>
                                    <td colspan="4">등록된 파일데이터가 존재하지 않습니다.</td>
                                </tr>`
                    $("tbody").append(html);
                    return false;
                }

                for(let i = 0; i < fdm.fdmList.length; i++) {
                    if (fdm.fdmList[i].rnum > 10) break;

                    let html = `<tr>
                                    <td>\${fdm.fdmList[i].orignlFileNm}</td>
                                    <td>\${fdm.fdmList[i].fileSize}</td>
                                    <td>\${fdm.fdmList[i].rgtrDt}</td>
                                    <td>
                                        <div>
                                            <button class="btn__bluegreen" onclick="fdm.downloadAtchFile('\${fdm.fdmList[i].atchFileId}', '\${fdm.fdmList[i].fileSn}')">\${fdm.fdmList[i].fileExtsn}</button>
                                        </div>
                                    </td>
                               </tr>`

                    $("tbody").append(html);
                }
                $('#pagination').page(1, gridModule.getPageSize(fdm.fdmList), 'fdm.pageMove');
            })
        },
        pageMove: function(pageIndex) {
            if (!pageIndex) return;

            gridModule.clear_grid("tbody");

            fdm.fdmList.filter(vo => vo.rnum >= ((pageIndex - 1) * 10 + 1) && vo.rnum <= (pageIndex * 10)).forEach(vo => {
                let html = `<tr>
                                <td>\${vo.orignlFileNm}</td>
                                <td>\${vo.fileSize}</td>
                                <td>\${vo.rgtrDt}</td>
                                <td>
                                    <div>
                                        <button class="btn__bluegreen" onclick="fdm.downloadAtchFile('\${vo.atchFileId}', '\${vo.fileSn}')">\${vo.fileExtsn}</button>
                                    </div>
                                </td>
                           </tr>`
                $("tbody").append(html);
            });
            $('#pagination').page(pageIndex, gridModule.getPageSize(fdm.fdmList), 'fdm.pageMove');
        },
    }

    $(() => {
        fdm.init();
        mmm.selectMenu('dtm', '파일데이터관리')
    })
</script>

<div>
    <div class="search-box">
        <ul style="margin-right: 2%;">
            <li>
                <div class="search__type__input">
                    <label for="searchKeyword" class="search__title">파일검색</label>
                    <input id="searchKeyword" name="searchKeyword" />
                </div>
            </li>
        </ul>
        <button class="btn__search icon" onclick="fdm.selectFileDataMngList();">
            <span>조회</span>
        </button>
    </div>
    <br>
    <div class="search__results">
        <div>
            <span>총</span>
            <span class="num" id="totCnt">0</span>
            <span>건</span>
        </div>
    </div>
    <div class="table-box">
        <table>
            <caption class="hidden">파일데이터 관리 목록</caption>
            <colgroup>
                <col class="num" width="55%">
                <col class="num" width="15%">
                <col class="num" width="15%">
                <col class="num" width="15%">
            </colgroup>
            <thead>
                <tr>
                    <th>파일명</th>
                    <th>파일크기</th>
                    <th>등록일자</th>
                    <th>다운로드</th>
                </tr>
            </thead>
            <tbody id="tbody"></tbody>
        </table>
    </div>
    <div class="paging-area">
        <div id="pagination" class="paging"></div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template-bottom.jspf" %>
