<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template_top.jspf" %>

<script>
    const cam = {
        init : () => {
            cam.addHeader();
            cam.addParam();
        },
        selectCollectApiResponse : () => {

            let param = {
                method : $("#method").val(),
                url : $("#url").val(),
                headers : cam.getHeadersJson(),
                params : cam.getParamsJson()
            }
            callModule.call(Util.getRequestUrl("/mng/dtm/cam/selectCollectApiResponse.do"), param, (result) => {
                let el = document.getElementById('res');
                el.value = "";
                let res = JSON.parse(result.collectApiVO.res);
                el.value = JSON.stringify(res, null, 2);
            })
        },
        getHeadersJson: () => {
            var tbody = document.getElementById('tbody1');
            var headers = {};
            for (let i = 0; i < tbody.rows.length; i++) {
                var rowNum = i + 1;
                var checkbox = document.getElementById(`headerCheck\${rowNum}`);
                if (checkbox.checked) {
                    var key = document.getElementById(`headerKey\${rowNum}`).value;
                    var value = document.getElementById(`headerValue\${rowNum}`).value;
                    if (key && value) {
                        headers[key] = value;
                    }
                }
            }
            return headers;
        },
        getParamsJson: () => {
            var tbody = document.getElementById('tbody2');
            var params = {};
            for (let i = 0; i < tbody.rows.length; i++) {
                var rowNum = i + 1;
                var checkbox = document.getElementById(`paramCheck\${rowNum}`);
                if (checkbox.checked) {
                    var key = document.getElementById(`paramKey\${rowNum}`).value;
                    var value = document.getElementById(`paramValue\${rowNum}`).value;
                    if (key && value) {
                        params[key] = value;
                    }
                }
            }
            return params;
        },
        addHeader: () => {
            var tbody = document.getElementById('tbody1');
            var rowCount = tbody.rows.length;
            var newRow = tbody.insertRow(rowCount);

            var cell1 = newRow.insertCell(0);
            var cell2 = newRow.insertCell(1);
            var cell3 = newRow.insertCell(2);
            var cell4 = newRow.insertCell(3);

            var rowNum = rowCount + 1;

            newRow.id = `headerRow\${rowNum}`;

            cell1.innerHTML = `<input type="checkbox" id="headerCheck\${rowNum}" checked onchange="cam.disableRow('header', \${rowNum})" />`;
            cell2.innerHTML = `<input type="text" id="headerKey\${rowNum}" placeholder="Key">`;
            cell3.innerHTML = `<input type="text" id="headerValue\${rowNum}" placeholder="Value">`;
            cell4.innerHTML = `<button onclick="cam.deleteHeader(\${rowNum})" class="btn__delete">Delete</button>`;
        },
        deleteHeader: (rowNum) => {
            var row = document.getElementById('headerRow' + rowNum);
            row.parentNode.removeChild(row);

            var tbody = document.getElementById('tbody1');
            for (let i = 0; i < tbody.rows.length; i++) {
                var newRowNum = i + 1;
                var currentRow = tbody.rows[i];

                currentRow.id = 'headerRow' + newRowNum;

                var checkbox = currentRow.querySelector('input[id^="headerCheck"]');
                var headerKeyInput = currentRow.querySelector('input[id^="headerKey"]');
                var headerValueInput = currentRow.querySelector('input[id^="headerValue"]');
                var deleteButton = currentRow.querySelector('button[class="btn__delete"]');

                checkbox.id = 'headerCheck' + newRowNum;
                headerKeyInput.id = 'headerKey' + newRowNum;
                headerValueInput.id = 'headerValue' + newRowNum;
                deleteButton.setAttribute('onclick', `cam.deleteHeader(\${newRowNum})`);
            }
        },
        addParam: () => {
            var tbody = document.getElementById('tbody2');
            var rowCount = tbody.rows.length;
            var newRow = tbody.insertRow(rowCount);

            var cell1 = newRow.insertCell(0);
            var cell2 = newRow.insertCell(1);
            var cell3 = newRow.insertCell(2);
            var cell4 = newRow.insertCell(3);

            var rowNum = rowCount + 1;

            newRow.id = `paramRow\${rowNum}`;

            cell1.innerHTML = `<input type="checkbox" id="paramCheck\${rowNum}" checked onchange="cam.disableRow('param', \${rowNum})"  />`;
            cell2.innerHTML = `<input type="text" id="paramKey\${rowNum}" placeholder="Key">`;
            cell3.innerHTML = `<input type="text" id="paramValue\${rowNum}" placeholder="Value">`;
            cell4.innerHTML = `<button onclick="cam.deleteParam(\${rowNum})" class="btn__delete">Delete</button>`;
        },
        deleteParam: (rowNum) => {
            var row = document.getElementById('paramRow' + rowNum);
            row.parentNode.removeChild(row);

            var tbody = document.getElementById('tbody2');
            for (let i = 0; i < tbody.rows.length; i++) {
                var newRowNum = i + 1;
                var currentRow = tbody.rows[i];

                currentRow.id = 'paramRow' + newRowNum;

                var checkbox = currentRow.querySelector('input[id^="paramCheck"]');
                var paramKeyInput = currentRow.querySelector('input[id^="paramKey"]');
                var paramValueInput = currentRow.querySelector('input[id^="paramValue"]');
                var deleteButton = currentRow.querySelector('button[class="btn__delete"]');

                checkbox.id = 'paramCheck' + newRowNum;
                paramKeyInput.id = 'paramKey' + newRowNum;
                paramValueInput.id = 'paramValue' + newRowNum;
                deleteButton.setAttribute('onclick', `cam.deleteParam(\${newRowNum})`);
            }
        },
        disableRow: (type, rowNum) => {
            var isChecked = document.getElementById(`\${type}Check\${rowNum}`).checked;
            document.getElementById(`\${type}Key\${rowNum}`).disabled = !isChecked;
            document.getElementById(`\${type}Value\${rowNum}`).disabled = !isChecked;
        }
    }

    $(() => {
        cam.init();
        mmm.selectMenu('dtm', '수집API관리')
    })
