async function apiRequest(url, options = {}) {
    try {
        const response = await fetch(url, options);
        if (!response.ok) {
            const errorData = await response.json().catch(() => ({
                statusMessage: '일시적인 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.'
            }));
            throw new Error(errorData.statusMessage || '일시적인 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.');
        }
        if (response.status === 204) {
            return null;
        }
        return await response.json();
    } catch (error) {
        console.error('API 요청 실패:', error);
        throw error;
    }
}

async function fetchMembers() {
    const response = await apiRequest('/api/members');
    return response.data;
}

function renderMembers(members) {
    const tbody = document.getElementById('member-table-body');
    tbody.innerHTML = '';

    if (!members || members.length === 0) {
        tbody.innerHTML = '<tr><td colspan="4" class="text-center">등록된 회원이 없습니다.</td></tr>';
        return;
    }

    members.forEach((member, index) => {
        const tr = document.createElement('tr');
        tr.dataset.memberId = member.memberId;
        tr.innerHTML = createMemberRowHtml(member, index);
        tbody.appendChild(tr);
    });
}

function createMemberRowHtml(member, displayIndex) {
    return `
        <td>${displayIndex + 1}</td>
        <td class="member-name" data-original-name="${member.memberName}">${member.memberName}</td>
        <td class="member-team" data-team-id="${member.teamId}">${member.teamName}</td>
        <td><a href="/members/${member.memberId}" class="details-link">⚙️</a></td>
    `;
}

document.addEventListener('DOMContentLoaded', async () => {
    try {
        const members = await fetchMembers();
        renderMembers(members);
    } catch (error) {
        alert(error.message || '회원 목록을 불러오지 못했습니다.');
    }
});