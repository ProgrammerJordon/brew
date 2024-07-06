<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template_top.jspf" %>

<script>
    const uvc = {

        searchParams : {},
        uvcList : [],

        visitorLineChart : null,
        vstLabel : [],
        vstData : [],

        init : () => {
            uvc.selectVisitorCntList();
            uvc.selectVisitorCnt();
        },
        selectVisitorCnt : (se) => {
            // 일간, 주간, 월간
            let param = {se : se || 'D'}

            if (uvc.visitorLineChart != null) {
                uvc.vstLabel = [];
                uvc.vstData = [];
                uvc.visitorLineChart.destroy();
            }

            callModule.call(Util.getRequestUrl("/mng/uss/uvc/selectVisitorCnt.do"), param, (result) => {

                result.visitorCntVOList.forEach(item => {
                    uvc.vstLabel.unshift(item.rgtrDt);
                    uvc.vstData.unshift(item.count);
                });

                let visitorGraph = document.getElementById('visitorLineChart');

                uvc.visitorLineChart = new Chart(visitorGraph, {
                    type: 'line',
                    data: {
                        labels: uvc.vstLabel,
                        datasets: [{
                            label: "Vistor Count",
                            data: uvc.vstData,
                            borderWidth: 2
                        }]
                    },
                    options: {
                        scales: {
                            y: {
                                beginAtZero: true
                            }
                        }
                    }
                });

            })
        },

        selectVisitorCntList : (pageIndex) => {
            let param = {
                searchKeyword : $("#searchKeyword").val(),
                pageIndex : pageIndex
            }

            uvc.searchParams = param;

            callModule.call(Util.getRequestUrl("/mng/uss/uvc/selectVisitorCntList.do"), param, (result) => {

                uvc.uvcList = result.visitorCntVOList || [];

                $("#totCnt").text(uvc.uvcList.length.toLocaleString());

                if(uvc.uvcList.length == 0) {
                    let html = `<tr>
                                    <td colspan="3">등록된 방문자 로그가 존재하지 않습니다.</td>
                                </tr>`
                    $("tbody").append(html);
                    return false;
                }

                gridModule.clear_grid("tbody");

                for(let i = 0; i < uvc.uvcList.length; i++) {
                    if (uvc.uvcList[i].rnum > 10) break;

                    let html = `<tr>
                                    <td>\${uvc.uvcList[i].logId}</td>
                                    <td>\${uvc.uvcList[i].clientIp}</td>
                                    <td>\${uvc.uvcList[i].rgtrDt}</td>
                              </tr>`

                    $("tbody").append(html);
                }
                $('#pagination').page(1, gridModule.getPageSize(uvc.uvcList), 'uvc.pageMove');
            })
        },
        pageMove: function(pageIndex) {
            if (!pageIndex) return;

            gridModule.clear_grid("tbody");

            uvc.uvcList.filter(vo => vo.rnum >= ((pageIndex - 1) * 10 + 1) && vo.rnum <= (pageIndex * 10)).forEach(vo => {
                let html = `<tr>
                                <td>\${vo.logId}</td>
                                <td>\${vo.clientIp}</td>
                                <td>\${vo.rgtrDt}</td>
                           </tr>`
                $("tbody").append(html);
            });
            $('#pagination').page(pageIndex, gridModule.getPageSize(uvc.uvcList), 'uvc.pageMove');
        },
    }

    $(() => {
        uvc.init();
        mmm.selectMenu('uss', '방문자수');
    })

</script>

<div>
    <div class="mb-24">
        <div>
            <div class="btn__box" style="display: flex; justify-content: right;">
                <div class="mr-16">
                    <button class="btn__black__line" onclick="uvc.selectVisitorCnt('D')">
                        <span>DAY</span>
                    </button>
                </div>
                <div class="mr-16">
                    <button class="btn__black__line" onclick="uvc.selectVisitorCnt('W')">
                        <span>WEEK</span>
                    </button>
                </div>
                <div>
                    <button class="btn__black__line" onclick="uvc.selectVisitorCnt('M')">
                        <span>MONTH</span>
                    </button>
                </div>
            </div>
        </div>
        <br>
        <div>
            <canvas id="visitorLineChart" width="800" height="200"></canvas>
        </div>
    </div>
    <div class="search-box">
        <ul style="margin-right: 2%;">
            <li>
                <div class="search__type__input">
                    <label for="searchKeyword" class="search__title">IP 검색</label>
                    <input id="searchKeyword" name="searchKeyword" />
                </div>
            </li>
        </ul>
        <button class="btn__search icon" onclick="uvc.selectVisitorCntList();">
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
            <caption class="hidden">방문자수 목록</caption>
            <colgroup>
                <col class="num" width="40%">
                <col class="num" width="40%">
                <col class="num" width="20%">
            </colgroup>
            <thead>
            <tr>
                <th>로그ID</th>
                <th>클라이언트IP</th>
                <th>일시</th>
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