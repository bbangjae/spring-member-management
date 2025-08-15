export function renderTable(tbodyId, list, createRowHtmlFn, emptyMessage) {
    const tbody = document.getElementById(tbodyId);
    tbody.innerHTML = '';

    if (!list || list.length === 0) {
        tbody.innerHTML = `<tr><td colspan="4" class="text-center">${emptyMessage}</td></tr>`;
        return;
    }

    list.forEach((item, index) => {
        const tr = document.createElement('tr');
        tr.innerHTML = createRowHtmlFn(item, index);
        tbody.appendChild(tr);
    });
}