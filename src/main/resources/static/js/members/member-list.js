async function apiRequest(url, options = {}) {
    try {
        const response = await fetch(url, options);
        const responseBody = await response.json();

        if (!response.ok) {
            return;
        }

        return responseBody;
    } catch (error) {
        console.error('API 요청 실패:', error);
        throw error;
    }
}

async function fetchMembers() {
    const result = await apiRequest('/api/members');
    return result.data;
}

function renderMembers(members) {
    const tbody = document.getElementById('member-table-body');
    tbody.innerHTML = '';

    members.forEach(member => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${member.memberId}</td>
            <td>${member.memberName}</td>
        `;
        tbody.appendChild(tr);
    });
}

document.addEventListener('DOMContentLoaded', async () => {
    try {
        const members = await fetchMembers();
        renderMembers(members);
    } catch (error) {
        alert(error.message || '회원 목록을 불러오지 못했습니다.');
    }
});