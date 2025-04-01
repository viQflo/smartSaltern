document.addEventListener("DOMContentLoaded", async function () {
    const response = await fetch("/api/env-data");
    const jsonData = await response.json();
    console.log("jsonData Sample:", jsonData);

    // 날짜 문자열 추출 (예: "2025/04/01")
    function extractDateString(dataList) {
        if (dataList.length === 0) return '';
        const fullDate = dataList[0].time;
        const [date] = fullDate.split(" ");
        return date.replace(/-/g, "/");
    }

    // 차트 그리기
    function drawLineChart(canvasId, label, dataList) {
        if (!dataList || dataList.length === 0) return;

        const labels = dataList.map(item => {
            const timePart = item.time?.split(" ")[1] || "시간없음";
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
            case 'TMP':
                if (value >= 25 && value <= 35) return '정상';
                if ((value >= 20 && value < 25) || (value > 35 && value <= 38)) return '경고';
                return '위험';
            case 'REH':
                if (value >= 40 && value <= 60) return '정상';
                if ((value >= 30 && value < 40) || (value > 60 && value <= 70)) return '경고';
                return '위험';
            case 'PCP':
                if (value === 0) return '정상';
                if (value > 0 && value <= 1) return '경고';
                return '위험';
            case 'SUN':
                if (value >= 6) return '정상';
                if (value >= 3 && value < 6) return '경고';
                return '위험';
            default:
                return 'N/A';
        }
    }

    function getStatusColorClass(status) {
        switch (status) {
            case '정상': return 'bg-success';
            case '경고': return 'bg-warning text-dark';
            case '위험': return 'bg-danger';
            default: return 'bg-secondary';
        }
    }

    function makeBadge(status) {
        const colorClass = getStatusColorClass(status);
        return `<span class="badge ${colorClass}" style="margin-left: 6px;">${status}</span>`;
    }

    function getStatusClass(status) {
        switch (status) {
            case '정상': return 'text-success';
            case '경고': return 'text-warning';
            case '위험': return 'text-danger';
            default: return 'text-muted';
        }
    }

    // 테이블 렌더링
    function renderSensorLogTable(dataMap) {
        const logBody = document.getElementById("sensor-log-body");
        if (!logBody) return console.warn("#sensor-log-body 없음.");

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

            const allStatuses = [tmpStatus, rehStatus, pcpStatus, sunStatus];
            const overallStatus = allStatuses.includes('위험') ? '위험'
                : allStatuses.includes('경고') ? '경고'
                    : allStatuses.includes('정상') ? '정상' : 'N/A';

            const warningItems = [];
            if (tmpStatus === '경고' || tmpStatus === '위험') warningItems.push(`온도(${tmpStatus})`);
            if (rehStatus === '경고' || rehStatus === '위험') warningItems.push(`습도(${rehStatus})`);
            if (pcpStatus === '경고' || pcpStatus === '위험') warningItems.push(`강수량(${pcpStatus})`);
            if (sunStatus === '경고' || sunStatus === '위험') warningItems.push(`일조량(${sunStatus})`);

            html += `
                <tr>
                    <td>${time}</td>
                    <td class="${getStatusClass(overallStatus)} fw-bold">${overallStatus}</td>
                    <td>${isNaN(TMP) ? '-' : TMP + '℃'} ${makeBadge(tmpStatus)}</td>
                    <td>${isNaN(REH) ? '-' : REH + '%'} ${makeBadge(rehStatus)}</td>
                    <td>${isNaN(PCP) ? '-' : PCP + 'mm'} ${makeBadge(pcpStatus)}</td>
                    <td>${isNaN(SUN) ? '-' : SUN + 'h'} ${makeBadge(sunStatus)}</td>
                    <td>${warningItems.length > 0 ? warningItems.join(', ') : '-'}</td>
                </tr>
            `;
        }

        logBody.innerHTML = html;
    }

    // 차트 그리기
    drawLineChart('chart1', '온도 (℃)', jsonData.TMP);
    drawLineChart('chart2', '습도 (%)', jsonData.REH);
    drawLineChart('chart3', '풍속 (m/s)', jsonData.WSD);
    drawLineChart('chart4', '강수량 (mm)', jsonData.PCP);
    drawLineChart('chart5', '일조량 (h)', jsonData.SUN);

    // 테이블 출력
    renderSensorLogTable(jsonData);
});