</script>

<div>
    <div class="search-box">
        <ul style="margin-right: 2%;">
            <li>
                <div class="search__type__select">
                    <label for="method" class="search__title">요청</label>
                    <select id="method">
                        <option value="GET">GET</option>
                        <option value="POST">POST</option>
                        <option value="PUT">PUT</option>
                        <option value="DELETE">DELETE</option>
                        <option value="PATCH">PATCH</option>
                        <option value="HEAD">HEAD</option>
                        <option value="OPTIONS">OPTIONS</option>
                    </select>
                </div>
                <div class="search__type__input">
                    <label for="url" class="search__title">URL</label>
                    <input id="url" name="url" value="https://jsonplaceholder.typicode.com/posts" placeholder="http://www.brew.com" />
                </div>
            </li>
        </ul>
        <button class="btn__search" onclick="cam.selectCollectApiResponse();">
            <span>Send</span>
        </button>
    </div>
    <br>
    <div class="search__results">
        <div>
            <span>Headers</span>
        </div>
        <div class="btn__box">
            <button class="btn__black__line" onclick="cam.addHeader();">Add Row</button>
        </div>
    </div>
    <div class="table-box">
        <table>
            <caption class="hidden">Headers</caption>
            <colgroup>
                <col class="num" width="10%">
                <col class="num" width="30%">
                <col class="num" width="50%">
                <col class="num" width="10%">
            </colgroup>
            <thead>
            <tr>
                <th></th>
                <th>Key</th>
                <th>Value</th>
                <th></th>
            </tr>
            </thead>
            <tbody id="tbody1"></tbody>
        </table>
    </div>
    <br>
    <div class="search__results">
        <div>
            <span>Params</span>
        </div>
        <div class="btn__box">
            <button class="btn__black__line" onclick="cam.addParam();">Add Row</button>
        </div>
    </div>
    <div class="table-box">
        <table>
            <caption class="hidden">Params</caption>
            <colgroup>
                <col class="num" width="10%">
                <col class="num" width="30%">
                <col class="num" width="50%">
                <col class="num" width="10%">
            </colgroup>
            <thead>
            <tr>
                <th></th>
                <th>Key</th>
                <th>Value</th>
                <th></th>
            </tr>
            </thead>
            <tbody id="tbody2"></tbody>
        </table>
    </div>
    <br>
    <div class="search__results">
        <div>
            <span>Response</span>
        </div>
    </div>
    <div>
        <textarea id="res" style="height: 500px;" readonly></textarea>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template-bottom.jspf" %>
