document.addEventListener("DOMContentLoaded", async function () {
    const response = await fetch("/api/env-data");
    const jsonData = await response.json();

    function drawLineChart(canvasId, label, dataList) {
        const labels = dataList.map(item => item.time);
        const data = dataList.map(item => item.value);

        new Chart(document.getElementById(canvasId), {
            type: 'line',
            data: {
                labels: labels.reverse(),
                datasets: [{
                    label: label,
                    data: data.reverse(),
                    fill: false,
                    tension: 0.1
                }]
            },
            options: {
                responsive: true,
                scales: {
                    x: { display: true, title: { display: true, text: '시간' }},
                    y: { display: true, title: { display: true, text: label } }
                }
            }
        });
    }

    drawLineChart('chart1', '온도 (℃)', jsonData.TMP);
    drawLineChart('chart2', '습도 (%)', jsonData.REH);
    drawLineChart('chart3', '풍속 (m/s)', jsonData.WSD);
    drawLineChart('chart4', '강수량 (mm)', jsonData.PCP);
    drawLineChart('chart5', '일조량 (h)', jsonData.SUN);
});
