<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template_top.jspf" %>

<script>
    const faq = {

        searchParams : {},
        faqList : [],

        init : () => {
            faq.selectFaqList();
        },

        insertFaqInsertVw : () => {
            callModule.post(Util.getRequestUrl("/mng/cmm/faq/insertFaqInsertVw.do"), {}, 'get')
        },

        selectFaqDtlsVw : (sn) => {
            let param = {sn : sn}
            callModule.post(Util.getRequestUrl("/mng/cmm/faq/selectFaqDtlsVw.do"), param, 'get')
        },

        selectFaqList : (pageIndex) => {
            let param = {
                pageIndex : pageIndex || '1'
            }

            faq.searchParams = param;

            callModule.call(Util.getRequestUrl("/mng/cmm/faq/selectFaqList.do"), param, (result) => {

                faq.faqList = result.faqVOList || [];

                $("#totCnt").text(faq.faqList.length.toLocaleString());

                gridModule.clear_grid("tbody");

                if(faq.faqList.length == 0) {
                    let html = `<tr>
                                    <td colspan="4">등록된 FAQ가 존재하지 않습니다.</td>
                                </tr>`
                    $("tbody").append(html);
                    return false;
                }

                for(let i = 0; i < faq.faqList.length; i++) {
                    if (faq.faqList[i].rnum > 10) break;

                    let html = `<tr onclick="faq.selectFaqDtlsVw(\${faq.faqList[i].sn});">
                                <td>\${faq.faqList[i].title}</td>
                                <td>\${faq.faqList[i].inqCnt}</td>
                                <td>\${faq.faqList[i].rgtrId}</td>
                                <td>\${faq.faqList[i].rgtrDt}</td>
                              </tr>`

                    $("tbody").append(html);
                }
                $('#pagination').page(1, gridModule.getPageSize(faq.faqList), 'faq.pageMove');
            })
        },

        pageMove: function(pageIndex) {
            if (!pageIndex) return;

            gridModule.clear_grid("tbody");

            faq.faqList.filter(vo => vo.rnum >= ((pageIndex - 1) * 10 + 1) && vo.rnum <= (pageIndex * 10)).forEach(vo => {

                let html = `<tr onclick="faq.selectFaqDtlsVw(\${vo.sn})">
                                <td>\${vo.title}</td>
                                <td>\${vo.inqCnt}</td>
                                <td>\${vo.rgtrId}</td>
                                <td>\${vo.rgtrDt}</td>
                           </tr>`
                $("tbody").append(html);
            });
            $('#pagination').page(pageIndex, gridModule.getPageSize(faq.faqList), 'faq.pageMove');
        },

    }

    $(() => {
        faq.init();
        mmm.selectMenu('cmm', 'FAQ관리');
    })
</script>

<div>
    <div class="search__results">
        <div>
            <span>총</span>
            <span class="num" id="totCnt">0</span>
            <span>건</span>
        </div>
        <div class="btn__box">
            <button class="btn__bluegreen" onclick="faq.insertFaqInsertVw();">
                <span>등록</span>
            </button>
        </div>
    </div>
    <div class="table-box">
        <table>
            <caption class="hidden">관리자 게시판 목록</caption>
            <colgroup>
                <col class="num" width="60%">
                <col class="num" width="10%">
                <col class="num" width="15%">
                <col class="num" width="15%">
            </colgroup>
            <thead>
            <tr>
                <th>질문사항</th>
                <th>조회수</th>
                <th>등록자</th>
                <th>등록일자</th>
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