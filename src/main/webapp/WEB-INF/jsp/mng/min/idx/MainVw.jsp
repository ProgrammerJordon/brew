<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template_top.jspf" %>

<script>
    const min = {
        init : () => {
            min.selectBoardList();
            min.selectFaqList();
        },

        selectBoardListVw : () => {
            callModule.post(Util.getRequestUrl("/mng/cmm/bbs/selectBoardListVw.do"), {}, 'get');
        },

        selectFaqListVw : () => {
            callModule.post(Util.getRequestUrl("/mng/cmm/faq/selectFaqListVw.do"), {}, 'get');
        },

        selectBoardDtlsVw : (sn) => {
            let param = {sn : sn}
            callModule.post(Util.getRequestUrl("/mng/cmm/bbs/selectBoardDtlsVw.do"), param, 'get');
        },

        selectFaqDtlsVw : (sn) => {
            let param = {sn : sn}
            callModule.post(Util.getRequestUrl("/mng/cmm/faq/selectFaqDtlsVw.do"), param, 'get');
        },

        selectBoardList : () => {
            let param = {mainYn : 'Y'}
            callModule.call(Util.getRequestUrl("/mng/min/idx/selectBoardList.do"), param, (result) => {

                for(var i = 0; i < result.boardVOList.length; i++) {
                    let html = `<tr style="cursor: pointer;" onclick="min.selectBoardDtlsVw('\${result.boardVOList[i].sn}');">
                                    <td colspan="2">
                                        <div style="display: flex; justify-content: space-between; align-items: center;">
                                            <div style="width: 70%; text-align: left;">
                                                <div style="font-size: 1.5em;">\${result.boardVOList[i].title}</div>
                                                <div>\${result.boardVOList[i].contents}</div>
                                            </div>
                                            <div style="width: 15%">
                                                <div style="text-align: center;">
                                                    <span>조회수 : </span>\${result.boardVOList[i].inqCnt}
                                                </div>
                                            </div>
                                            <div style="width: 15%">
                                                <div>\${result.boardVOList[i].rgtrDt}</div>
                                            </div>
                                        <div>
                                    </td>
                                </tr>`;
                    $("#tbody1").append(html);
                }
            })
        },
        selectFaqList : () => {
            let param = {mainYn : 'Y'}
            callModule.call(Util.getRequestUrl("/mng/min/idx/selectFaqList.do"), param, (result) => {

                for(var i = 0; i < result.faqVOList.length; i++) {
                    let html = `<tr style="cursor: pointer;" onclick="min.selectFaqDtlsVw('\${result.faqVOList[i].sn}');">
                                    <td colspan="2">
                                        <div style="display: flex; justify-content: space-between; align-items: center;">
                                            <div style="width: 70%; text-align: left;">
                                                <div style="font-size: 1.5em;">\${result.faqVOList[i].title}</div>
                                                <div>\${result.faqVOList[i].contents}</div>
                                            </div>
                                            <div style="width: 15%">
                                                <div style="text-align: center;">
                                                    <span>조회수 : </span>\${result.faqVOList[i].inqCnt}
                                                </div>
                                            </div>
                                            <div style="width: 15%">
                                                <div>\${result.faqVOList[i].rgtrDt}</div>
                                            </div>
                                        <div>
                                    </td>
                                </tr>`;
                    $("#tbody2").append(html);
                }
            })
        }
    }

    $(() => {
        min.init();
        mmm.selectMenu('bpm')
    })
</script>
<div>
    <div class="dashboard">
        <div style="display: flex; justify-content: space-around;">
            <div>
                <canvas id="chart1" width="400" height="400"></canvas>
            </div>
            <div>
                <canvas id="bar1" width="400" height="400"></canvas>
            </div>
        </div>
        <br>
        <div style="display: flex; justify-content: space-around;">
            <div>
                <canvas id="line1" width="800" height="200"></canvas>
            </div>
        </div>
    </div>
    <br>
    <div style="display: flex; justify-content: space-between">
        <div style="width: 48%">
            <div class="table-box">
                <table>
                    <caption class="hidden">관리자 게시판 목록</caption>
                    <colgroup>
                        <col class="num" width="80%">
                        <col class="num" width="20%">
                    </colgroup>
                    <thead>
                    <tr>
                        <th class="req">공지사항</th>
                        <th>
                            <div>
                                <button class="btn__black__line" onclick="min.selectBoardListVw();">
                                    <span>더보기</span>
                                </button>
                            </div>
                        </th>
                    </tr>
                    </thead>
                    <tbody id="tbody1"></tbody>
                </table>
            </div>
        </div>
        <div style="width: 48%">
            <div class="table-box">
                <table>
                    <caption class="hidden">관리자 FAQ 목록</caption>
                    <colgroup>
                        <col class="num" width="80%">
                        <col class="num" width="20%">
                    </colgroup>
                    <thead>
                    <tr>
                        <th class="req">FAQ</th>
                        <th>
                            <div>
                                <button class="btn__black__line" onclick="min.selectFaqListVw();">
                                    <span>더보기</span>
                                </button>
                            </div>
                        </th>
                    </tr>
                    </thead>
                    <tbody id="tbody2"></tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template-bottom.jspf" %>

<!-- chart.js -->
<script>
    // 추후 통계데이터 주입예정
    let graph1 = document.getElementById('chart1').getContext('2d');
    let chart1 = new Chart(graph1, {
        type: 'doughnut',
        data: {
            labels: [
                'Red',
                'Blue',
                'Yellow'
            ],
            datasets: [{
                label: 'My First Dataset',
                data: [300, 50, 100],
                backgroundColor: [
                    'rgb(255, 99, 132)',
                    'rgb(54, 162, 235)',
                    'rgb(255, 205, 86)'
                ],
                hoverOffset: 4
            }]
        }
    });

    let graph2 = document.getElementById('bar1').getContext('2d');
    let bar1 = new Chart(graph2, {
        type: 'bar',
        data: {
            labels: [
                'Red',
                'Blue',
                'Yellow'
            ],
            datasets: [{
                label: 'My First Dataset',
                data: [300, 50, 100],
                backgroundColor: [
                    'rgb(255, 99, 132)',
                    'rgb(54, 162, 235)',
                    'rgb(255, 205, 86)'
                ],
                hoverOffset: 4
            }]
        }
    });

    let graph3 = document.getElementById('line1').getContext('2d');
    let line1 = new Chart(graph3, {
        type: 'line',
        data: {
            labels: [
                'Red',
                'Blue',
                'Yellow'
            ],
            datasets: [{
                label: 'My First Dataset',
                data: [300, 50, 100],
                backgroundColor: [
                    'rgb(255, 99, 132)',
                    'rgb(54, 162, 235)',
                    'rgb(255, 205, 86)'
                ],
                hoverOffset: 4
            }]
        }
    });

</script>