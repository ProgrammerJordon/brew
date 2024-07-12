<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template_top.jspf" %>


<script>
    const iim = {
        init : () => {

        },
        insertItemInfoBatch : () => {
            MessageUtil.confirm("종목정보 배치를 실행하시겠습니까?", (boolean) => {
                if(boolean) {
                    let param = {};
                    callModule.call(Util.getRequestUrl("/mng/trd/iim/insertItemInfoBatch.do"), param, (result) => {
                        debugger;
                        MessageUtil.alert(result.itemInfoVO.resultMessage, () => {

                        })
                    })
                }
            }, "실행", "취소")
        }
    }

    $(() => {
        iim.init();
        mmm.selectMenu("trd")
    })
</script>

<div>
    <div class="search-box">
        <ul style="margin-right: 2%;">
            <li>
                <div class="search__type__select">
                    <label for="mrktCtg" class="search__title">Market</label>
                    <select id="mrktCtg" name="mrktCtg">
                        <option value="">TOTAL</option>
                        <option value="KOSPI">KOSPI</option>
                        <option value="KOSDAQ">KOSDAQ</option>
                        <option value="KONEX">KONEX</option>
                    </select>
                </div>
                <div class="search__type__input">
                    <label for="searchKeyword" class="search__title">Search</label>
                    <input id="searchKeyword" name="searchKeyword" />
                </div>
            </li>
        </ul>
        <button class="btn__search icon" onclick="">
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
        <div class="btn__box">
            <button class="btn__bluegreen" onclick="iim.insertItemInfoBatch();">
                <span>ItemInfo Batch</span>
            </button>
        </div>
    </div>
    <div class="table-box">
        <table>
            <caption class="hidden">종목 리스트</caption>
            <colgroup>
                <col class="num" width="" >
            </colgroup>
            <thead></thead>
            <tbody></tbody>
        </table>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template-bottom.jspf" %>