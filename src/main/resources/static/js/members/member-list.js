import { apiRequest } from '../api/request.js';
import { renderTable } from '../utils/tableRender.js';

async function fetchMembers() {
    const response = await apiRequest('/api/members');
    return response.data;
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

        renderTable(
            'member-table-body',
            members,
            createMemberRowHtml,
            '등록된 회원이 없습니다.'
        );
    } catch (error) {
        alert(error.message || '회원 목록을 불러오지 못했습니다.');
    }
});