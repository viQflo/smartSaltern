document.addEventListener("DOMContentLoaded", function () {
    const chartConfigs = [
        { id: 'chart1', label: '온도(°C)', data: [21, 22, 23, 24, 23, 22, 21] },
        { id: 'chart2', label: '습도(%)', data: [40, 42, 45, 50, 55, 53, 50] },
        { id: 'chart3', label: '풍속(m/s)', data: [3, 4, 5, 6, 5, 4, 3] },
        { id: 'chart4', label: '강수량(mm)', data: [0, 1, 0, 3, 5, 0, 0] },
        { id: 'chart5', label: '일조량(%)', data: [90, 85, 80, 70, 60, 75, 80] }
    ];

    chartConfigs.forEach(cfg => {
        new Chart(document.getElementById(cfg.id), {
            type: 'bar', // <-- 모두 bar로 통일
            data: {
                labels: ['월', '화', '수', '목', '금', '토', '일'],
                datasets: [{
                    label: cfg.label,
                    data: cfg.data,
                    backgroundColor: 'rgba(54, 162, 235, 0.5)',
                    borderColor: 'rgba(54, 162, 235, 1)',
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false, // <-- 고정 높이 div에서 유효하게 설정
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    });
});
