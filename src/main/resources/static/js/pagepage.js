document.addEventListener("DOMContentLoaded", function () {
    const tbody = document.getElementById("sensor-log-body");
    const paginationContainer = document.createElement("div");
    paginationContainer.className = "mt-3 d-flex justify-content-center";
    paginationContainer.id = "pagination";
    tbody.parentElement.parentElement.appendChild(paginationContainer);

    function paginateByDate() {
        const rows = Array.from(tbody.querySelectorAll("tr"));
        const pages = {};

        // 날짜별로 그룹핑
        rows.forEach(row => {
            const timeCell = row.querySelector("td");
            if (!timeCell) return;
            const dateStr = timeCell.textContent.split("T")[0]; // "2025-04-04"

            if (!pages[dateStr]) pages[dateStr] = [];
            pages[dateStr].push(row);
        });

        const dateList = Object.keys(pages).sort().reverse();
        const maxVisible = 5;
        let currentPage = 0;

        function renderPage(pageIndex) {
            tbody.innerHTML = "";
            const date = dateList[pageIndex];
            pages[date].forEach(row => tbody.appendChild(row));
            renderPagination();
        }

        function renderPagination() {
            paginationContainer.innerHTML = "";

            const ul = document.createElement("ul");
            ul.className = "pagination";

            for (let i = 0; i < dateList.length; i++) {
                const li = document.createElement("li");
                li.className = "page-item" + (i === currentPage ? " active" : "");
                const a = document.createElement("a");
                a.className = "page-link";
                a.textContent = i + 1;
                a.href = "#";
                a.addEventListener("click", (e) => {
                    e.preventDefault();
                    currentPage = i;
                    renderPage(currentPage);
                });
                li.appendChild(a);
                ul.appendChild(li);
            }

            paginationContainer.appendChild(ul);
        }

        renderPage(currentPage);
    }

    // tbody 내용 다 채워진 뒤에 실행 (chart-setup.js가 끝난 이후)
    setTimeout(paginateByDate, 500); // 혹은 window.onload로도 가능
});

