document.addEventListener("DOMContentLoaded", async function () {
    const response = await fetch("/api/env-data");
    const jsonData = await response.json();
    console.log("jsonData Sample:", jsonData);

    // 날짜 기준 정렬 (최신순)
    Object.keys(jsonData).forEach(sensor => {
        jsonData[sensor].sort((a, b) => new Date(b.time) - new Date(a.time));
    });

    // 날짜 문자열 추출 (예: "2025/04/01")
    function extractDateString(dataList) {
        if (dataList.length === 0) return '';
        const fullDate = dataList[0].time;
        const [date] = fullDate.split("T");
        return date.replace(/-/g, "/");
    }

    function drawLineChart(canvasId, label, dataList) {
        if (!dataList || dataList.length === 0) return;

        const labels = dataList.map(item => {
            const timePart = item.time?.split("T")[1] || "시간없음";
            return timePart;
        });

        const data = dataList.map(item => item.value);
        const dateStr = extractDateString(dataList);
        const ctx = document.getElementById(canvasId).getContext("2d");

        new Chart(ctx, {
            type: 'line',
            data: {
                labels: labels.reverse(),
                datasets: [{
                    label: `${label} (${dateStr})`,
                    data: data.reverse(),
                    borderColor: 'rgba(0, 97, 242, 1)',
                    backgroundColor: 'rgba(0, 97, 242, 0.1)',
                    fill: false,
                    tension: 0.1
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        display: true,
                        align: 'start',
                        labels: { color: '#363d47' }
                    }
                },
                scales: {
                    x: {
                        title: { display: true, text: '시간' }
                    },
                    y: {
                        title: { display: true, text: label }
                    }
                }
            }
        });
    }

    // 센서 상태 기준
    function getSensorStatus(type, value) {
        switch (type) {
            case 'TMP': return value >= 25 && value <= 35 ? '정상' : (value >= 20 && value <= 38 ? '경고' : '위험');
            case 'REH': return value >= 40 && value <= 60 ? '정상' : (value >= 30 && value <= 70 ? '경고' : '위험');
            case 'PCP': return value === 0 ? '정상' : (value <= 1 ? '경고' : '위험');
            case 'SUN': return value >= 6 ? '정상' : (value >= 3 ? '경고' : '위험');
            default: return 'N/A';
        }
    }

    function getStatusColorClass(status) {
        return {
            '정상': 'bg-success',
            '경고': 'bg-warning text-dark',
            '위험': 'bg-danger'
        }[status] || 'bg-secondary';
    }

    function makeBadge(status) {
        const colorClass = getStatusColorClass(status);
        return `<span class="badge ${colorClass}" style="margin-left: 6px;">${status}</span>`;
    }

    function getStatusClass(status) {
        return {
            '정상': 'text-success',
            '경고': 'text-warning',
            '위험': 'text-danger'
        }[status] || 'text-muted';
    }

    function renderSensorLogTable(dataMap) {
        const logBody = document.getElementById("sensor-log-body");
        if (!logBody) return;

        const length = dataMap.TMP?.length || 0;
        let html = "";

        for (let i = 0; i < length; i++) {
            const time = dataMap.TMP[i]?.time || '시간없음';
            const TMP = parseFloat(dataMap.TMP?.[i]?.value ?? "-");
            const REH = parseFloat(dataMap.REH?.[i]?.value ?? "-");
            const PCP = parseFloat(dataMap.PCP?.[i]?.value ?? "-");
            const SUN = parseFloat(dataMap.SUN?.[i]?.value ?? "-");

            const tmpStatus = isNaN(TMP) ? 'N/A' : getSensorStatus('TMP', TMP);
            const rehStatus = isNaN(REH) ? 'N/A' : getSensorStatus('REH', REH);
            const pcpStatus = isNaN(PCP) ? 'N/A' : getSensorStatus('PCP', PCP);
            const sunStatus = isNaN(SUN) ? 'N/A' : getSensorStatus('SUN', SUN);

            const overallStatus = [tmpStatus, rehStatus, pcpStatus, sunStatus].includes('위험') ? '위험'
                : [tmpStatus, rehStatus, pcpStatus, sunStatus].includes('경고') ? '경고'
                : '정상';

            const memo = [];
            if (tmpStatus !== '정상') memo.push(`온도(${tmpStatus})`);
            if (rehStatus !== '정상') memo.push(`습도(${rehStatus})`);
            if (pcpStatus !== '정상') memo.push(`강수량(${pcpStatus})`);
            if (sunStatus !== '정상') memo.push(`일조량(${sunStatus})`);

            html += `
                <tr>
                    <td>${time}</td>
                    <td class="${getStatusClass(overallStatus)} fw-bold">${overallStatus}</td>
                    <td>${isNaN(TMP) ? '-' : TMP + '℃'} ${makeBadge(tmpStatus)}</td>
                    <td>${isNaN(REH) ? '-' : REH + '%'} ${makeBadge(rehStatus)}</td>
                    <td>${isNaN(PCP) ? '-' : PCP + 'mm'} ${makeBadge(pcpStatus)}</td>
                    <td>${isNaN(SUN) ? '-' : SUN + 'h'} ${makeBadge(sunStatus)}</td>
                    <td>${memo.length ? memo.join(', ') : '-'}</td>
                </tr>
            `;
        }

        logBody.innerHTML = html;
    }

    // --- ✅ 날짜별 페이지네이션 + 그래프 연동 ---
    const groupedByDate = {};
    for (let i = 0; i < jsonData.TMP.length; i++) {
        const date = jsonData.TMP[i]?.time?.split("T")[0];
        if (!groupedByDate[date]) groupedByDate[date] = [];
        groupedByDate[date].push(i);
    }

    const dateKeys = Object.keys(groupedByDate);
    let currentPage = 0;

    function updatePage(pageIndex) {
        currentPage = pageIndex;
        const indices = groupedByDate[dateKeys[pageIndex]];

        const filteredData = {};
        ['TMP', 'REH', 'PCP', 'SUN', 'WSD'].forEach(sensor => {
            filteredData[sensor] = indices.map(i => jsonData[sensor]?.[i]).filter(Boolean);
        });

        renderSensorLogTable(filteredData);

        drawLineChart('chart1', '온도 (℃)', filteredData.TMP);
        drawLineChart('chart2', '습도 (%)', filteredData.REH);
        drawLineChart('chart3', '풍속 (m/s)', filteredData.WSD);
        drawLineChart('chart4', '강수량 (mm)', filteredData.PCP);
        drawLineChart('chart5', '일조량 (h)', filteredData.SUN);

        renderPagination();
    }

    function renderPagination() {
        const paginationContainer = document.getElementById("pagination");
        if (!paginationContainer) return;

        let html = "<nav><ul class='pagination mb-0'>";
        for (let i = 0; i < dateKeys.length; i++) {
            html += `<li class="page-item ${i === currentPage ? 'active' : ''}">
                        <button class="page-link" onclick="goToPage(${i})">${i + 1}</button>
                    </li>`;
        }
        html += "</ul></nav>";
        paginationContainer.innerHTML = html;
    }

    window.goToPage = updatePage;
    updatePage(0);
});
